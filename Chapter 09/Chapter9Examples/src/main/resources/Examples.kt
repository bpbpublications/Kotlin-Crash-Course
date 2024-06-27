interface Brew {
    fun brewCoffee()
}

class Delegate : Brew {
    override fun brewCoffee() {
        println("Brewing coffee...")
    }
}

class ClientObject(brewDelegate: Brew) : Brew by brewDelegate

fun main() {
    val delegate = Delegate()
    val client = ClientObject(delegate)
    client.brewCoffee()  // Output: Brewing coffee...
}
