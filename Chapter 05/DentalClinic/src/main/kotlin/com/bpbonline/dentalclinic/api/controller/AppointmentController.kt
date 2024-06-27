package com.bpbonline.dentalclinic.api.controller

import com.bpbonline.dentalclinic.api.dto.AppointmentDetailDTO
import com.bpbonline.dentalclinic.api.dto.AppointmentRequest
import com.bpbonline.dentalclinic.api.dto.AppointmentResponse
import com.bpbonline.dentalclinic.service.AppointmentQueryService
import com.bpbonline.dentalclinic.service.AppointmentScheduler
import com.bpbonline.dentalclinic.service.impl.DefaultAppointmentScheduler
import com.bpbonline.dentalclinic.service.impl.WeekendAppointmentScheduler
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.DayOfWeek
import java.time.LocalDateTime

@RestController
@RequestMapping("/appointments")
class AppointmentController(
    val appointmentSchedulers: List<AppointmentScheduler>,
    val appointmentQueryService: AppointmentQueryService,
) {
    @PostMapping
    fun scheduleAppointment(
        @Valid @RequestBody appointmentRequest: AppointmentRequest,
    ): ResponseEntity<AppointmentResponse> {
        val appointmentScheduler =
            findSchedulerFor(appointmentRequest.dateTime)
        val appointmentResult =
            appointmentScheduler.schedule(appointmentRequest)
        return if (appointmentResult.id != null)
            ResponseEntity.ok(appointmentResult)
        else ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(appointmentResult)

    }

    @GetMapping("/{appointmentId}")
    fun getAppointment(
        @PathVariable appointmentId: String,
    ): ResponseEntity<AppointmentDetailDTO> {
        val appointmentDetails =
            appointmentQueryService.getAppointmentById(
                appointmentId
            )
        return if (appointmentDetails != null) {
            ResponseEntity.ok(appointmentDetails)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    private fun findSchedulerFor(
        // Determine the correct scheduler
        date: LocalDateTime,
    ): AppointmentScheduler {
        val weekend =
            listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
        return if (date.dayOfWeek in weekend) {
            appointmentSchedulers.first {
                it is WeekendAppointmentScheduler
            }
        } else {
            appointmentSchedulers.first {
                it is DefaultAppointmentScheduler
            }
        }
    }
}
