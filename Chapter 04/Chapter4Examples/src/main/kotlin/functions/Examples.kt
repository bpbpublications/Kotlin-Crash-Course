package functions

import kotlin.random.Random

const val MAX_LAPS = 5
const val MAX_TEAMS = 10

fun generateRaceEvent(): RaceEvent =
    Random.nextInt(100).let {
        when {
            it < 5 -> RaceEvent.BREAKDOWN
            it < 7 -> RaceEvent.COLLISION
            else -> RaceEvent.NORMAL
        }
    }

fun generateRaceEvent(
    breakdownPercent: Int = 5,
    collisionPercent: Int = 2,
): RaceEvent {
    val totalExceptionPercent =
        breakdownPercent + collisionPercent
    val event = Random.nextInt(100).let {
        when {
            it <= breakdownPercent -> RaceEvent.BREAKDOWN
            it <= totalExceptionPercent -> RaceEvent.COLLISION
            else -> RaceEvent.NORMAL
        }
    }
    return event
}

class Race(
    val numberOfLaps: Int,
    val teams: List<Team>,
    private var currentLap: Int = 0,
) {
    private val raceResults: MutableList<Result> =
        mutableListOf()

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

    fun runRace() {
        // race simulation...
    }

    private fun runLap() {
        teams.forEach { team ->
            team.driverCarMap.forEach { (driver, car) ->
                val result =
                    findOrAddResult(team, driver, car)
                // If the car needs a pit stop, we skip this lap
                if (car.isPitStopNeeded) {
                    println("Car #${car.carNumber} is in the pit stop.")
                } else {
                    val lapTime =
                        simulateLap(driver, car)
                    result.totalLapTime += lapTime
                    if (lapTime < result.fastestLap) {
                        result.fastestLap = lapTime
                    }
                }
            }
        }
    }

    private fun runLap_refctored() {
        val processCar: (Team, Map.Entry<Driver, RaceCar>) -> Unit =
            { team: Team, entry: Map.Entry<Driver, RaceCar> ->
                val (driver, car) = entry
                val result =
                    findOrAddResult(team, driver, car)
                // If the car needs a pit stop, we skip this
                if (car.isPitStopNeeded) {
                    println("Car #${car.carNumber} is in the pit stop.")
                } else {
                    val lapTime =
                        simulateLap(driver, car)
                    result.totalLapTime += lapTime
                    if (lapTime < result.fastestLap) {
                        result.fastestLap = lapTime
                    }
                }
            }
        val processTeam: (Team) -> Unit = { team: Team ->
            team.driverCarMap.entries.forEach { entry ->
                processCar(team, entry)
            }
        }
        teams.forEach(processTeam)
    }


    private fun displayTeamLeaderboard() {
        println("\n--- TEAM LEADERBOARD ---")
        val teamResults = teams.map { team ->
            val teamTime =
                raceResults.filter { it.team == team }
                    .sumOf { it.totalLapTime }
            TeamResult(team, teamTime)
        }.sortedBy { it.totalTime }
        teamResults.forEachIndexed { index, result ->
            println("${index + 1}. Team ${result.team.name}")
        }
    }

    // Extension function on List<Team> to generate TeamResults and sort them by total time
    private fun List<Team>.toSortedTeamResults(
        raceResults: List<Result>,
    ): List<TeamResult> {
        return this.map { team ->
            val teamTime =
                raceResults.filter { it.team == team }
                    .sumOf { it.totalLapTime }
            TeamResult(team, teamTime)
        }.sortedBy { it.totalTime }
    }

    // Extension function on TeamResult to print the result in the desired format
    private fun TeamResult.format(index: Int): String {
        return "${index + 1}. Team ${this.team.name} with total time ${this.totalTime} minutes"
    }

    private fun displayTeamLeaderboard_refactored() {
        println("\n--- TEAM LEADERBOARD ---")
        val teamResults =
            teams.toSortedTeamResults(raceResults)

        teamResults.forEachIndexed { index, result ->
            println(result.format(index))
        }
    }

    private fun simulateLap(
        driver: Driver,
        car: RaceCar,
    ): Double {
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

    private fun findOrAddResult(
        team: Team,
        driver: Driver,
        car: RaceCar,
    ) =
        raceResults.find { it.driver == driver }
            ?: Result(
                team,
                driver,
                car
            ).also { raceResults.add(it) }

    companion object {
        const val PITSTOP_TIME = 5.0 // 5 minutes
        const val SLOWDOWN_TIME = 1.0  // 1 minute
    }
}

fun main() {
    // Nested functions to prompt for user input
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

    fun createDriver(name: String) = Driver(name)

    fun createTeam(name: String, numLaps: Int) = Team(
        name,
        listOf(
            createDriver(name = promptString(prompt = "Enter name for driver 1 of team $name: ")),
            createDriver(name = promptString(prompt = "Enter name for driver 2 of team $name: ")),
        ),
        setOf(
            RaceCar(carNumber = 1, numLaps = numLaps),
            RaceCar(carNumber = 2, numLaps = numLaps),
        ),
    )

    val numLaps = promptInt(
        prompt = "Enter number of laps (up to $MAX_LAPS): ",
        max = MAX_LAPS,
    )
    val numTeams = promptInt(
        prompt = "Enter number of teams (up to $MAX_TEAMS): ",
        max = MAX_TEAMS,
    )

    val teams = List(numTeams) {
        val teamName = promptString(
            prompt = "Enter name for team ${it + 1}: "
        )
        createTeam(name = teamName, numLaps = numLaps)
    }

    val race =
        Race(numberOfLaps = numLaps, teams = teams)
    race.runRace()
}

//-----
fun runLap() {
    // some implementation
}

enum class RaceEvent {
    NORMAL,
    BREAKDOWN,
    COLLISION,
}

class Team(
    val name: String,
    val drivers: List<Driver>,
    val raceCars: Set<RaceCar>,
) {
    val driverCarMap: Map<Driver, RaceCar> =
        drivers.zip(raceCars).toMap()

    override fun toString(): String {
        return "Team(name='$name', driverCarMap=$driverCarMap)"
    }
}

class Driver(
    val name: String,
    var points: Int = 0,
)

class RaceCar(
    val carNumber: Int,
    val manufacturer: String? = null,
    private val maxSpeed: Double = Random.nextDouble(
        200.0,
        230.0
    ), // mph, random speed between 200 and 230
    private var currentSpeed: Double = 0.0,
    internal var currentLap: Int = 0,
    internal var isPitStopNeeded: Boolean = false,
    numLaps: Int,
) {
    var lapTimes = arrayOfNulls<Double>(numLaps)
        private set

    fun addLapTime(lapNumber: Int, time: Double) {
        lapTimes[lapNumber - 1] = time
    }
}

fun exampleHigherOrderFunction() {
    val raceResults: List<Race.Result> = emptyList()

    val sortedRaceResults =
        raceResults.sortedWith(compareBy { it.totalLapTime })

    // Lambda expression for sorting based on fastest lap
    val sortedByFastestLap =
        raceResults.sortedBy { it.fastestLap }
    println(sortedByFastestLap)
}

