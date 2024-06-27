package com.bpbonline.dentalclinic.model

import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val patient: Patient,
    val dentalPractitioner: DentalPractitioner,
    val time: LocalDateTime,
    val treatment: Treatment,
)
