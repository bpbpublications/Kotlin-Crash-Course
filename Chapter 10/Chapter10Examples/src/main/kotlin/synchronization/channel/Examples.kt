package synchronization.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Define a simple data class for race updates
data class LapTime(val carId: String, val lapTime: Long)

// Coroutine that measures lap times for a car & sends them to a channel
fun CoroutineScope.produceLapTimes(
    channel: Channel<LapTime>,
    carId: String,
) = launch {
    for (lap in 1..10) {
        // Simulate measuring lap time
        val lapTime = measureLapTime(carId)
        // Send the lap time to the channel
        channel.send(LapTime(carId, lapTime))
    }
}

// Coroutine that listens for lap times coming in and updates standings
fun CoroutineScope.consumeLapTimes(
    channel: Channel<LapTime>,
) = launch {
    for (lapTime in channel) { // Receive and process each lap time
        updateStandings(lapTime) // Update the race standings
    }
}

// Example main function where you want the simulation to run
fun main(): Unit = runBlocking {
    // Create a channel for lap times
    val lapTimesChannel = Channel<LapTime>()
    // Start producer coroutines for each car to generate lap times
    listOf("Car 1", "Car 2").forEach { carId ->
        produceLapTimes(lapTimesChannel, carId)
    }
    // Start a consumer coroutine to update standings with lap times
    consumeLapTimes(lapTimesChannel)
}

fun updateStandings(lapTime: LapTime) {
}

fun measureLapTime(carId: String): Long = 0
