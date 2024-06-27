package functions

fun calculateTotal(products: List<Product>): Double {
    var total = 0.0
    for (product in products) {
        total += product.price
    }
    return total
}

fun processOrder(order: Order) {
    // Nested function for order validation
    fun validateOrder(order: Order): Boolean {
        // Perform order validation logic
        return true
    }

    // Nested function for applying discounts
    fun applyDiscounts(order: Order) {
        // Apply discounts to the order
    }

    // Main logic of the processOrder function
    if (validateOrder(order)) {
        applyDiscounts(order)
        // Continue processing the order
    } else {
        // Handle invalid order
    }
}

class Order {

}

class Product (
    val name: String,
    val price: Double,
)

// Extension function to calculate the total price of a products list
fun List<Product>.totalPrice(): Double {
    var total = 0.0
    for (product in this) {
        total += product.price
    }
    return total
}

// Create a list of products
val products = listOf(Product("Laptop", 999.99),
    Product("Mouse", 29.99))

// Use the extension function to calculate the total price
val totalPrice = products.totalPrice()

// Lambda function
val discountFunction: (Double) -> Double = { price -> price * 0.9 }

//Higher-order function
fun applyDiscount(
    price: Double,
    discountCalculator: (Double) -> Double,
) = discountCalculator(price)

// Using the higher-order function with the lambda
val finalPrice = applyDiscount(100.0, discountFunction)

fun calculateDiscount(price: Double, discount: Double): Double {
    return price - discount
}

val discountedPrice = calculateDiscount(price = 10.0, discount = 2.0)

