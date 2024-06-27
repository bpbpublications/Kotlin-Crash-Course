package variables.immutable

import java.util.*
import kotlin.random.Random

class RaceCar(
    val carNumber: Int,
    val maxSpeed: Double = Random.nextDouble(200.0, 230.0),
    // ...
)

class Driver(
    val name: String,
    var points: Int = 0,
    val uuid: UUID = UUID.randomUUID()
) {
    //...
}

