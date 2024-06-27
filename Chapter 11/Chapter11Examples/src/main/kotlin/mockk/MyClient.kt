package mockk

import kotlinx.coroutines.runBlocking

class MyClient(private val myService: MyService) {
    fun performAction(parameter: String): String {
        // This method relies on MyService to perform its action
        val data = myService.someFunction(parameter)
        // Perform action with the data
        return "MyService returned $data"
    }

    fun performActionWithCallToSuspendingFunction(
        id: Int,
    ): String = runBlocking {
        // This method relies on MyService to perform its action
        val data = myService.fetchData(id)
        // Perform action with the data
        "MyService returned $data"
    }
}
