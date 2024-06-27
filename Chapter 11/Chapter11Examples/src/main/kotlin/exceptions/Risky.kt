package exceptions

class MyRiskyClient(val myRiskyService: MyRiskyService) {
    fun performAction(parameter: String) {
        try {
            myRiskyService.riskyOperation()
        } catch (e: Exception) {
            throw MyException(
                "Action with parameter `$parameter` failed",
                e,
            )
        }
    }
}

class MyException(
    message: String,
    throwable: Throwable,
) : Exception(message, throwable)

class MyRiskyService {
    fun riskyOperation() {
        // ... can throw exceptions
    }
}