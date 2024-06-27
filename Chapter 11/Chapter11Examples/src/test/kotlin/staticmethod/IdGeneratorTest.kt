package staticmethod

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkObject
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class IdGeneratorTest {

    @Test
    fun `should return predefined id value`() {
        // Predefined ID for the test
        val predefinedId = "my uuid"
        // Start mocking the UUID class
        mockkStatic(UUID::class) {
            // Stub the randomUUID chained call to return predefinedId
            every {
                UUID.randomUUID().toString()
            } returns predefinedId
            // Call the method under test
            val id = IdGenerator().generateId()
            // Assert the result equals predefinedId
            assertEquals(predefinedId, id)
        }
    }
}

