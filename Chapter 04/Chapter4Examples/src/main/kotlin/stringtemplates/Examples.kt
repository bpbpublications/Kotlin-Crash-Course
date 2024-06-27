package stringtemplates

import kotlin.random.Random

val driver = Driver("Hamilton")
val team = Team("Red Bull")
val car = RaceCar(carNumber = 1, numLaps = 5)
val result = Result(team, driver, car)

val lapTime = 1.25

fun exampleTemplate() {
    val lap = 1
    // String template
    println("Starting lap $lap!")
}

fun exampleConcatenationVsTemplates() {
    println(
        "Driver " + driver.name + " completed lap in " +
                lapTime + " minutes."
    )
    println("Driver ${driver.name} completed lap in $lapTime minutes.")
}

fun exampleInterpolation() {
    println("Driver ${driver.name} completed lap in $lapTime minutes")
}

fun exampleRawString() {
    val leaderboardEntryConcatenation =
        "Driver " + result.driver.name +
                " in car #" + result.car.carNumber +
                " from team " + result.team.name +
                " with time " + result.totalLapTime +
                " min. (fastest lap: " +
                result.fastestLap + " min.)"

    val leaderboardEntryRaw = """
Driver ${result.driver.name} in car #${result.car.carNumber}
from team ${result.team.name} with time ${result.totalLapTime} min.
(fastest lap: ${result.fastestLap} min.)
"""

    val leaderboardEntryRawTrimMargin = """
|Driver ${result.driver.name} in car #${result.car.carNumber}
|from team ${result.team.name} with time ${result.totalLapTime} min.
|(fastest lap: ${result.fastestLap} min.)
""".trimMargin()

}


//------
class Driver(
    val name: String,
    var points: Int = 0,
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

class RaceCar(
    val carNumber: Int,
    val maxSpeed: Double = Random.nextDouble(
        200.0,
        230.0
    ),
    internal var isPitStopNeeded: Boolean = false,
    numLaps: Int,
) {
    var lapTimes = arrayOfNulls<Double>(numLaps)
    var bestLapTime = Double.MAX_VALUE
}

data class RaceResult(
    val driver: Driver,
    val team: Team,
    val car: RaceCar,
    val totalLapTime: Double,
    val fastestLap: Double,
    val position: Int,
)

fun generateRaceSummary(result: RaceResult): String {
    return """
        |Race Summary:
        |Driver: ${result.driver.name}
        |Team: ${result.team.name}
        |Car: #${result.car.carNumber}
        |Total Time: ${"%.2f".format(result.totalLapTime)} minutes
        |Fastest Lap: ${"%.2f".format(result.fastestLap)} minutes
        |${if (result.position == 1) "Winner of the Race!" else ""}
    """.trimMargin()
}

fun List<RaceCar>.findFastestCar(): RaceCar? =
    this.minByOrNull { it.bestLapTime }
