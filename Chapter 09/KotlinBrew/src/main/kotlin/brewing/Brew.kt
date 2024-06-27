package brewing

interface Brew {
    val name: String
    fun brewCoffee(): String
}

class BasicBrew : Brew {
    override val name = "Basic Brew"
    override fun brewCoffee() = "Brewing a basic coffee."
}

class CappuccinoBrew : Brew {
    override val name = "Cappuccino"
    override fun brewCoffee() = "Brewing a frothy cappuccino."
}

class EspressoBrew : Brew {
    override val name = "Espresso"
    override fun brewCoffee() = "Brewing a rich espresso."
}
