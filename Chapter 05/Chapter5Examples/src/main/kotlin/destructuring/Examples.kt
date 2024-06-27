package destructuring

import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val patient: Patient,
    val dentalPractitioner: DentalPractitioner,
    val time: LocalDateTime,
    val treatment: Treatment,
)

val appointment = Appointment(
    "123",
    Patient(),
    DentalPractitioner(),
    LocalDateTime.now(),
    Treatment()
)

fun exampleDestructuring() {
    val (_, patient, practitioner, time, treatment) = appointment
    println("Appointment: $patient, $practitioner - $time, $treatment.")
}

fun exampleDestructuringAndVariableAssignment() {
    val appointment = Clinic.getAppointmentById("123")
    val (_, patient, practitioner, time, treatment) = appointment

}

fun exampleDestructuringAndLambdaFunctions() {
    val treatments = mapOf("123" to Treatment())
    treatments.forEach { (id, treatment) ->
        println("Treatment ID: $id, Details: $treatment")
    }
}

fun exampleDestructuringAndLoops() {
    val appointments = listOf(appointment)
    for ((_, patient, practitioner, time, treatment) in appointments) {
        println("Appointment: $patient, $practitioner - $time, $treatment.")
    }
}

object Clinic {
    fun getAppointmentById(appointmentId: String) =
        appointment
}

class Patient
class DentalPractitioner
class Treatment

