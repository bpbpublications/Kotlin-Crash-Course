package com.bpbonline.dentalclinic.model

import java.util.*

class Hygienist(
    id: String,
    name: String,
    override val role: String = "Hygienist",
) : Person(id, name), Employee, DentalPractitioner {

    override fun scheduleLeave(start: Date, end: Date) {
        println("Hygienist $name scheduled leave from $start to $end.")
    }

    override fun performTreatment(
        patient: Patient,
        treatment: Treatment,
    ): String {
        val message = "Hygienist $name is performing ${treatment.name}"
        println(message)
        patient.receiveTreatment(treatment)
        return message
    }
}
