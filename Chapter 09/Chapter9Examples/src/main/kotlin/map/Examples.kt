package map

class CoffeeMachineConfig(settings: Map<String, Any?>) {
    val temperature: Int by settings
    val beanType: String by settings
}

fun main() {
    // Configuration for a coffee machine might be loaded from an external source
    val configMap = mapOf(
        "temperature" to 85,
        "beanType" to "Robusta",
    )

    val coffeeMachineConfig = CoffeeMachineConfig(configMap)

    println("Coffee Machine Settings:")
    println("Temperature: ${coffeeMachineConfig.temperature}°C")
    println("Bean Type: ${coffeeMachineConfig.beanType}")
}
