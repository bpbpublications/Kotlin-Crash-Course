package atomic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

fun main(): Unit =
    runBlocking { // Start a coroutine scope for the main function
        val cars = listOf(
            Car("Car 1"),
            Car("Car 2"),
            Car("Car 3")
        )

        val jobs = cars.map { car ->
            launch(Dispatchers.Default) {
                repeat(10) { // Simulate 10 laps
                    delay(100L) // Simulate time taken for a lap
                    car.completeLap()
                    println("${car.id} completed lap ${car.getLapsCompleted()}")
                }
            }
        }
        jobs.forEach { it.join() } // Wait for all coroutines (cars) to complete their execution
    }

class Car(val id: String) {
    private var _time = AtomicReference(0.0)
    val time
        get() = _time.get()
    private val lapsCompleted = AtomicInteger(0)

    fun completeLap() {
        lapsCompleted.incrementAndGet()
    }

    fun getLapsCompleted(): Int = lapsCompleted.get()
}

class RaceStatus {
    private val currentLeader = AtomicReference<Car>()

    fun updateLeader(newLeader: Car) {
        currentLeader.updateAndGet { existingLeader ->
            if (existingLeader == null
                || newLeader.time < existingLeader.time
            ) newLeader
            else existingLeader
        }
    }

    fun getLeader(): Car? = currentLeader.get()
}
