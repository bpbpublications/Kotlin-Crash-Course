package namingandorganisation.variablesandconstants

// file level constants (primitive types)
const val GLOBAL_TAX_RATE = 0.07
const val DEFAULT_SHIPPING_FEE = 5.0

// Non-primitive constant - no 'const' keyword
val SUPPORTED_CURRENCIES = listOf("EUR", "GBP")

class Product {
    private val _prices = mutableListOf<Double>()

    val prices: List<Double>
        get() = _prices

    // Other properties and methods
}

class Shop {
    companion object {
        //class level constants
        const val MINIMUM_ORDER_AMOUNT = 10.0
        const val DISCOUNT_THRESHOLD = 100.0
        const val BULK_DISCOUNT_RATE = 0.1

        // Other properties and methods
    }
}
