package controlflowstatements

import java.util.*
import kotlin.random.Random

val numLaps = 5

val maxTeams = if (numLaps <= 3) 5 else 10

class Race(
    val numberOfLaps: Int,
    val teams: List<Team>,
    private var currentLap: Int = 0,
) {
    private val raceResults: MutableList<Result> = mutableListOf()

    private fun runLap() {
        teams.forEach { team ->
            team.driverCarMap.forEach { (driver, car) ->
                val result = findOrAddResult(team, driver, car)
                // If the car needs a pit stop, we skip this lap
                if (car.isPitStopNeeded) {
                    println("Car #${car.carNumber} is in the pit stop.")
                } else {
                    val lapTime = simulateLap(driver, car)
                    result.totalLapTime += lapTime
                    if (lapTime < result.fastestLap) {
                        result.fastestLap = lapTime
                    }
                }
            }
        }
    }

    data class Result(
        val team: Team,
        val driver: Driver,
        val car: RaceCar,
        var totalLapTime: Double = 0.0,
        var fastestLap: Double = Double.MAX_VALUE,
    )

    data class TeamResult(
        val team: Team,
        val totalTime: Double,
    )

    fun start() {
        for (lap in 1..numberOfLaps) {
            currentLap = lap
            println("Starting lap $currentLap")
            runLap()
        }
    }

    fun exampleForLoop() {
        for (lap in 1..3) {
            println("Lap $lap")
        }
    }

    fun runRace() {
        // race simulation...
    }


    private fun findOrAddResult(team: Team, driver: Driver, car: RaceCar) =
        raceResults.find { it.driver == driver } ?: Race.Result(team, driver, car).also { raceResults.add(it) }

    private fun simulateLap(driver: Driver, car: RaceCar): Double {
        return when (generateRaceEvent()) {
            RaceEvent.BREAKDOWN -> {
                car.isPitStopNeeded = true
                PITSTOP_TIME
            }

            RaceEvent.COLLISION -> {
                car.isPitStopNeeded = true
                PITSTOP_TIME
            }

            RaceEvent.NORMAL -> {
                car.currentLap++
                val lapTime = Random.nextDouble(1.0, 2.0)
                car.addLapTime(car.currentLap, lapTime)
                println("Driver ${driver.name} in car #${car.carNumber} completed in $lapTime minutes.")
                lapTime
            }
        }
    }

    fun simulateRace(drivers: List<Driver>) {
        drivers.forEach driverLoop@{ driver ->
            val breakdown = Random.nextBoolean()
            if (breakdown) {
                println("${driver.name}'s car suffered a breakdown.")
                // Skip the rest of the current iteration
                return@driverLoop
            }
            println("Driver ${driver.name} completes the lap.")
        }
        println("The race is over.")
    }

    fun calculateDriverPerformance(driver: Driver): String {
        val performance = run loop@{
            driver.raceResults.forEach { raceResult ->
                if (raceResult.isDisqualified) {
                    // Stop evaluating further race results
                    // return with a disqualified message.
                    return@loop "Driver ${driver.name} was disqualified."
                }
            }
            // If the driver wasn't disqualified in any race,
            // return a completed message.
            "Driver ${driver.name} completed all races."
        }
        return performance
    }

    fun determineWinner(): String {
        if (currentLap < numberOfLaps) {
            return "Race is still underway!"
        } else {
            val winningTeam = calculateWinningTeam()
            return "The winner is team $winningTeam"
        }
    }

    fun determineWinner_refactored() = if (currentLap < numberOfLaps) {
        "Race is still underway!"
    } else {
        val winningTeam = calculateWinningTeam()
        "The winner is team $winningTeam"
    }


    private fun calculateWinningTeam(): String = "Mercedes"

    companion object {
        const val PITSTOP_TIME = 5.0 // 5 minutes
        const val SLOWDOWN_TIME = 1.0  // 1 minute
    }
}

fun updateTrackCondition(weather: String) {
    when (weather) {
        "Sunny" -> println("The track is dry.")
        "Rainy" -> println("The track is wet.")
        "Cloudy" -> println("The track condition is uncertain.")
        else -> println("Unable to determine track condition.")
    }
}

fun handleTrackCondition(weather: String) {
    when (weather) {
        "Sunny", "Cloudy" -> println("The track is dry.")
        "Rainy", "Drizzle" -> println("The track is wet.")
        else -> println("Unable to determine track condition.")
    }
}

enum class RaceEvent {
    NORMAL,
    BREAKDOWN,
    COLLISION
}

fun describeEvent(event: RaceEvent) = when (event) {
    RaceEvent.NORMAL -> "Everything is running smoothly"
    RaceEvent.BREAKDOWN -> "There's a breakdown on the track"
    RaceEvent.COLLISION -> "Collision - safety measures activated"
}

sealed class Team(
    val name: String,
    val drivers: List<Driver>,
    val raceCars: Set<RaceCar>,
) {
    val driverCarMap: Map<Driver, RaceCar> = drivers.zip(raceCars).toMap()

    object Mercedes : Team("Mercedes", emptyList(), emptySet())
    object Ferrari : Team("Ferrari", emptyList(), emptySet())
    object RedBull : Team("RedBull", emptyList(), emptySet())

    fun printPoints() {
        for (driver in drivers) {
            println("${driver.name} has ${driver.points} points!")
        }
    }

    fun printDriversPositions() {
        for ((index, driver) in drivers.withIndex()) {
            println("Position ${index + 1}: ${driver.name}")
        }
    }


}

fun printTeamInfo(team: Team) = when (team) {
    is Team.Mercedes -> "Mercedes: Based in Brackley, England"
    is Team.Ferrari -> "Ferrari: Based in Maranello, Italy"
    is Team.RedBull -> "RedBull: Based in Milton Keynes, England"
}

fun generateRaceEvent(): RaceEvent {
    val event = Random.nextInt(100).let {
        when {
            it < 5 -> RaceEvent.BREAKDOWN
            it < 7 -> RaceEvent.COLLISION
            else -> RaceEvent.NORMAL
        }
    }
    return event
}

fun generateRaceEvent_refatored(): RaceEvent = Random.nextInt(100).let {
    when {
        it < 5 -> RaceEvent.BREAKDOWN
        it < 7 -> RaceEvent.COLLISION
        else -> RaceEvent.NORMAL
    }
}

class RaceCar(
    val carNumber: Int,
    val manufacturer: String? = null,
    private val maxSpeed: Double = Random.nextDouble(200.0, 230.0), // mph, random speed between 200 and 230
    private var currentSpeed: Double = 0.0,
    internal var currentLap: Int = 0,
    internal var isPitStopNeeded: Boolean = false,
    numLaps: Int,
) {
    var lapTimes = arrayOfNulls<Double>(numLaps)

    fun addLapTime(lapNumber: Int, time: Double) {
        lapTimes[lapNumber - 1] = time
    }

    fun exampleForLoop(numLaps: Int = 5) {
        for (i in numLaps downTo 0 step 2) {
            println("Lap $i time: ${lapTimes[i]}")
        }
    }

    fun exampleWhileLoop() {
        var fuel = 100

        while (fuel > 0) {
            println("Car is still running with $fuel% fuel")
            fuel -= 10
        }
    }

    fun exampleDoWhileLoop() {
        var lapsRemaining = 0

        do {
            println("Still racing. Laps remaining: $lapsRemaining")
            lapsRemaining--
        } while (lapsRemaining > 0)

    }
}

class Driver(
    val name: String,
    var points: Int = 0,
    val uuid: UUID = UUID.randomUUID(), // unique identifier
    val raceResults: List<RaceResult> = listOf(),
) {
    /**
     * Adds points to the driver's total.
     * @param newPoints The number of points to add.
     */
    fun addPoints(newPoints: Int) {
        points += newPoints
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Driver

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    override fun toString(): String {
        return "Driver(name='$name', points=$points)"
    }

    data class RaceResult(val isDisqualified: Boolean)
}

fun promptInt(prompt: String, max: Int): Int {
    var value: Int
    do {
        print(prompt)
        value = readLine()?.toIntOrNull() ?: 0
    } while (value !in 1..max)
    return value
}

fun promptString(prompt: String): String {
    print(prompt)
    return readLine() ?: ""
}

fun createUniqueDriver(existingDrivers: MutableList<Driver>) {
    var driverName: String
    while (true) {
        driverName = promptString("Please enter a unique driver name: ")
        if (driverName.isNotEmpty()
            && existingDrivers.none { it.name == driverName }
        ) {
            // A unique name has been found, so we can break the loop
            break
        }
        println("This driver name is already taken. Please try again.")
    }
    existingDrivers.add(Driver(name = driverName))
}

class ExampleBreakToLabel() {
    fun createTeams() {
        val teams = mutableListOf<Team>()
        val maxTeams = 5 // arbitrary number

        teamLoop@ for (teamNum in 1..maxTeams) {
            val team = Team()
            driverLoop@ while (true) {
                val driverName =
                    promptString("Enter a unique name (or 'quit' to stop):")

                if (driverName == "quit") {
                    println("Exiting team creation.")
                    break@teamLoop
                }

                if (!team.hasDriver(driverName)) {
                    team.addDriver(Driver(driverName))
                    break@driverLoop
                } else {
                    println("This driver name is already taken.")
                }
            }
            teams.add(team)
        }
        println("Created ${teams.size} teams!")
    }

    data class Team(val drivers: MutableList<Driver> = mutableListOf()) {

        fun hasDriver(driverName: String) = drivers.any { it.name == driverName }

        fun addDriver(driver: Driver) {
            drivers.add(driver)
        }
    }
}

class ExampleContinue(val drivers: List<Driver> = listOf()){
    fun simulateRace(drivers: List<Driver>) {
        for (driver in drivers) {
            if (driver.car.hasPitStop) {
                println("Driver ${driver.name} is in a pit stop.")
                // Skip this turn and proceed with the next driver
                continue
            }
            // simulate a turn for this driver...
        }
    }
    data class Driver(val raceResults: List<RaceResult> = listOf(), val name: String, val car: RaceCar = RaceCar(1))
    data class RaceCar(val number: Int, val hasPitStop: Boolean = false)
    data class RaceResult(val isDisqualified: Boolean)
}

class ExampleContinueToLabel(val drivers: List<Driver> = listOf()){
    fun simulateRace(laps: Int, drivers: List<Driver>) {
        lapLoop@ for (lap in 1..laps) {
            println("Lap $lap starts.")
            for (driver in drivers) {
                if (driver.car.hasPitStop) {
                    println("Driver ${driver.name} is in a pit stop.")
                    // Skip the rest of this lap and proceed to the next lap
                    continue@lapLoop
                }
                // simulate a lap for this driver...
            }
        }
    }
    data class Driver(val raceResults: List<RaceResult> = listOf(), val name: String, val car: RaceCar = RaceCar(1))
    data class RaceCar(val number: Int, val hasPitStop: Boolean = false)
    data class RaceResult(val isDisqualified: Boolean)
}

class ExampleAdvancedWhenExpression{
    sealed class RaceEvent {
        object Normal : RaceEvent()
        class SafetyCar(val laps: Int) : RaceEvent()
        object Collision : RaceEvent()
    }

    fun RaceCar.handleRaceEvent(event: RaceEvent): String {
        return when (event) {
            is RaceEvent.Normal -> {
                this.continueRacing()
                "Continue racing as usual."
            }
            is RaceEvent.SafetyCar -> {
                this.followSafetyCar(event.laps)
                "Safety car deployed for ${event.laps} laps. Reducing speed."
            }
            is RaceEvent.Collision -> {
                this.initiateSafetyProtocol()
                "Collision detected! Initiating safety protocol."
            }
        }
    }

    fun RaceCar.continueRacing() {
        // Implementation for continuing the race
    }

    fun RaceCar.followSafetyCar(laps: Int) {
        // Implementation to follow the safety car for a given number of laps
    }

    fun RaceCar.initiateSafetyProtocol() {
        // Implementation for initiating safety protocol after a collision
    }
}


