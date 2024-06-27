package exceptionhandling

import controlflowstatements.ExampleContinueToLabel
import kotlin.random.Random

open class RaceException(message: String) :
    Exception(message)

class PitStopException(message: String) :
    RaceException(message)

class SafetyCarException(message: String) :
    RaceException(message)

class YellowFlagException(message: String) :
    RaceException(message)

fun exampleThrowException(car: RaceCar) {
    if (car.isPitStopNeeded) {
        throw PitStopException("Car can’t run a lap, it needs a pit stop")
    }

}

private fun runLapForDriver(result: Result) {
    try {
        // Here, we're simulating a lap for a driver and a car
        // Can throw Race exceptions
        val lapTime =
            simulateLap(result.driver, result.car)

        // If the simulation is successful, update lap result
        // ...
    } catch (e: Exception) {
        // If any exception happens like collision, print a message.
        println("Issue ${e.message} for driver ${result.driver.name}.")
    } finally {
        // Regardless of whether an exception was thrown or not,
        // this block of code will always be executed.
        println("Lap for ${result.driver.name} has concluded.")
    }
}

fun exampleMultipleCatch(driver: Driver, car: RaceCar) {
    try {
        // Here we simulate a lap. During this, disruptive events may occur.
        val lapTime = simulateLap(driver, car)
        // ...
    } catch (e: SafetyCarException) {
        // On SafetyCarException, safety car deployment is required.
        // We log this event and slow down lap times.
        println("${e.message} Safety car deployed.")
        slowDownLapTimes()
    } catch (e: YellowFlagException) {
        // YellowFlagException indicates track hazard requiring caution.
        // Similarly, we log the event and slow down lap times.
        println("${e.message} Yellow flag raised.")
        slowDownLapTimes()
    }
}

fun slowDownLapTimes() {
    // slow down lap times
}

fun simulateLap(driver: Driver, car: RaceCar) =
    Random.nextFloat()

data class Result(
    val team: Team,
    val driver: Driver,
    val car: RaceCar,
    var totalLapTime: Double = 0.0,
    var fastestLap: Double = Double.MAX_VALUE,
)

data class Driver(
    val raceResults: List<ExampleContinueToLabel.RaceResult> = listOf(),
    val name: String,
    val car: RaceCar = RaceCar(
        carNumber = 1,
        numLaps = 5
    ),
)

class Team()
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
)

sealed class RaceOutcome {
    data class Success(val lapTime: Double) :
        RaceOutcome()

    object PitStopNeeded : RaceOutcome()
    object SafetyCarDeployed : RaceOutcome()
}

enum class RaceEvent {
    NORMAL,
    BREAKDOWN,
    COLLISION,
}

class ExampleAlternativeExceptionHandling() {
    private fun generateRaceEvent() = RaceEvent.NORMAL
    private fun calculateLapTime(
        driver: Driver,
        car: RaceCar,
    ): Double = 0.0

    fun simulateLap(
        driver: Driver,
        car: RaceCar,
    ): RaceOutcome {
        return when (generateRaceEvent()) {
            RaceEvent.BREAKDOWN -> {
                car.isPitStopNeeded = true
                RaceOutcome.PitStopNeeded
            }

            RaceEvent.COLLISION -> {
                car.isPitStopNeeded = true
                RaceOutcome.SafetyCarDeployed
            }

            RaceEvent.NORMAL -> {
                val lapTime =
                    calculateLapTime(driver, car)
                RaceOutcome.Success(lapTime)
            }
        }
    }
}


fun main() {
    // Usage example
    val driver = Driver(name = "Verstappen")
    val raceCar = RaceCar(carNumber = 1, numLaps = 5)
    val lapOutcome =
        ExampleAlternativeExceptionHandling().simulateLap(
            driver,
            raceCar
        )
    when (lapOutcome) {
        is RaceOutcome.Success -> println("Lap time: ${lapOutcome.lapTime}")
        is RaceOutcome.PitStopNeeded -> println("Pit stop!")
        is RaceOutcome.SafetyCarDeployed -> println("Slow down.")
    }
}

