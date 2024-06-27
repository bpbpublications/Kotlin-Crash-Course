package infix

data class Route(
    val origin: String,
    val destination: String,
)

// Define an infix function
infix fun Route.connectsTo(other: Route): Route {
    return Route(this.destination, other.destination)
}

val route1 = Route("New York", "London")
val route2 = Route("London", "Paris")

// Use the infix function
val combinedRoute = route1 connectsTo route2

data class RewardPoints(val points: Int) {
    // Define plus and minus operators on RewardPoints
    operator fun plus(other: RewardPoints) =
        RewardPoints(points + other.points)
    operator fun minus(other: RewardPoints) =
        RewardPoints(points - other.points)
}
val pointsFromTrip1 = RewardPoints(50)
val pointsFromTrip2 = RewardPoints(30)
// Use the plus operator on RewardPoints
val totalPoints = pointsFromTrip1 + pointsFromTrip2



