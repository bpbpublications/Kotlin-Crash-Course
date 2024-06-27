package valueclasses

@JvmInline
value class Email(val email: String)

val email = Email("alice@example.com")

@JvmInline
value class PatientId(val id: String) {
    init {
        require(id.isNotBlank()) { "Patient ID cannot be blank" }
        require(id.matches(Regex("P[0-9]{3}"))) {
            "Patient ID must be in the format 'P' followed by 3 digits."
        }
    }

    override fun toString(): String = id
}