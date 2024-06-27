package datatypes.basic

import java.math.BigDecimal
import kotlin.random.Random

class Driver(
    val name: String,
    var points: Int = 0,
) {
    fun addPoints(newPoints: Int) {
        points += newPoints
    }
}

class RaceCar(
    val carNumber: Int,
    val maxSpeed: Double = Random.nextDouble(
        200.0,
        230.0
    ),
    internal var isPitStopNeeded: Boolean = false,
    numLaps: Int,
) {
    var lapTimes = arrayOfNulls<Double>(numLaps)
}

class Race {
    // ...
    data class Result(
        // ...
        var totalLapTime: Double = 0.0,
        var fastestLap: Double = Double.MAX_VALUE,
    )
}

class Team(
    val name: String,
    // ...
)

val car1 = RaceCar(carNumber = 1, numLaps = 5)
val car2 = RaceCar(carNumber = 2, numLaps = 5)
val car3 = RaceCar(carNumber = 3, numLaps = 5)
val car4 = RaceCar(carNumber = 4, numLaps = 5)


val team1Cars = arrayOf(car1, car2)
val team2Cars = arrayOf(car3, car4)
val allCars = team1Cars + team2Cars

data class Result(
    val team: Team,
    val driver: Driver,
    val car: RaceCar,
    var totalLapTime: Double = 0.0,
    var fastestLap: Double = Double.MAX_VALUE,
)

fun exampleComparison(lapTime: Double) {
    val result = Result(
        team = Team("RedBull"),
        driver = Driver("Verstappen"),
        car1
    )
    if (lapTime < result.fastestLap) {
        result.fastestLap = lapTime
    }
}

fun exampleNotOperator() {
    if (!car1.isPitStopNeeded) {
        println("Car #${car1.carNumber} is ready to continue the race.")
    }

}

fun main() {
    val totalDistance = 305.0 // Kilometers
    val totalTime = 5400.0 // Seconds
    val averageSpeed =
        totalDistance / (totalTime / 3600) // km/h
    println(averageSpeed)

    var totalInteractions: Int = Int.MAX_VALUE
    // Hypothetical number of interactions per race
    val interactionsPerRaceWeekend = 50_000_000

    try {
        totalInteractions = Math.addExact(
            totalInteractions,
            interactionsPerRaceWeekend,
        )
    } catch (e: ArithmeticException) {
        println("Total interactions exceeded the maximum value of Int.")
    }

    // Earnings from a single race win
    val raceWinEarnings = BigDecimal("50000.95")
    // Number of race wins
    val numberOfWins = BigDecimal("3")
    // Precise calculation
    val totalEarnings = raceWinEarnings.multiply(numberOfWins)
    println("Total Earnings: $$totalEarnings")
}
