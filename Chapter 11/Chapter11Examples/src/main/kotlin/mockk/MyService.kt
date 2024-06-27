package mockk

import kotlinx.coroutines.delay

class MyService {
    suspend fun fetchData(id: Int): String {
        delay(1000)
        // Imagine this method would make a network call or query a database
        return "Data for ID: $id"
    }

    fun calculateValue(a: Int, b: Int): Int {
        // This method performs some calculation
        return a + b
    }

    fun someFunction(someParameters: String): String =
        "Something"

}
