package polymorphism

interface DentalPractitioner {
    fun performTreatment(
        patient: Patient,
        treatment: Treatment,
    ): String
}

class Dentist(
    override val id: String,
    override val name: String,
) : Person(id, name), DentalPractitioner {
    override fun performTreatment(
        patient: Patient,
        treatment: Treatment,
    ): String {
        // Dentist-specific treatment process
        return "Dentist $name is performing ${treatment.name}"
    }
}

class Hygienist(
    override val id: String,
    override val name: String,
) : Person(id, name), DentalPractitioner {
    override fun performTreatment(
        patient: Patient,
        treatment: Treatment,
    ): String {
        // Hygienist-specific treatment process
        return "Hygienist $name is performing ${treatment.name}"
    }
}

open class Person(
    open val id: String,
    open val name: String,
) {
    open fun introduce() = "Hello, my name is $name."
}

class Patient(
    override val id: String,
    override val name: String,
) : Person(id, name) {
    // Overriding the introduce method
    override fun introduce() =
        "${super.introduce()} I am a patient."
}

fun exampleIntroduceMethod() {
    val person: Person = Patient("001", "Alice")
    println("${person.name} is a patient.")
}

fun examplePolymorphismInterfaces() {
    val drSmith: DentalPractitioner =
        Dentist("002", "Dr. Smith")
    val msJones: DentalPractitioner =
        Hygienist("003", "Ms. Jones")
    val treatment = Treatment("Root Canal")

    println(
        drSmith.performTreatment(
            Patient(
                "001", "Alice"
            ), treatment
        )
    )
    // Output: "Dentist Dr. Smith is performing Root Canal"

    println(
        msJones.performTreatment(
            Patient(
                "001", "Alice"
            ), treatment
        )
    )
    // Output: "Hygienist Ms. Jones is performing Root Canal"
}

fun exampleIsKeyword(person: Person) {
    if (person is Patient) {
        println("${person.name} is a patient.")
    }
    val patient: Patient? = person as? Patient
}

fun exampleAsOperator() {
    val person: Person = Patient("001", "Alice")
    val patient: Patient? = person as? Patient
}

data class Treatment(val name: String)


interface AppointmentScheduler {
    fun schedule(
        appointmentRequest: AppointmentRequest,
    ): AppointmentResponse

    fun successMessage(): String
}


class DefaultAppointmentScheduler :
    AppointmentScheduler {
    override fun schedule(
        appointmentRequest: AppointmentRequest,
    ): AppointmentResponse {
        // ... default scheduling implementation
        TODO()
    }

    override fun successMessage() =
        "Appointment scheduled on a weekday."
}

class WeekendAppointmentScheduler :
    AppointmentScheduler {
    override fun schedule(
        appointmentRequest: AppointmentRequest,
    ): AppointmentResponse {
        // ... weekend-specific scheduling implementation
        TODO()
    }

    override fun successMessage() =
        "Appointment scheduled on the weekend!"
}


class AppointmentResponse {

}

class AppointmentRequest {

}