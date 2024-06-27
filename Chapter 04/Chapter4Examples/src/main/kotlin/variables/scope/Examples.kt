package variables.scope

import kotlin.random.Random

// Top-level mutable variable
var currentWeather: String = "Sunny"

class RaceCar(
    val carNumber: Int,
    val maxSpeed: Double = Random.nextDouble(200.0, 230.0),
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
    // ...
}