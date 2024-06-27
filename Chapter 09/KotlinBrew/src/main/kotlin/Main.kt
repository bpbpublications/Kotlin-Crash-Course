import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.KotlinBrewMachine

@Composable
@Preview
fun App() {
    MaterialTheme {
        KotlinBrewMachine()
    }
}

fun main() = application {
    Window(
        title = "KotlinBrew",
        onCloseRequest = ::exitApplication,
    ) {
        App()
    }
}

