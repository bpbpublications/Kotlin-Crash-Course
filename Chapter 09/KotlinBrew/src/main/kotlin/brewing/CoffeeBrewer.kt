package brewing

class CoffeeBrewer(val brew: Brew) : Brew by brew {
    var sugarLevel: Int by SugarDelegate()

    fun brewCoffeeWithAddOns(): String {
        val coffee = brewCoffee()
        val sugar = "Adding $sugarLevel spoon(s) of sugar."
        return if (sugarLevel > 0) "$coffee $sugar" else coffee
    }
}
