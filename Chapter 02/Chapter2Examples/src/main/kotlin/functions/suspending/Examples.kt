package functions.suspending

import kotlinx.coroutines.*

// A suspending function simulates fetching product from a network
suspend fun fetchProductDetails(productId: String): String {
    // Simulate network delay
    delay(1000) // Delays are non-blocking in coroutines
    return "Details for product $productId"
}

fun main() = runBlocking {
    val productId = "12345"
    println("Starting to fetch details for product $productId...")
    // Immediate print statement before the coroutine's delay
    println("Fetching... Please wait.")
    // Launch a coroutine for the non-blocking delay and fetch operation
    val details = async { fetchProductDetails(productId) }.await()
    // This line will execute after the delay in fetchProductDetails
    println(details)
}
