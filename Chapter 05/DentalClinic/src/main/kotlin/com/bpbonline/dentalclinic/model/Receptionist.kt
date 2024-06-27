package com.bpbonline.dentalclinic.model

import java.util.*

class Receptionist(
    id: String,
    name: String,
    override val role: String = "Receptionist",
) : Person(id, name), Employee {

    override fun scheduleLeave(start: Date, end: Date) {
        println("Receptionist $name scheduled leave from $start to $end.")
    }
    // Receptionist specific methods here
}
