package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.destination
import repository.DestinationsRepository
import service.toDestinationTags

@Composable
fun ManageDestinations() {
    var destinationName by remember { mutableStateOf("") }
    var destinationPrice by remember { mutableStateOf("") }
    var destinationDescription by remember { mutableStateOf("") }
    var destinationTags by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    fun clearFieldsAndShowSuccessMessage() {
        destinationName = ""
        destinationPrice = ""
        destinationDescription = ""
        destinationTags = ""
        showSnackbar = true
    }
    Scaffold(
        snackbarHost = {
            if (showSnackbar) {
                Snackbar() {
                    Text("Destination added!")
                }
            }
        }
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = destinationName,
                onValueChange = { destinationName = it },
                label = { Text("Destination Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = destinationPrice,
                onValueChange = { destinationPrice = it },
                label = { Text("Destination Price") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = destinationDescription,
                onValueChange = { destinationDescription = it },
                label = { Text("Destination Description") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = destinationTags,
                onValueChange = { destinationTags = it },
                label = { Text("Tags (comma separated)") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val tagsList = destinationTags.toDestinationTags()
                val newDestination = destination {
                    name = destinationName
                    price = destinationPrice.toDoubleOrNull() ?: 0.0
                    description = destinationDescription
                    tags(*tagsList.toTypedArray())
                }
                DestinationsRepository.addDestination(newDestination)
                clearFieldsAndShowSuccessMessage()
            }) {
                Text("Add Destination")
            }
        }
    }
}
