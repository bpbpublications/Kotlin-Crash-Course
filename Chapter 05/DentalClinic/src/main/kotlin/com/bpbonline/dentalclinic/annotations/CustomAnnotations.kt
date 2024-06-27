package com.bpbonline.dentalclinic.annotations

import jakarta.validation.Constraint
import jakarta.validation.Payload
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
    val payload: Array<KClass<out Payload>> = []
)
