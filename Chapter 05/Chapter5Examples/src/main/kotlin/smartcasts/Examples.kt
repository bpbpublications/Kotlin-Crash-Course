package smartcasts

import java.time.LocalDateTime

inline fun <reified T> findPersonAcrossManagers(
    id: String,
    vararg managers: PersonManager<*>,
): T? {
    for (manager in managers) {
        val person = manager.getPersonById(id)
        if (person != null && person is T) {
            return person
        }
    }
    return null
}

val result =
    ScheduleResult.DentalPractitionerUnavailable(
        listOf(DentalPractitioner("123"))
    )
val message: String = when (result) {
    // ...
    is ScheduleResult.DentalPractitionerUnavailable -> {
        val ids =
            result.availableDentalPractitioners.map { it.id }
        "Practitioner unavailable! Available practitioners: $ids"
    }

    else -> "Other"
    // ...
}

val patients = PersonManager<Patient>()

fun scheduleAppointment(
    patientId: String,
    // ... other parameters
): ScheduleResult {
    val patient: Patient? =
        patients.getPersonById(patientId)

    // Patient is smart cast to non-nullable after null check.
    patient
        ?: return ScheduleResult.NotFound("Patient not found")

    // Following the null check, use patient as non-nullable.
    return ScheduleResult.Success("Patient is found: ${patient.id}")
}

fun isDentalPractitionerAvailable(
    dentalPractitionerId: String,
    time: LocalDateTime,
): Boolean {
    TODO()
}

class Appointment(
    patient: Patient,
    dentalPractitionerId: String,
    time: LocalDateTime,
    treatmentId: String,
)


class PersonManager<T : Person> {
    private val persons = mutableMapOf<String, T>()
    fun getPersonById(id: String): T? = persons[id]
}


sealed interface ScheduleResult {
    data class Success(
        val appointmentId: String,
    ) : ScheduleResult

    data class DentalPractitionerUnavailable(
        val availableDentalPractitioners: List<DentalPractitioner>,
    ) : ScheduleResult

    data class TreatmentUnavailable(
        val availableTreatments: List<Treatment>,
    ) : ScheduleResult

    data class NotFound(val message: String) :
        ScheduleResult

    object TimeSlotUnavailable : ScheduleResult
}

open class Person
class DentalPractitioner(val id: String)
class Treatment
class Patient(val id: String, val name: String) :
    Person() {

}