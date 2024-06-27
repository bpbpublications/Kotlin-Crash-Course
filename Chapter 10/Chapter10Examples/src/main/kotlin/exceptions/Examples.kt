package exceptions

import kotlinx.coroutines.*

class CarBreakdownException(message: String) :
    Exception(message)

class Race(
    // ... existing properties ...
    val numberOfLaps: Int,
) {
    // ... existing methods ...

    private suspend fun simulateRaceForCar(car: Car) {
        for (lap in 1..numberOfLaps) {
            try {
                val lapTime = simulateLap(car)
                updateResults(car, lapTime)
            } catch (e: CarBreakdownException) {
                handleBreakdown(car, e)
            }
        }
    }

    private fun simulateLap(car: Car): Double {
        throw CarBreakdownException("Car broken")
    }

    private suspend fun updateResults(
        car: Car,
        lapTime: Double,
    ) {
    }

    private fun handleBreakdown(
        car: Car,
        e: CarBreakdownException,
    ) {
        println("Car ${car.carNumber} broke down: ${e.message}")
        // Additional logic to handle the car breakdown.
    }

    fun CoroutineScope.simulateRaceForAllCars(cars: List<Car>) {
        val supervisor = SupervisorJob()
        cars.forEach { car ->
            launch(supervisor + Dispatchers.Default) {
                // Sometimes throws CarBreakdownException
                simulateRaceForCar(car)
            }
        }
    }

    suspend fun simulateRaceForAllCars(cars: List<Car>) {
        supervisorScope {
            cars.forEach { car ->
                launch() {
                    simulateLap(car)
                }
            }
        }
    }


    // ... existing methods ...
}

class Car(val carNumber: String)

val exceptionHandler =
    CoroutineExceptionHandler { _, exception ->
        println("An uncaught exception occurred: $exception")
    }

fun runRace() = runBlocking(exceptionHandler) {
    // Coroutine logic...
}

fun main() = runBlocking {
    Race(1).simulateRaceForAllCars(listOf(Car("1"), Car("2")))
}

