package classesandobjects

import java.time.LocalDateTime

class Patient(val id: String, val name: String) {
    var insurance: String? = null
    lateinit var dentalRecord: DentalRecord

    // This will be called as part of initialization
    init {
        println("Patient Initialized with id: $id and name: $name")
    }

    // Secondary constructor
    constructor(
        id: String,
        name: String,
        insurance: String,
    )
            : this(id, name) {
        println("Secondary constructor called. Insurance: $insurance")
        this.insurance = insurance
    }

    fun receiveTreatment(treatment: Treatment) {
        if (!::dentalRecord.isInitialized) {
            dentalRecord = DentalRecord(id)
        }
        // The rest of the method...
    }
}

fun usePrimaryConstructorWithDefaults() {
    class Patient(
        val id: String,
        val name: String,
        var insurance: String? = null,
    ) {
        lateinit var dentalRecord: DentalRecord

        init {
            println("Patient Initialized with id: $id and name: $name")
            if (insurance != null) {
                println("Insurance: $insurance")
            }
        }
    }
}



class Appointment(
    val id: String,
    val patient: Patient,
    val dentist: Dentist,
    val time: LocalDateTime,
    val treatment: Treatment,
)

fun exampleReferenceAndIdentity() {
    val patient1 = Patient("1", "John Doe")
    val patient2 = patient1

    if (patient1 === patient2) {
        println("patient1 and patient2 are identical.") // this is printed
    } else {
        println("patient1 and patient2 are not identical.")
    }

    val patient3 = Patient("1", "John Doe")

    if (patient1 === patient3) {
        println("patient1 and patient3 are identical.")
    } else {
        println("patient1 and patient3 are not identical.") // this is printed
    }
}

class Dentist(val id: String, val name: String) {
    var treatmentCount = 0
        private set
    //...
}

class DentalRecord(val patientId: String) {
    private val _treatmentHistory =
        mutableListOf<Treatment>()
    val treatmentHistory: List<Treatment>
        get() = _treatmentHistory

    fun addTreatment(treatment: Treatment) {
        _treatmentHistory.add(treatment)
    }
}

class Treatment(
    val id: String,
    val name: String,
    val duration: Int,
)