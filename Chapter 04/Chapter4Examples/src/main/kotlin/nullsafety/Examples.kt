package nullsafety

import kotlin.random.Random

class RaceCar(
    val carNumber: Int,
    val manufacturer: String? = null,
    val maxSpeed: Double = Random.nextDouble(
        200.0,
        230.0
    ),
    private var currentSpeed: Double = 0.0,
    internal var currentLap: Int = 0,
    internal var isPitStopNeeded: Boolean = false,
    numLaps: Int,
) {
    var lapTimes = arrayOfNulls<Double>(numLaps)
        private set
}

val raceCar = RaceCar(carNumber = 1, numLaps = 5)

fun exampleIfNullCheck() {
    if (raceCar.manufacturer != null) {
        // this is allowed as we've performed a null check
        val length = raceCar.manufacturer.length
    }

}

val manufacturerLength = raceCar.manufacturer?.length

val manufacturerLengthUpperCase = raceCar.manufacturer
    ?.uppercase()
    ?.trim()

fun exampleLet() {
    raceCar.manufacturer?.let { println("The manufacturer is $it") }
}

val manufacturer =
    raceCar.manufacturer ?: "Unknown Manufacturer"

fun exampleElvisThrowsException() {
    val manufacturer = raceCar.manufacturer
        ?: throw IllegalStateException("Manufacturer required")
}

fun exampleElvisExecuteCode() {
    val manufacturer = raceCar.manufacturer ?: run {
        // Obtain the default manufacturer
        val default = getDefaultManufacturer()
        // Print message with default manufacturer
        println("Manufacturer not found, using default: $default")
        // The last line in a run block is its return value
        default
    }

}

fun printManufacturer(manufacturer: String) {
    println("Manufacturer: $manufacturer")
}

fun exampleNonNullAssertion() {
    val manufacturerNonNull = raceCar.manufacturer!!
    printManufacturer(manufacturerNonNull)
}

fun printRaceCar(raceCar: RaceCar) {
    if (raceCar.manufacturer != null) {
        // Here, the Kotlin compiler knows manufacturer is not null
        // This works without !! because of smart casting
        printManufacturer(raceCar.manufacturer)
    }
}

/**
 * Try this in a Scratch file, as it treats the project as external module
 */
fun printRaceCar_otherModule(raceCar: RaceCar) {
    if (raceCar.manufacturer != null) {
        printManufacturer(raceCar.manufacturer!!)
    }
}

fun exampleNullCheckInAnotherFunctiom() {
    fun isValid(raceCar: RaceCar) =
        raceCar.manufacturer != null

    fun printRaceCar(raceCar: RaceCar) {
        if (isValid(raceCar)) {
            printManufacturer(raceCar.manufacturer!!)
        }
    }
}

fun printDetails(obj: Any) {
    val car = obj as? RaceCar
    car?.let {
        println("RaceCar details: Max Speed: ${it.maxSpeed}")
    }
}

fun printRaceCarDetails(obj: Any) {
    val car = obj as? RaceCar ?: getDefaultRaceCar()
    println("RaceCar details: Max Speed: ${car.maxSpeed}")
}

//----

fun getDefaultRaceCar() = raceCar

fun getDefaultManufacturer() = "default"

class Sponsor(val name: String, val amount: Double)
class Driver(val name: String, var sponsor: Sponsor?)

val driver = Driver("Verstappen", null)

// Accessing the sponsor details safely
val sponsorName = driver.sponsor?.name ?: "No Sponsor"
val sponsorshipAmount = driver.sponsor?.amount ?: 0.0

fun checkForNullWithLetExample(){
    // Safer approach using let, even for mutable properties
    if(driver.sponsor!= null){
        // The captured consistentSponsor is a fixed reference for this block
        processSponsorship(driver.sponsor!!)
    }
}


fun processSponsorship(sponsor: Sponsor) {
    TODO("Not yet implemented")
}