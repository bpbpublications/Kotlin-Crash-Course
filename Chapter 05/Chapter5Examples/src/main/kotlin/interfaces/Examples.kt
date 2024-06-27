package interfaces

import java.time.LocalDate
import java.time.Period

interface DentalPractitioner {
    fun performTreatment(patient: Patient, treatment: Treatment): String
}

class Dentist(
    override val id: String,
    override val name: String,
    override val role: String,
) : Person(id, name), Employee, DentalPractitioner {

    var treatmentCount = 0
        private set

    override fun scheduleLeave(start: LocalDate, end: LocalDate) {
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


    //The rest of Dentist class
}

interface Employee {
    val role: String
    fun scheduleLeave(start: LocalDate, end: LocalDate)

    fun calculateLeaveDays(start: LocalDate, end: LocalDate): Int {
        return Period.between(start, end).days
    }
}


class Patient(
    val id: String,
    val name: String,
) {

    fun receiveTreatment(treatment: Treatment) {
        // additional code to handle the patient's response or actions
    }

}

class Treatment(val name: String)
open class Person(
    open val id: String,
    open val name: String,
)
