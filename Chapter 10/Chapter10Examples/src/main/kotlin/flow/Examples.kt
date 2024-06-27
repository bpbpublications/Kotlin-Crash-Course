package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class Car(val id: String) {
    // Function that emits a flow of lap times
    fun lapTimesFlow(): Flow<Int> = flow {
        for (lap in 1..10) {
            // Asynchronously measure lap time
            val lapTime = measureLapTime()
            emit(lapTime) // Emit the lap time
        }
    }

    // Simulate measuring the lap time
    private suspend fun measureLapTime(): Int {
        delay(1000) // Simulate the delay between laps
        // Some complex logic to measure lap time
        return (100..200).random()
    }
}

// Usage of the flow in the main function
fun main() = runBlocking {
    val car = Car("Car 1")
    car.lapTimesFlow().collect { lapTime ->
        println("Car ${car.id} completed the lap in $lapTime seconds")
    }
}

class F1Race {
    private val raceLeaderFlow = MutableStateFlow<Car?>(null)

    fun updateLeader(newLeader: Car) {
        raceLeaderFlow.value = newLeader
    }

    fun getLeaderFlow(): Flow<Car> = raceLeaderFlow.filterNotNull()
}

class DashboardUI {
    fun displayRaceLeader(leaderFlow: Flow<Car>) {
        CoroutineScope(Dispatchers.Main).launch {
            leaderFlow.collect { leader ->
                println("Current race leader: Car ${leader.id}")
            }
        }
    }
}
