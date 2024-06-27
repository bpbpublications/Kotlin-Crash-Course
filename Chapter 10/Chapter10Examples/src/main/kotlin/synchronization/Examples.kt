package synchronization

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap


class RaceStandings {
    private val mutex = Mutex()
    private val standings = mutableMapOf<Car, Int>()

    // A function that updates the standings safely using a mutex.
    suspend fun updateStandings(car: Car, position: Int) {
        mutex.withLock {
            standings[car] = position
        }
    }

    // Retrieves a snapshot of the standings in a thread-safe manner.
    suspend fun getStandings(): Map<Car, Int> {
        return mutex.withLock {
            // Return a copy to avoid shared mutable state
            standings.toMap()
        }
    }

}


typealias RaceResults = ConcurrentHashMap<Car, RaceResult>

class Race(val raceParameters: RaceParameters) {
    private val raceResults = RaceResults()

    fun startRace() {
        // Using Dispatchers.Default for background processing
        CoroutineScope(Dispatchers.Default).launch {
            raceParameters.cars.forEach { car ->
                launch {
                    val result = simulateRaceForCar(car)
                    raceResults[car] = result // Thread-safe update
                }
            }
        }
    }

    fun updateUIWithLatestResults() {
        // Confining UI updates to the Main thread
        CoroutineScope(Dispatchers.Main).launch {
            // Assume latestRaceResults is updated elsewhere in response to race progress.
            val latestResults = fetchLatestRaceResults()
            updateRaceStandingsUI(latestResults)
        }
    }

    // Fetch the most current race results.
    private fun fetchLatestRaceResults(): RaceResults = raceResults

    private fun simulateRaceForCar(car: Car): RaceResult = RaceResult()
    private fun updateRaceStandingsUI(raceResults: ConcurrentHashMap<Car, RaceResult>) {

    }
}

class Car {

}

class RaceResult {

}

data class RaceParameters (
    val cars: List<Car>
)
