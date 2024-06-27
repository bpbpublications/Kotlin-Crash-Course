package coroutines

import kotlinx.coroutines.test.runTest
import kotlin.test.*

internal class AsyncProcessorTest {
    @Test
    fun `fetchData returns expected data`() = runTest {
        // This test coroutine context ensures delays are skipped for efficiency
        val processor = AsyncProcessor()
        val result = processor.fetchData()
        // Verify that the result is as expected
        assertEquals(
            expected = "Data",
            actual = result,
            message = "Expected data to match the async fetched result",
        )
    }
}