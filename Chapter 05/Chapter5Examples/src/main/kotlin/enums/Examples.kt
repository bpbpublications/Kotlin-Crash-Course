package enums

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

enum class TreatmentTypeWithMembers(val displayName: String) {
    CHECK_UP("Check-up") {
        override fun getDuration() = 30.minutes
    };

    // the rest of the enums
    abstract fun getDuration(): Duration
}

enum class TreatmentType {
    CHECK_UP,
    CLEANING,
    TOOTH_EXTRACTION,
    FILLING,
    ROOT_CANAL
}

