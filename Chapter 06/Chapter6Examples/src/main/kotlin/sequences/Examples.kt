package sequences

val productCodesSequence =
    sequenceOf("P001", "P002", "P003", "P004")

val productNames =
    listOf("Phone", "Laptop", "Tablet", "Watch")
val productNamesSequence = productNames.asSequence()

// Produces 1, 2, 4, 8
val stockCounts = generateSequence(1) { it * 2 }.take(4)

// Takes the first three products
val limitedProducts = productNamesSequence.take(3)

// Skips the first two products
val productsAfterSkipping = productNamesSequence.drop(2)

val firstProduct = productNamesSequence.first()

val lastProduct = productNamesSequence.last()

fun efficientDataProcessing() {
    // Large dataset of product IDs
    val allProductIDs = (1..10000).toList()

    // Sale applies only to product IDs within a specific range
    val saleRange = 200..600

    // Efficiently process IDs in the sale range using a progression
    allProductIDs
        .asSequence()
        .filter { it in saleRange }
        .forEach { id ->
            // Apply discount to product by ID
            applyDiscountToProduct(id)
        }
}


fun applyDiscountToProduct(id: Int) {

}
