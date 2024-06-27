package com.bpbonline.f1app.participants

import kotlin.random.Random

class RaceCar(
    val carNumber: Int,
    val manufacturer: String? = null,
    val maxSpeed: Double = Random.nextDouble(200.0, 230.0),
    private var currentSpeed: Double = 0.0,
    internal var currentLap: Int = 0,
    internal var isPitStopNeeded: Boolean = false,
    numLaps: Int,
) {
    var lapTimes = arrayOfNulls<Double>(numLaps)

    fun addLapTime(lapNumber: Int, time: Double) {
        lapTimes[lapNumber - 1] = time
    }

    override fun toString(): String {
        return "RaceCar(carNumber=$carNumber, manufacturer=$manufacturer, maxSpeed=$maxSpeed, currentSpeed=$currentSpeed, currentLap=$currentLap, isPitStopNeeded=$isPitStopNeeded, lapTimes=${lapTimes.contentToString()})"
    }


}
