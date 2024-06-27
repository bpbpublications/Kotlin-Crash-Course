package com.bpbonline.dentalclinic.api.dto

import com.bpbonline.dentalclinic.annotations.ValidPatientId
import java.time.LocalDateTime

data class AppointmentRequest(
    @ValidPatientId
    val patientId: String,
    val dentalPractitionerId: String,
    val dateTime: LocalDateTime,
    val treatmentId: String,
)

data class AppointmentResponse(
    val id: String? = null,
    val message: String,
)

data class AppointmentDetailDTO(
    val dateTime: LocalDateTime,
    val patientId: String,
    val dentalPractitionerId: String,
    val treatmentType: String,
    val durationInMinutes: Long,
)
