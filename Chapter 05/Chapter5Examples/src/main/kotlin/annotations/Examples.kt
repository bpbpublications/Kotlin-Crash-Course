package annotations

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import java.util.*
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY_GETTER
)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PatientIdValidator::class])
annotation class ValidPatientId(
    val message: String = "Invalid patient id",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

data class AppointmentRequest(
    @ValidPatientId
    val patientId: String,
    // The rest of the constructor
){
    @set:Deprecated("Use another property.")
    var name: String = ""
}

fun getterExample() {
    class AppointmentRequest {
        @get:ValidPatientId
        var patientId: String = ""
            get() = field.uppercase() // hypothetical getter transformation
    }

}


class PatientIdValidator :
    ConstraintValidator<ValidPatientId, String> {

    // Regex pattern for string that begins with "P" followed by numbers
    private val idPattern = "P\\d+".toRegex()

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?,
    ): Boolean {
        return value != null && value.matches(idPattern)
    }
}


