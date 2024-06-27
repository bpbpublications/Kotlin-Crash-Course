package customproperty.local

import kotlin.reflect.KProperty

class UsageCounterDelegate {
    private var count = 0

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Int {
        return count
    }

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Int,
    ) {
        count = value
    }
}

class CoffeeMachine(val serialNumber: String) {
    fun performOperation() {
        var usageCount by UsageCounterDelegate() // Local delegation

        // Simulate the operation
        for (i in 1..5) {
            usageCount++
            println("Operation $i, usage count: $usageCount")
        }
    }
}

fun main() {
    val machine = CoffeeMachine("12345ABC")
    machine.performOperation()
    // After this function, usageCount is not retained
}
