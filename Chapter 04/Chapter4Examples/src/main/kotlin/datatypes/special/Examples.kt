package datatypes.special

import kotlin.random.Random

private var currentLap: Int = 0
private val raceResults: MutableList<Result> = mutableListOf()

val teams: List<Team> = listOf()

fun printInfo(item: Any) {
    println(item.toString())
}

fun calculateRaceStatistics(): Nothing {
    TODO("This function has not been implemented yet.")
}

private fun displayTeamLeaderboard(): Unit {
    println("\n--- TEAM LEADERBOARD ---")
    val teamResults = teams.toSortedTeamResults(raceResults)

    teamResults.forEachIndexed { index, result ->
        println(result.format(index))
    }
}

// Extension function on List<Team> to generate TeamResults and sort them by total time
private fun List<Team>.toSortedTeamResults(raceResults: List<Result>): List<TeamResult> {
    return this.map { team ->
        val teamTime = raceResults.filter { it.team == team }
            .sumOf { it.totalLapTime }
        TeamResult(team, teamTime)
    }.sortedBy { it.totalTime }
}

// Extension function on TeamResult to print the result in the desired format
private fun TeamResult.format(index: Int): String {
    return "${index + 1}. Team ${this.team.name} with total time ${this.totalTime} minutes"
}

data class TeamResult(
    val team: Team,
    val totalTime: Double,
)

data class Result(
    val team: Team,
    val driver: Driver,
    val car: RaceCar,
    var totalLapTime: Double = 0.0,
    var fastestLap: Double = Double.MAX_VALUE,
)

class Team(
    val name: String,
    // ...
)

class Driver(
    val name: String,
    var points: Int = 0,
) {
    fun addPoints(newPoints: Int) {
        points += newPoints
    }
}

class RaceCar(
    val carNumber: Int,
    val maxSpeed: Double = Random.nextDouble(200.0, 230.0),
    internal var isPitStopNeeded: Boolean = false,
    numLaps: Int,
) {
    var lapTimes = arrayOfNulls<Double>(numLaps)
}

class Race {
    // ...
    data class Result(
        // ...
        var totalLapTime: Double = 0.0,
        var fastestLap: Double = Double.MAX_VALUE,
    )
}
