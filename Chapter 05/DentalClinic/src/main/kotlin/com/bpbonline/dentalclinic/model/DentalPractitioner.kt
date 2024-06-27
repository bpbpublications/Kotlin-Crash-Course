package com.bpbonline.dentalclinic.model

interface DentalPractitioner {
    val id: String
    fun performTreatment(patient: Patient, treatment: Treatment): String
}