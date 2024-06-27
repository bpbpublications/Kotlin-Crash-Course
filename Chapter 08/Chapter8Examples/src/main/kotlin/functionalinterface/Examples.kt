package functionalinterface

// Functional interface for booking actions
fun interface BookingAction {
    fun book(trip: Trip)
}

// Instance of the functional interface using a lambda
val action =
    BookingAction { trip -> println("Trip to ${trip.destination}") }

// Usage
fun exampleUseFunInterface() {
    action.book(Trip("Paris"))
}

// Class implementation of the functional interface
class TraditionalBooking : BookingAction {
    override fun book(trip: Trip) {
        println("Trip to ${trip.destination}")
    }
}

// Functional interface for booking actions with operator invoke
fun interface BookingMaker {
    operator fun invoke(trip: Trip)
}

// Instance of the functional interface using a lambda
val makeBooking =
    BookingMaker { trip -> println("Trip to ${trip.destination}") }

fun exampleUseFunInterfaceWithInvoke() {
    // Usage of makeBooking instance as a function
    makeBooking(Trip("Tokyo"))
}


data class Trip(val destination: String)

