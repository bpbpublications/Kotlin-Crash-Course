package com.bpbonline.dentalclinic.service.impl

import com.bpbonline.dentalclinic.service.AppointmentScheduler
import org.springframework.stereotype.Service

@Service("defaultScheduler")
class DefaultAppointmentScheduler :
    AppointmentScheduler {

    override fun successMessage() = "Scheduled on a weekday!"
}
