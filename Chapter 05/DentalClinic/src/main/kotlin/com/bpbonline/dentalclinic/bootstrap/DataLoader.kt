package com.bpbonline.dentalclinic.bootstrap

import com.bpbonline.dentalclinic.model.*
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DataLoader {

    @EventListener(ContextRefreshedEvent::class)
    fun populateInitialData() {
        // Adding a Patient
        val patient = Patient("P001", "John Doe")
        Clinic.patients.addPerson(patient)
        // Adding a Hygienist
        val hygienist = Hygienist("DP001", "Ms. Claire")
        Clinic.hygienists.addPerson(hygienist)
        // Adding a Dentist
        val dentist = Dentist("DP002", "Dr. Smith")
        Clinic.dentists.addPerson(dentist)
        // Adding a Treatment using a factory method
        Clinic.addTreatment(Treatment.teethCleaning())
    }
}
