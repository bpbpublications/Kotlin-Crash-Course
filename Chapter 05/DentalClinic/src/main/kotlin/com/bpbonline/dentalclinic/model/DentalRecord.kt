package com.bpbonline.dentalclinic.model

class DentalRecord(val patientId: String) {
    private val _treatmentHistory = mutableListOf<Treatment>()
    val treatmentHistory: List<Treatment>
        get() = _treatmentHistory

    fun addTreatment(treatment: Treatment) {
        _treatmentHistory.add(treatment)
    }
}
