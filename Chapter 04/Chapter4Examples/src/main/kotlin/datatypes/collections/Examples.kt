package datatypes.collections

import java.util.*
import kotlin.random.Random

val numTeams = 2
val numLaps = 5

private val raceResults: MutableList<Result> = mutableListOf()

val teams = List(numTeams) {
    val teamName = promptString("Enter name for team ${it + 1}: ")

    createTeam(teamName, numLaps)
}

val set = setOf(
    RaceCar(carNumber = 1, numLaps = numLaps),
    RaceCar(carNumber = 2, numLaps = numLaps)
)

class Team(
    val name: String,
    val drivers: List<Driver>,
    val raceCars: Set<RaceCar>,
) {
    val driverCarMap: Map<Driver, RaceCar> = drivers.zip(raceCars).toMap()

}

val team = teams[0]

fun exampleIterateOverMap(){
    team.driverCarMap.forEach { (driver, car) ->
        /* operation on each pair */
    }
}



//----
data class Result(
    val team: Team,
    val driver: Driver,
    val car: RaceCar,
    var totalLapTime: Double = 0.0,
    var fastestLap: Double = Double.MAX_VALUE,
)

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
        private set
}

class Driver(
    val name: String,
    private var points: Int = 0,
    val uuid: UUID = UUID.randomUUID(), // unique identifier
)