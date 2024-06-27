package variablesandconstants

// Top-level constant
const val SALES_TAX_RATE = 0.07
const val DEFAULT_CURRENCY = "USD"

val shopName: String = "Gadget Store"

class Product {
    val productId: Int = 101
    val isInStock: Boolean = true
    val name: String = "Laptop"
    var price: Double = 999.99
    // nullable variable
    val discount: Double? = null
    // non-nullable variable
    val productCode: String = "AB123"


    fun calculateDiscountedPrice(): Double =
        price * (1 - DEFAULT_DISCOUNT_RATE)

    companion object {
        const val DEFAULT_DISCOUNT_RATE = 0.1
        const val CATEGORY_NAME = "Electronics"
    }
}

fun calculateDiscount(price: Double, discount: Double): Double {
    val discountedPrice = price * (1 - discount)
    return discountedPrice
}

object CurrencyConverter {
    val SUPPORTED_CURRENCIES = listOf("USD", "EUR", "JPY")
}

class Customer {
    val customerName = "Alice"
    var customerAge = 30

}

val products: List<String> = listOf("Laptop", "Mouse")
val mutableProducts: MutableList<String> =
    mutableListOf("Laptop", "Mouse")

const val PADDING_THICKNESS = 3

fun main() {
    // Total padding added to each side
    val padding = PADDING_THICKNESS * 2

    // Book dimensions with padding included
    val length = 20 + padding
    val width = 10 + padding
    val height = 5 + padding

    // Calculate the volume of the shipping box
    val boxVolume = length * width * height

    // Display the calculated volume
    println("Required shipping box volume: $boxVolume cm³")
}
