package sealedclasses

sealed class PatientStatus {
    object Waiting : PatientStatus()
    data class UnderTreatment(val treatment: String) :
        PatientStatus()

    object Discharged : PatientStatus()
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

fun processSchedulingResult(result: ScheduleResult) {
    when (result) {
        is ScheduleResult.Success -> handleSuccess(result.appointmentId)
        is ScheduleResult.DentalPractitionerUnavailable ->
            handlePractitionerUnavailability(
                result.availableDentalPractitioners
            )
        is ScheduleResult.TreatmentUnavailable ->
            handleTreatmentUnavailability(result.availableTreatments)
        is ScheduleResult.NotFound -> handleNotFound(result.message)
        is ScheduleResult.TimeSlotUnavailable -> handleTimeSlotUnavailability()
    }
}

fun handleTimeSlotUnavailability() {
}

fun handleNotFound(message: String) {
}

fun handleTreatmentUnavailability(availableTreatments: List<Treatment>) {
}

fun handlePractitionerUnavailability(
    availableDentalPractitioners: List<DentalPractitioner>,
) {
}

fun handleSuccess(appointmentId: String) {
}

class DentalPractitioner
class Treatment