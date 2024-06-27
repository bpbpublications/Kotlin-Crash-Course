package com.bpbonline.f1app

import com.bpbonline.f1app.participants.Driver
import com.bpbonline.f1app.participants.RaceCar
import com.bpbonline.f1app.participants.Team
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class Race(
    val numberOfLaps: Int,
    val teams: List<Team>,
) {
    private val raceResultsMutex = Mutex()
    val raceResults: MutableList<Result> = mutableListOf()
    private val raceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val exceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            if (exception is UnrepairableCollisionException) {
                val driverName = exception.driver.name
                println("$driverName has to retire: ${exception.message}")
            } else {
                // Rethrow other exceptions
                throw exception
            }
        }


    private val fastestLapFlow = MutableStateFlow<FastestLap?>(null)

    // Expose as a read-only flow
    val fastestLap: StateFlow<FastestLap?> = fastestLapFlow.asStateFlow()

    data class FastestLap(
        val lapTime: Double,
        val driverName: String,
    )

    data class Result(
        val team: Team,
        val driver: Driver,
        val car: RaceCar,
        var totalLapTime: Double = 0.0,
        var fastestLap: Double = Double.MAX_VALUE,
        var isInRace: Boolean = true,
    )

    private fun updateFastestRaceLap(
        driver: Driver,
        newLapTime: Double,
    ) {
        // Only update if the new lap time is faster
        fastestLapFlow.value?.let {
            if (newLapTime < it.lapTime) {
                fastestLapFlow.value =
                    FastestLap(newLapTime, driver.name)
            }
        } ?: run {
            fastestLapFlow.value =
                FastestLap(newLapTime, driver.name)
        }
    }


    private suspend fun runCarRace(
        car: RaceCar,
        team: Team,
        driver: Driver,
    ) {
        for (lap in 1..numberOfLaps) {
            val threadName = "Thread ${Thread.currentThread().name}"
            val carName = "Car ${car.carNumber}"
            val driverName = "Driver ${driver.name}"
            val lapContext = "$driverName in $carName on $threadName"
            println("$lapContext starts lap $lap.")
            val result = findOrAddResult(team, driver, car)
            if (car.isPitStopNeeded) {
                handlePitStop(result)
                println("$lapContext completes lap after a pitstop.")
            } else {
                runLapForDriver(result)
                println("$lapContext completes lap $lap.")
            }
        }
    }

    private suspend fun observeFastestLapTime() =
        raceScope.launch {
            fastestLap.collect { fastestLapTime ->
                fastestLapTime?.let {
                    val label =
                        "The new fastest lap time in Race is"
                    println("$label: ${it.lapTime} by ${it.driverName}")
                }
            }
        }

    private suspend fun runLapForDriver(result: Result) {
        try {
            val lapTime = simulateLap(result)
            delay((lapTime * 1000).toLong())
            result.totalLapTime += lapTime
            if (lapTime < result.fastestLap) {
                result.fastestLap = lapTime
            }
            updateFastestRaceLap(result.driver, lapTime)
        } catch (e: SafetyCarException) {
            println("${e.message} Safety car deployed.")
            slowDownLapTimes()
        } catch (e: YellowFlagException) {
            println("${e.message} Yellow flag raised.")
            slowDownLapTimes()
        }
    }


    private suspend fun handlePitStop(result: Result) {
        // reset the flag
        result.car.isPitStopNeeded = false
        delay((PITSTOP_TIME * 1000).toLong())
        // add pit stop time
        result.totalLapTime += PITSTOP_TIME
    }

    private suspend fun slowDownLapTimes() {
        raceResultsMutex.withLock {
            // Increase lap times for all drivers to simulate a race slowdown
            raceResults.forEach { it.totalLapTime += SLOWDOWN_TIME }
        }
    }

    private suspend fun simulateLap(result: Result): Double {
        val (_, driver, car) = result
        return when (generateRaceEvent()) {
            RaceEvent.BREAKDOWN -> {
                car.isPitStopNeeded = true
                throw YellowFlagException(
                    "Car ${car.carNumber} broke down - pit stop!"
                )
            }

            RaceEvent.COLLISION -> {
                val context = "Car ${car.carNumber} has collided"
                val isReparable = Random.nextBoolean()
                if (isReparable) {
                    car.isPitStopNeeded = true
                    throw SafetyCarException(
                        "$context - pit stop!"
                    )
                } else {
                    raceResultsMutex.withLock {
                        result.isInRace = false
                    }
                    throw UnrepairableCollisionException(
                        "$context - cannot continue the race.",
                        driver
                    )
                }
            }

            RaceEvent.NORMAL -> {
                car.currentLap++
                val lapTime = Random.nextDouble(1.0, 2.0)
                car.addLapTime(car.currentLap, lapTime)
                println("Driver ${driver.name} completed lap: $lapTime min")
                lapTime
            }
        }
    }

    suspend fun findOrAddResult(team: Team, driver: Driver, car: RaceCar) =
        raceResults.find { it.driver == driver }
            ?: Result(team, driver, car).also {
                raceResultsMutex.withLock {
                    raceResults.add(it)
                }
            }

    fun displayLeaderboard() {
        println("\n--- LEADERBOARD ---")
        raceResults
            .filter { it.isInRace }
            .sortedBy { it.totalLapTime }
            .forEachIndexed { index, result ->
                val leaderboardEntry = """
            |${index + 1}. Driver ${result.driver.name} 
            |in car #${result.car.carNumber}
            |from team ${result.team.name} 
            |with total time ${result.totalLapTime} minutes
            |(fastest lap: ${result.fastestLap} minutes)
            """.trimMargin()
                println(leaderboardEntry)
            }
    }

    suspend fun runRace() {
        // observe fastest lap of the race
        val fastestLapJob = observeFastestLapTime()
        start()
        // Once the race is complete, cancel the observer job
        fastestLapJob.cancel()
        end()
    }

    suspend fun start() {
        val jobs = mutableListOf<Job>()
        teams.forEach { team ->
            team.driverCarMap.forEach { (driver, car) ->
                val job = raceScope.launch(exceptionHandler) {
                    runCarRace(car, team, driver)
                }
                jobs.add(job)
            }
        }
        // Wait for all cars to complete the race
        jobs.joinAll()
    }

    fun end() {
        awardPoints()
        displayLeaderboard()
        displayTeamLeaderboard()
    }

    /**
     * Awards points to the top 10 finishers.
     */
    private fun awardPoints() {
        // Points corresponding to the positions 1st through 10th.
        val pointsList = listOf(25, 18, 15, 12, 10, 8, 6, 4, 2, 1)

        // Award points to the top 10 finishers
        for ((index, result) in raceResults.take(10).withIndex()) {
            // The points for this position
            // are at the same index in the pointsList
            val points = pointsList.getOrNull(index) ?: 0
            result.driver.addPoints(points)
        }
    }

    fun displayTeamLeaderboard() {
        println("\n--- TEAM LEADERBOARD ---")
        val teamResults = teams.toSortedTeamResults(raceResults)

        teamResults.forEachIndexed { index, result ->
            println(result.format(index))
        }
    }

    /**
     * Extension function on List<Team> to generate TeamResults and sort them by total time
     */
    private fun List<Team>.toSortedTeamResults(raceResults: List<Result>) =
        map { team ->
            val teamTime =
                raceResults.filter { it.team == team }
                    .sumOf { it.totalLapTime }
            // Count the number of cars in this team that completed the race
            val carsInRace =
                raceResults.count { it.team == team && it.isInRace }
            TeamResult(team, teamTime, carsInRace)
        }.sortedWith(compareByDescending<TeamResult> { it.carsInRace }
            .thenBy { it.totalTime })



    /**
     *  Extension function on TeamResult
     *  to print the result in the desired format
     */
    private fun TeamResult.format(index: Int): String {
        val teamPosition = "${index + 1}. Team ${team.name}"
        val teamTime = "Total time $totalTime minutes"
        val noOfCars = "with ${carsInRace} cars that completed the race"
        val sponsor = team.mainSponsor?.let { "Sponsored by ${it.name}" }
            ?: "No main sponsor"
        return "$teamPosition $noOfCars. $teamTime. $sponsor"
    }



    data class TeamResult(
        val team: Team,
        val totalTime: Double,
        val carsInRace: Int,
    )

    companion object {
        // 5 minutes
        const val PITSTOP_TIME = 5.0

        // 1 minute
        const val SLOWDOWN_TIME = 1.0
    }
}

enum class RaceEvent {
    NORMAL,
    BREAKDOWN,
    COLLISION,
}

fun generateRaceEvent(
    breakdownPercent: Int = 5,
    collisionPercent: Int = 30,
): RaceEvent {
    val totalExceptionPercent = breakdownPercent + collisionPercent
    val event = Random.nextInt(100).let {
        when {
            it < breakdownPercent -> RaceEvent.BREAKDOWN
            it < totalExceptionPercent -> RaceEvent.COLLISION
            else -> RaceEvent.NORMAL
        }
    }
    return event
}
