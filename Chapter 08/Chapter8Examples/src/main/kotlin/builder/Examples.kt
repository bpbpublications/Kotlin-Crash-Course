package builder

data class TourPackage(
    val departureDate: String,
    val destination: String,
    val hotel: String
) {
    class Builder {
        private var departureDate: String = ""
        private var destination: String = ""
        private var hotel: String = ""

        fun departureDate(date: String) = apply { this.departureDate = date }
        fun destination(destination: String) = apply { this.destination = destination }
        fun hotel(hotel: String) = apply { this.hotel = hotel }
        fun build() = TourPackage(departureDate, destination, hotel)
    }
}

// Usage
val myTrip = TourPackage.Builder()
    .departureDate("2024-01-01")
    .destination("Paris")
    .hotel("Eiffel Tower Inn")
    .build()