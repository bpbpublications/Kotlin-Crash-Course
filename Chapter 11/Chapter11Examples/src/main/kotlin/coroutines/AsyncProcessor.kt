package coroutines

import kotlinx.coroutines.delay

class AsyncProcessor {
    suspend fun fetchData(): String {
        delay(1000) // This delay is skipped in the test
        return "Data"
    }
}