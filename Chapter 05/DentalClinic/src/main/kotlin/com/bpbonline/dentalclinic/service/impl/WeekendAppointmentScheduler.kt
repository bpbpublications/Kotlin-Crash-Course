package com.bpbonline.dentalclinic.service.impl

import com.bpbonline.dentalclinic.service.AppointmentScheduler
import org.springframework.stereotype.Service

@Service("weekendScheduler")
class WeekendAppointmentScheduler :
    AppointmentScheduler {

    override fun successMessage() =
        "Scheduled on a weekend!"
}
