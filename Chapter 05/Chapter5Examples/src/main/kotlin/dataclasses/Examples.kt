package dataclasses

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val time: LocalDateTime,
    val treatment: Treatment,
)
data class Treatment(
    val id: String,
    val name: String,
)

@Serializable
data class Patient(val id: String, val name: String)

fun exampleUpdateImmutable(){
    val patient = Patient("1", "John")
    // patient.name = "Doe" // Compilation error
}

fun exampleCopy(){
    val patient = Patient("1", "John")
    val updatedPatient = patient.copy(name = "Doe")
}

fun exampleEquals(){
    val patient1 = Patient("1", "John")
    val patient2 = Patient("1", "John")
    println(patient1 == patient2) // Prints: true
}

fun exampleToString(){
    val patient1 = Patient("1", "John")
    println(patient1.toString()) // Prints: Patient(id=1, name=John)
}

fun exampleOverrideToString(){
    data class Patient(val id: String, val name: String) {
        override fun toString() = name
    }
    val patient = Patient("1", "John")
    println(patient.toString()) // Prints: John
}

fun exampleSerialize(){
    val patient = Patient("1", "John")
    val jsonString = Json.encodeToString(patient)
    println(jsonString) // Prints: {"id":"1","name":"John"}
}

fun main() {
    exampleEquals()
    exampleToString()
    exampleOverrideToString()
    exampleSerialize()
}