package com.bpbonline.dentalclinic.annotations

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

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
