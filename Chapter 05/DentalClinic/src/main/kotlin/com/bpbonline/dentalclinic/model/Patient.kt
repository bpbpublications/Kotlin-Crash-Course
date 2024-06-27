package com.bpbonline.dentalclinic.model

class Patient(
    id: String,
    name: String,
) : Person(id, name) {
    var insurance: String? = null
    lateinit var dentalRecord: DentalRecord

    init {
        println("Patient Initialized with id: $id and name: $name")
    }

    constructor(id: String, name: String, insurance: String)
            : this(id, name) {
        println("Secondary constructor called. Insurance: $insurance")
        this.insurance = insurance
    }

    fun receiveTreatment(treatment: Treatment) {
        if (!::dentalRecord.isInitialized) {
            dentalRecord = DentalRecord(id)
        }
        println("Patient $name is receiving ${treatment.name}")
        dentalRecord.addTreatment(treatment)
        // additional code to handle the patient's response or actions
    }

    override fun introduce() = "${super.introduce()} I am a patient."
}
