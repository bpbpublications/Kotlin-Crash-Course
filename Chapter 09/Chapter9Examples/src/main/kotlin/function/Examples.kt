package function

interface Brew {
    fun brewCoffee(): String
}

class BasicBrew : Brew {
    override fun brewCoffee() = "Brewing a basic coffee."
}

class EspressoMachine(val brew: Brew) : Brew by brew

class CappuccinoBrew : Brew {
    override fun brewCoffee() = "Brewing a frothy cappuccino."
}

fun main() {
    val basicMachine = EspressoMachine(BasicBrew())
    // Prints: Brewing a basic coffee.
    println(basicMachine.brewCoffee())

    val cappuccinoMachine = EspressoMachine(CappuccinoBrew())
    // Prints: Brewing a frothy cappuccino.
    println(cappuccinoMachine.brewCoffee())
}

