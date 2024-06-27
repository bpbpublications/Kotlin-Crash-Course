package objectkeyword

import java.time.Duration
import java.time.LocalDateTime
import java.util.*

object Clinic {
    val patients = mutableMapOf<String, Patient>()
    val dentists = mutableMapOf<String, Dentist>()
    val treatments = mutableMapOf<String, Treatment>()
    val appointments = mutableListOf<Appointment>()
    // The rest of the object
    fun addTreatment(treatment: Treatment) =
        treatments.put(treatment.id, treatment)

    fun scheduleAppointment(
        patientId: String,
        dentalPractitionerId: String,
        time: LocalDateTime,
        treatmentId: String,
    ): ScheduleResult {
        TODO()
    }
}

class ScheduleResult{}
data class Treatment(
    val id: String,
    val name: String,
    val type: TreatmentType,
) {
    val duration = type.duration

    companion object {
        fun teethCleaning() = Treatment(
            UUID.randomUUID().toString(),
            "Teeth Cleaning",
            TreatmentType.CLEANING
        )
        // other factory methods
    }
}

val appointmentListener = object : AppointmentListener {
    override fun onAppointmentScheduled(appointment: Appointment) {
        // handle the event
    }
}

interface AppointmentListener {
    fun onAppointmentScheduled(appointment: Appointment)
}

enum class TreatmentType(
    val displayName: String,
    val duration: Duration,
) {
    CHECK_UP("Check-up", Duration.ofMinutes(30)),
    CLEANING("Cleaning", Duration.ofMinutes(60)),
}

class Patient
class Dentist
class Appointment