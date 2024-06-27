package channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

// Example coroutine that processes sensor data
fun CoroutineScope.sensorDataProcessor(
    inputChannel: ReceiveChannel<SensorReading>,
    outputChannel: SendChannel<ProcessedData>,
) = launch {
    // Coroutine's body that processes the sensor data
}

class ProcessedData {

}

class SensorReading {

}


// Define the Car class with the necessary functions
class Car(val number: String) {
    var inPitStop = false

    fun needsPitStop() = inPitStop

    fun pitStopComplete() {
        inPitStop = false
    }

    suspend fun awaitPitStopCompletion() {
        while (inPitStop) {
            delay(100) // Wait for pit stop to complete
        }
    }
}

// Channel to act as a pit stop queue
val pitStopChannel = Channel<Car>(Channel.UNLIMITED)

// Sample list of race cars, some need a pit stop, others do not.
val raceCars = listOf(
    Car("1").apply { inPitStop = true },
    Car("2"),
    Car("3").apply { inPitStop = true }
)

fun CoroutineScope.serviceCars() = launch {
    for (car in pitStopChannel) {
        // Process the pit stop for the car
        performPitStop(car)
        // Notify the car when the pit stop is complete
        car.pitStopComplete()
    }
}

fun CoroutineScope.checkCars() = launch {
    raceCars.forEach { car ->
        launch {
            if (car.needsPitStop()) {
                pitStopChannel.send(car)
                car.awaitPitStopCompletion()
                println("Car ${car.number} has finished the pit stop and continues racing.")
            }
        }
    }
}

suspend fun performPitStop(car: Car) {
    println("Car ${car.number} is in the pit stop.")
    delay(5000) // Simulate time taken for the pit stop
    println("Car ${car.number} has completed the pit stop.")
}

class F1RaceTeam {
    val strategyChannel = Channel<String>()

    // Function to send strategic commands to the driver
    fun sendStrategyCommand(command: String) {
        CoroutineScope(Dispatchers.Default).launch {
            strategyChannel.send(command)  // Commands are sent to the driver via the channel
        }
    }

    // Receives and executes commands sent by the race team
    fun driverReceiveCommands() =
        CoroutineScope(Dispatchers.Default).launch {
            for (command in strategyChannel) {
                println("Driver receives strategy command: $command")
                // Driver acts on the command
            }
        }
}

fun CoroutineScope.channelExampleSendReceive(){
    val strategyChannel = Channel<String>()

    // Launch a coroutine to send strategy messages
     launch {
        val strategies = listOf(
            "Box this lap",
            "Overtake",
            "Conserve tyres",
            "Fuel saving mode"
        )
        for (strategy in strategies) {
            // Send a strategy
            strategyChannel.send(strategy)
            // Time taken to evaluate and send the next strategy
            delay(1000)
        }

    }

    // Driver’s coroutine receives and acts upon strategies from the pit wall
    launch {
        repeat (4) {// receive 4 strategies sent
            println("Driver action: ${strategyChannel.receive()}")
        }
    }
}

// The main function to run the simulation
fun main() = runBlocking {
    val pitStopServiceJob = serviceCars()
    val carCheckJob = checkCars()

    // Wait for all cars to be serviced
    pitStopServiceJob.join()
    carCheckJob.join()

    // channels
    val raceTeam = F1RaceTeam()
    // Set up the driver to receive commands
    raceTeam.driverReceiveCommands()
    // Simulate sending strategic commands during the race
    raceTeam.sendStrategyCommand("Pit this lap.")
    raceTeam.sendStrategyCommand("Switch to plan B.")
    raceTeam.sendStrategyCommand("Conserve fuel.")
}

