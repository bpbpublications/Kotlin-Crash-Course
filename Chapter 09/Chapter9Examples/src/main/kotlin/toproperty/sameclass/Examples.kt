package toproperty.sameclass

class CoffeeMachine {
    // New descriptive property
    var totalCupsMade: Int = 0
    // Old property, now delegates to the new one
    @Deprecated("Use 'totalCupsMade' instead")
    var cups: Int by this::totalCupsMade
}

fun main() {
    val myCoffeeMachine = CoffeeMachine()
    // Sets the value using the old property name
    myCoffeeMachine.cups = 10
    // Outputs: 10, showing the new property name has the updated value
    println(myCoffeeMachine.totalCupsMade)
}
