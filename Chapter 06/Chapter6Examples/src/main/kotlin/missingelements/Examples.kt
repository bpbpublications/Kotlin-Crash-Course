package missingelements



val products = listOf(Product("Phone", 450.0))

// If there's no product at index 10, it returns the default product.
val product = products.getOrElse(10) {
    Product(
        "Default",
        0.0
    )
}

// productOrNull will be null if index 10 doesn't exist.
val productOrNull = products.getOrNull(10)

// If no TV product exists, furnitureProduct will be null.
val tvProduct = products.firstOrNull { it.name == "TV" }

val phoneProduct = products.lastOrNull { it.name.contains("Phone")}

val productNames: List<String?> = listOf("Laptop", null, "Phone")
// nonNullNames will be a list containing "Laptop" and "Phone".
val nonNullNames = productNames.filterNotNull()

data class Product(
    val name: String,
    val price: Double?,
)
// Handle a potential null price when calculating the average price
fun calculateAveragePrice(products: List<Product?>): Double =
    products.filterNotNull()
        .mapNotNull { it.price }
        .average()

