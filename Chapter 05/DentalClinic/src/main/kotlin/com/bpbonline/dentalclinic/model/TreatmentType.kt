package com.bpbonline.dentalclinic.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

enum class TreatmentType(
    val displayName: String,
    val duration: Duration,
) {
    CHECK_UP("Check-up", 30.minutes),
    CLEANING("Cleaning", 60.minutes),
    TOOTH_EXTRACTION("Tooth Extraction", 90.minutes),
    FILLING("Filling", 120.minutes),
    ROOT_CANAL("Root Canal", 120.minutes);
}
