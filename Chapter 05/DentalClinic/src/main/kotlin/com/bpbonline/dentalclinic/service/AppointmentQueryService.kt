package com.bpbonline.dentalclinic.service

import com.bpbonline.dentalclinic.api.dto.AppointmentDetailDTO
import com.bpbonline.dentalclinic.model.Clinic
import org.springframework.stereotype.Service

@Service
class AppointmentQueryService {
    fun getAppointmentById(appointmentId: String): AppointmentDetailDTO? {
        val appointment =
            Clinic.getAppointmentById(appointmentId)
                ?: return null
        // Destructuring declaration extracts specific properties.
        // The underscore skips and ignores unused properties.
        val (_, patient, practitioner, time, treatment) = appointment
        return AppointmentDetailDTO(
            time,
            patient.id,
            practitioner.id,
            treatment.type.displayName,
            treatment.type.duration.inWholeMinutes
        )
    }
}
