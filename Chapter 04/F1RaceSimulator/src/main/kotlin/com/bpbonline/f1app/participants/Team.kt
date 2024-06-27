package com.bpbonline.f1app.participants

import kotlin.random.Random

class Team(
    val name: String,
    val drivers: List<Driver>,
    val raceCars: Set<RaceCar>,
    val mainSponsor: Sponsor? = getRandomSponsor(),
) {
    val driverCarMap: Map<Driver, RaceCar> = drivers
        .zip(raceCars)
        .toMap()

    override fun toString(): String {
        return "Team(name='$name', mainSponsor=$mainSponsor, driverCarMap=$driverCarMap)"
    }

}

fun getRandomSponsor(): Sponsor? {
    // Generate a random number from 0 to 10
    val randomNumber = Random.nextInt(0, 11)

    // Return null if the random number is 0, otherwise return a Sponsor
    return if (randomNumber == 0) {
        null
    } else {
        Sponsor(
            name = "Sponsor$randomNumber",
            amount = Random.nextDouble(1_000_000.0, 10_000_000.0)
        )
    }
}
