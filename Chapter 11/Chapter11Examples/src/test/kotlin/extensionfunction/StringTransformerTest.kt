package extensionfunction

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlin.test.Test
import kotlin.test.assertEquals

internal class StringTransformerTest {
    @Test
    fun `should return "mock response"`() {
        // Start mocking String's extension function
        mockkStatic(String::myExtensionFunction)
        // Define behavior: "my request".myExtensionFunction()
        // will return "mock response"
        every {
            "my request".myExtensionFunction()
        } returns "mock response"
        // Use StringTransformer to apply the function
        val result = StringTransformer().transform("my request")
        // Check if result is as expected
        assertEquals("mock response", result)
        // Finish mocking to avoid side effects
        unmockkStatic(String::myExtensionFunction)
    }

}

