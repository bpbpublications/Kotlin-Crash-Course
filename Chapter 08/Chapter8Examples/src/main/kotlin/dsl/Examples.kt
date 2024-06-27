package dsl

@DslMarker
annotation class TourDsl

@TourDsl
class TourPackage {
    var departureDate: String = ""
    var destination: String = ""
    var hotel: String = ""
    val flights = mutableListOf<FlightDetails>()

    fun flightDetails(block: FlightDetails.() -> Unit) {
        val flight = FlightDetails().apply(block)
        // Add flight to the tour package
        flights.add(flight)
    }
}

@TourDsl
class FlightDetails {
    var airline: String = ""
    var flightNumber: String = ""
}


fun tourPackage(block: TourPackage.() -> Unit): TourPackage =
    TourPackage().apply(block)

// Usage
val myTrip = tourPackage {
    departureDate = "2024-01-01"
    destination = "Paris"
    hotel = "Eiffel Tower Inn"

    flightDetails {
        airline = "Air France"
        flightNumber = "AF256"
    }
}