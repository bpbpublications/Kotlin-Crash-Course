package com.bpbonline.dentalclinic.model

import java.util.*

class Dentist(
    id: String,
    name: String,
    override val role: String = "Dentist",
) : Person(id, name), Employee, DentalPractitioner {
    var treatmentCount = 0
        private set

    override fun scheduleLeave(start: Date, end: Date) {
        println("Dentist $name scheduled leave from $start to $end.")
    }

    override fun performTreatment(
        patient: Patient,
        treatment: Treatment,
    ): String {
        val message = "Dentist $name is performing ${treatment.name}"
        println(message)
        treatmentCount++
        // additional code to handle the treatment process
        patient.receiveTreatment(treatment)
        return message
    }


    override fun introduce() = "${super.introduce()} I am a dentist."
}