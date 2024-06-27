package com.bpbonline.dentalclinic.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PerformTreatmentTest {
    private lateinit var patient: Patient
    private lateinit var treatment: Treatment
    private lateinit var dentist: Dentist
    private lateinit var hygienist: Hygienist
    private lateinit var dentalPractitioners: List<DentalPractitioner>

    @BeforeEach
    fun setUp() {
        patient = Patient("1", "Bob")
        treatment = Treatment(
            "123",
            "Clean teeth",
            TreatmentType.CLEANING,
        )
        dentist = Dentist("2", "Jane")
        hygienist = Hygienist("3", "John")
        dentalPractitioners = listOf(dentist, hygienist)
    }

    @Test
    fun performTreatment() {
        dentalPractitioners.forEach {
            val result = it.performTreatment(patient, treatment)
            assertTreatmentResult(it, result)
        }
    }

    private fun assertTreatmentResult(
        practitioner: DentalPractitioner,
        result: String,
    ) {
        if (practitioner is Dentist)
            assertEquals(
                "Dentist Jane is performing Clean teeth",
                result
            )
        else
            assertEquals(
                "Hygienist John is performing Clean teeth",
                result
            )
    }
}
