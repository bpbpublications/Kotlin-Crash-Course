package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import brewing.*
import kotlin.math.roundToInt

@Composable
fun DropdownBrewSelector(
    brewOptions: List<Brew>, // Brew object list to populate the menu
    selectedBrew: Brew?, // Currently selected Brew, can be null
    onSelectionMade: (Brew) -> Unit // Lambda callback on selection made
) {
    // State of the dropdown menu: expanded or collapsed
    var expanded by remember { mutableStateOf(false) }

    // Selected brew's name, or "Select Brew" if none is selected
    val selectedBrewName = selectedBrew?.name ?: "Select Brew"

    // A container box that expands to the maximum width of its parent
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // An outlined button that when clicked, shows the dropdown menu
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedBrewName) // The currently selected brew name
            // Arrow icon indicating this is a dropdown
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }
        // The actual dropdown menu that is toggled by the button
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Create menu items from list of brew options
            brewOptions.forEach { brew ->
                // Individual dropdown menu item
                DropdownMenuItem(onClick = {
                    onSelectionMade(brew) // Notify selection
                    expanded = false // Close the menu after selection
                }) {
                    Text(brew.name) // Text for the brew option
                }
            }
        }
    }
}

@Composable
fun BeverageSettings(
    selectedBrew: Brew?, // The currently selected Brew
    onSelectionMade: (Brew) -> Unit, // Lambda callback
) {
    // Create drop down composable
    DropdownBrewSelector(
        // Pass a list of brews available for selection
        brewOptions = listOf(
            BasicBrew(),
            CappuccinoBrew(),
            EspressoBrew(),
        ),
        // Pass the currently selected brew to display
        selectedBrew = selectedBrew,
        // Pass the lambda that will handle the selection of a new brew
        onSelectionMade = onSelectionMade
    )
}
@Composable
fun KotlinBrewMachine() {
    // State variables to keep track of selected brew type and result
    var selectedBrew by remember { mutableStateOf<Brew?>(null) }
    var coffeeResult by remember { mutableStateOf<String?>(null) }
    var sugarLevel by remember { mutableStateOf(0) }
    // UI structure for the coffee machine
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dropdown for selecting the type of coffee
        BeverageSettings(
            selectedBrew = selectedBrew,
            onSelectionMade = { brew ->
                selectedBrew = brew
            }
        )
        // Spacer for padding
        Spacer(modifier = Modifier.height(20.dp))
        // Slider for sugar level
        Text("Sugar Level: $sugarLevel")
        Slider(
            value = sugarLevel.toFloat(),
            onValueChange = { newValue ->
                sugarLevel = newValue.roundToInt()
            },
            valueRange = 0f..10f, // Set the range of the slider from 0 to 10
            steps = 9, // Set the number of steps in the slider (0 to 10)
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        // Spacer for padding
        Spacer(modifier = Modifier.height(20.dp))
        // Button to make coffee
        Button(
            onClick = { // Brewing logic when button is clicked
                selectedBrew?.let {
                    val brewingSession = CoffeeBrewer(it)
                    brewingSession.sugarLevel = sugarLevel
                    coffeeResult = brewingSession.brewCoffeeWithAddOns()
                }
            },
            enabled = selectedBrew != null,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Make Coffee")
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Display coffee result
        coffeeResult?.let { brewResult ->
            Text(brewResult)
        }
    }
}


