import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication {
    ClickableButton()
}

@Composable
fun ClickableButton() {
    Button(onClick = { println("Button clicked!") }) {
        Text("Click Me")
    }
}
