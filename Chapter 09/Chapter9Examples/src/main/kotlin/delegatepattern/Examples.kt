package delegatepattern

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

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

@Composable
fun CoffeeMachineUIWithoutDelegation() {
    // State is manually managed and stored in a variable
    val stateHolder = remember { mutableStateOf<CoffeeType?>(null) }
    val selectedCoffeeType = stateHolder.value // Accessing value manually

    Button(onClick = {
        // Updating state required manually setting the value
        stateHolder.value = CoffeeType.Espresso
    }) {
        Text("Select Espresso")
    }
    // ... other UI code
}

@Composable
fun CoffeeMachineUIWithDelegation() {
    // State now has a delegate
    var selectedCoffeeType by remember { mutableStateOf<CoffeeType?>(null) }

    Button(onClick = {
        // State can be updated directly because of delegated property
        selectedCoffeeType = CoffeeType.Espresso
    }) {
        Text("Select Espresso")
    }
    // ... other UI code
}

enum class CoffeeType {
    Espresso
}
