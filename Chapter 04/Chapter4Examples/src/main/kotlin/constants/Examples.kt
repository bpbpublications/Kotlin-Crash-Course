package constants

const val MAX_LAPS = 5
const val MAX_TEAMS = 10
// Computed at compile time
const val MAX_DRIVERS = MAX_TEAMS * 2

class RaceCar {
    // ...
    companion object {
        const val PITSTOP_TIME = 5.0 // 5 minutes
    }
}
