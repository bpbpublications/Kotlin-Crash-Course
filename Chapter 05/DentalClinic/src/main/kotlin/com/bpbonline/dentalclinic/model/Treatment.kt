package com.bpbonline.dentalclinic.model

data class Treatment(
    val id: String,
    val name: String,
    val type: TreatmentType,
) {
    val duration = type.duration

    companion object {
        fun teethCleaning() = Treatment(
            "T001",
            "Teeth Cleaning",
            TreatmentType.CLEANING
        )

        fun cavityFilling() = Treatment(
            "T002",
            "Cavity Filling",
            TreatmentType.FILLING
        )

        fun rootCanal() = Treatment(
            "T003",
            "Root Canal",
            TreatmentType.ROOT_CANAL
        )
    }

}
