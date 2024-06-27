package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.Destination
import model.DiscountApplier
import repository.DestinationsRepository
import service.applyDiscount
import service.toDestinationTags

@Composable
fun DestinationItem(
    destination: Destination,
    isSelected: Boolean = false,
    onClick: () -> Unit,

    ) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(if (isSelected) Color.Yellow else Color.LightGray)
            .clickable(onClick = onClick),
    ) {
        Row {
            Column {
                Text(
                    text = "${destination.name}: ",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(text = "${destination.price}")
                Text(
                    text = destination.tags.joinToString(", "),
                    fontStyle = FontStyle.Italic,
                )
            }
            Column {
                Text(text = destination.description)
            }
        }
    }
}

@Composable
fun DestinationsList(
    destinations: List<Destination>,
    selectedIndices: Set<Int>,
    onDestinationSelected: (Int) -> Unit,

    ) {
    LazyColumn {
        items(destinations.size) { index ->
            val destination = destinations[index]
            DestinationItem(
                destination = destination,
                isSelected = selectedIndices.contains(index)
            ) {
                onDestinationSelected(index)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DestinationSearch() {
    var minPrice by remember { mutableStateOf(TextFieldValue("")) }
    var maxPrice by remember { mutableStateOf(TextFieldValue("")) }
    var tagsInput by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var filteredDestinations by remember {
        mutableStateOf(
            listOf<Destination>()
        )
    }
    var selectedIndices by remember {
        mutableStateOf(setOf<Int>())
    }

    Column {
        TextField(
            value = minPrice,
            onValueChange = { minPrice = it },
            label = { Text("Min Price") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = maxPrice,
            onValueChange = { maxPrice = it },
            label = { Text("Max Price") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = tagsInput,
            onValueChange = { tagsInput = it },
            label = { Text("Search Tags (comma separated)") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(onClick = {
                val min = minPrice.text.toDoubleOrNull()?: 0.0
                val max = maxPrice.text.toDoubleOrNull()?: Double.MAX_VALUE
                val tags = tagsInput.text.toDestinationTags()

                filteredDestinations =
                    DestinationsRepository.filterDestinations (
                        { it.price in min..max },
                        { tags.isEmpty() || it.tags.intersect(tags).isNotEmpty() },
                    )
            }) {
                Text("Search")
            }

            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                val discountApplier =
                    DiscountApplier { destination ->
                        // Create a copy of 'destination' with 90% of the price
                        destination.copy(price = destination.price * 0.9)
                    }
                // Apply the discount logic to the list of destinations
                filteredDestinations =
                    filteredDestinations.applyDiscount(discountApplier)
            }) {
                Text("Apply 10% Discount")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (selectedIndices.size == 2) {
                    val dest1 = filteredDestinations[selectedIndices.elementAt(0)]
                    val dest2 = filteredDestinations[selectedIndices.elementAt(1)]
                    val combined = dest1 + dest2
                    filteredDestinations = (filteredDestinations + combined)
                        .sortedBy { it.price } // Sort destinations by price
                    selectedIndices = setOf()   // Reset the selections
                }
            }) {
                Text("Combine Selected")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        DestinationsList(
            destinations = filteredDestinations,
            selectedIndices = selectedIndices,
            onDestinationSelected = { index ->
                selectedIndices =
                    if (selectedIndices.contains(index)) {
                        selectedIndices - index
                    } else if (selectedIndices.size < 2) {
                        selectedIndices + index
                    } else selectedIndices
            }
        )
    }
}
