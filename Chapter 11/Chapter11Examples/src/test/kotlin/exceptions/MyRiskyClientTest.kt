package exceptions

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class MyRiskyClientTest {

    @Test
    fun performAction() {
        // Create a mock instance of MyRiskyService
        mockk<MyRiskyService>().apply {
            // Configure the mock to throw an exception when riskyOperation is called
            every { riskyOperation() } throws Exception("test exception")

            // Use kotlin-test's assertFailsWith to expect a specific type of exception
            val exception = assertFailsWith<MyException> {
                // Create an instance of MyRiskyClient with the mocked service and call performAction
                MyRiskyClient(this).performAction("test")
            }

            // Assert that the message in the caught exception is as expected
            assertEquals(
                "Action with parameter `test` failed",
                exception.message
            )
        }
    }

}

