package exceptionmanagement

fun calculateDiscountedPrice(originalPrice: Int, discountRate: Int) {
    try {
        val discountAmount = originalPrice / discountRate
        val discountedPrice = originalPrice - discountAmount
        println("Discounted Price: $$discountedPrice")
    } catch (e: ArithmeticException) {
        println("Cannot apply a discount rate of 0%.")
    } finally {
        println("Discount calculation attempted.")
    }
}

fun main() {
    calculateDiscountedPrice(100, 0) // Example usage
}
