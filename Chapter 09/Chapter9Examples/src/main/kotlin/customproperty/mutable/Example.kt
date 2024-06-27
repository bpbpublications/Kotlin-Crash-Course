package customproperty.mutable

import kotlin.reflect.KProperty

class WaterLevelDelegate {
    private var level = 0

    operator fun getValue(
        thisRef: CoffeeMachine,
        property: KProperty<*>,
    ): Int {
        // Returns the current water level.
        return level
    }

    operator fun setValue(
        thisRef: CoffeeMachine,
        property: KProperty<*>,
        value: Int,
    ) {
        // Ensures the new water level stays within the 0-100 range.
        level = value.coerceIn(0..100)
    }
}

class CoffeeMachine {
    var waterLevel: Int by WaterLevelDelegate()
}

fun main() {
    val machine = CoffeeMachine()
    // This will set the water level to 0.
    machine.waterLevel = -10
    println(machine.waterLevel) // Output: 0
    // This will update the water level to 50.
    machine.waterLevel = 50
    println(machine.waterLevel) // Output: 50
}

