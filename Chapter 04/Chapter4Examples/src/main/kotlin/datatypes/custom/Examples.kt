package datatypes.custom

import java.util.*
import kotlin.random.Random

class Driver(
    val name: String,
    var points: Int = 0,
    val uuid: UUID = UUID.randomUUID(), // unique identifier
) {
    // ...

    override fun toString(): String {
        return "Driver(name='$name', points=$points)"
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
}

data class Result(
    val team: Team,
    val driver: Driver,
    val car: RaceCar,
    var totalLapTime: Double = 0.0,
    var fastestLap: Double = Double.MAX_VALUE,
)

enum class RaceEvent {
    NORMAL,
    BREAKDOWN,
    COLLISION,
}

//-----

class Team(
    val name: String,
    // ...
)

class RaceCar(
    val carNumber: Int,
    val maxSpeed: Double = Random.nextDouble(200.0, 230.0),
    internal var isPitStopNeeded: Boolean = false,
    numLaps: Int,
) {
    var lapTimes = arrayOfNulls<Double>(numLaps)
}

