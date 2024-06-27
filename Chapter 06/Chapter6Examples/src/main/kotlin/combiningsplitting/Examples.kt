package combiningsplitting

val productsOnSale =
    listOf("Laptop", "Phone", "Headphones")
val discountPercentages = listOf(
    10,
    15,
    5
) // Representing discounts of 10%, 15%, and 5% respectively.
val productsWithDiscounts =
    productsOnSale.zip(discountPercentages)

fun unzipExample() {
    val (products, discounts) = productsWithDiscounts.unzip()
}

fun partitionExample() {
    data class Product(
        val name: String,
        val onSale: Boolean,
    )

    val products = listOf(
        Product("I am on sale", true),
        Product("I am not on sale", false)
    )
    val (onSale, regularPrice) = products.partition { it.onSale }
}

val products: List<String> =
    listOf("Laptop", "Phone", "Headphones")

// For displaying 2 products at a time
val displayBatches: List<List<String>> =
    productsOnSale.chunked(2)

// Pair each product with its corresponding sale percentage
fun prepareFlashSale(
    products: List<String>,
    discounts: List<Int>,
): List<Pair<String, Int>> =
    products.zip(discounts)

// Group products for batch email promotions
fun groupProductsForPromotion(
    products: List<String>,
    batchSize: Int,
): List<List<String>> =
    products.chunked(batchSize)