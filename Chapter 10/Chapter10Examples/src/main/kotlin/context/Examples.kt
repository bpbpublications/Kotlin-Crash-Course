package context

import kotlinx.coroutines.*

val myContext = Dispatchers.Default +
        CoroutineName("MyCoroutine") +
        CoroutineExceptionHandler { _, throwable ->
            println("Exception handled: ${throwable.localizedMessage}")
        }

class F1RaceSimulation(val raceParameters: RaceParameters) {

    fun startRace() {
        val raceContext = Dispatchers.Default +
                CoroutineName("RaceCoroutine") +
                CoroutineExceptionHandler { _, exception ->
                    println("Race error: ${exception.localizedMessage}")
                }
        CoroutineScope(raceContext).launch {
            // Race logic with the specific context
            raceParameters.cars.forEach { car ->
                launch(Dispatchers.IO + CoroutineName("Car#${car.id}")) {
                    trackCar(car)
                }
            }
        }
    }

    private suspend fun trackCar(car: Car) {
        // Tracking logic for each car
    }
}

data class RaceParameters(
    val cars: List<Car>,
)

data class Car(
    val id: String,
)

fun contextSwitchingExample(){
    class F1RaceSimulation(val raceParameters: RaceParameters) {
        fun startRace() {
            // Race initialization code ...
            raceParameters.cars.forEach { car ->
                CoroutineScope(Dispatchers.Default).launch {
                    val pitStopStrategy = calculatePitStopStrategy(car)
                    // Switch to the main thread to update the UI
                    withContext(Dispatchers.Main) {
                        updateUIWithStrategy(car, pitStopStrategy)
                    }
                }
            }
        }

        // Background calculation function remains the same
        private suspend fun calculatePitStopStrategy(
            car: Car,
        ): PitStopStrategy {
            // Complex calculation logic
            return PitStopStrategy() // Placeholder for the actual strategy
        }

        // UI update function now uses Dispatchers.Main
        private suspend fun updateUIWithStrategy(
            car: Car,
            strategy: PitStopStrategy,
        ) {
            // Update UI elements with the new strategy
            // ... UI update logic ...
        }
    }
}

class PitStopStrategy

