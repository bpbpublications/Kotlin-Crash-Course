package customproperty.immutable

import kotlin.reflect.KProperty

class CoffeeMachine(val serialNumber: String) {
    val modelInfo: String by ModelInfoDelegate()
}

class ModelInfoDelegate {
    operator fun getValue(
        thisRef: CoffeeMachine,
        property: KProperty<*>,
    ): String {
        // Returns model information based on the serial number
        // Example: Append "-KotlinBrewModel" to every serial number.
        return "${thisRef.serialNumber}-KotlinBrewModel"
    }
}

fun main() {
    val machine = CoffeeMachine("12345ABC")
    println(machine.modelInfo) // Prints "12345ABC-KotlinBrewModel"
}
