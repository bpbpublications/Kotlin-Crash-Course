package extensions

import java.text.SimpleDateFormat
import java.util.*

data class Journey(
    val destinations: List<String>,
    val distanceTraveled: Double,
)

fun Journey.addDestination(destination: String): Journey {
    return Journey(
        this.destinations + destination,
        this.distanceTraveled
    )
}

fun Journey.travelDistance(distance: Double): Journey {
    return Journey(
        this.destinations,
        this.distanceTraveled + distance
    )
}

val journey = Journey(listOf("New York"), 0.0)
val updatedJourney = journey
    .addDestination("Paris")
    .travelDistance(3625.0)


// Extension property
val Journey.hasMultipleDestinations: Boolean
    get() = this.destinations.size > 1

fun exampleExtensionPropertyUsage() {
    val journey =
        Journey(listOf("Amsterdam", "Paris"), 4500.0)
    println("Multiple destinations? ${journey.hasMultipleDestinations}")
}

fun Date.toVoyagerFormat(): String =
    SimpleDateFormat("dd-MM-yyyy 'Voyager Time'").format(
        this
    )

fun exampleDateExtensionFunctionUsage() {
    println(Date().toVoyagerFormat())
}

// Assuming a constant speed of 1000 km per hour
fun Journey.durationInHours() =
    this.distanceTraveled / 1000

fun exampleDurationExtensionFunctionUsage() {
    val journey = Journey(
        listOf("Paris", "Rome"),
        1600.0
    )
    // Print duration
    println("Duration: ${journey.durationInHours()} hours")
}

// A data class representing a travel destination
data class Destination(
    val name: String,
    val price: Double,
    val description: String,
)

// Extension function to apply a discount based on the provided discount rate
fun Destination.applyDiscount(discountRate: Double): Double =
    price * discountRate  // Apply given discount rate

// Extension function to add tax of 6% to a price
fun Double.addTax(): Double = this * 1.06

// Create a new destination
val destination =
    Destination("Paris", 100.0, "Capital of France")

// apply 15% discount, then add tax
val finalPrice = destination.applyDiscount(0.85).addTax()

fun Destination.calculateDistance(
    otherDestination: Destination
): Result<Double> {
    return runCatching {
        // Perform distance calculation, which may throw an exception
        // if the destination is unknown
        computeDistanceTo(otherDestination)
    }
}

fun Destination.computeDistanceTo(otherDestination: Destination): Double =
    throw Exception("Destination $otherDestination is unknown")
