import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.DestinationSearch
import ui.ManageDestinations

@Composable
fun VoyagerTabs() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(
                        tabPositions[selectedTabIndex]
                    )
                )
            }
        ) {
            Tab(
                text = { Text("Search Destinations") },
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 }
            )
            Tab(
                text = { Text("Manage Destinations") },
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 }
            )
        }

        when (selectedTabIndex) {
            0 -> DestinationSearch()
            1 -> ManageDestinations()
        }
    }
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        VoyagerTabs()
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinVoyager"
    ) {
        App()
    }
}
