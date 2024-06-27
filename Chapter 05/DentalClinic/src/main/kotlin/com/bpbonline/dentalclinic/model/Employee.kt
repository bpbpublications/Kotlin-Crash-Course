package com.bpbonline.dentalclinic.model

import java.util.*

interface Employee {
    val role: String
    fun scheduleLeave(startDate: Date, endDate: Date)
}