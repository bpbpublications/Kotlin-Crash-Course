package grouping

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val productCategory: ProductCategory,
)

enum class ProductCategory(val displaySequence: Int) {
    ELECTRONICS(2), FURNITURE(4), APPLIANCES(3), CLOTHING(
        1
    ),
    FOOD(6), BEDDING(5), KITCHENWARE(7), TOYS(8)
}

val products = listOf(
    Product(
        2, "Phone", 450.0, ProductCategory.ELECTRONICS
    )
)

val productsByCategory =
    products.groupBy { it.productCategory }

val productsByCategoryAndPriceRange = products.groupBy {
    Pair(
        it.productCategory, when {
            it.price > 1000 -> "Expensive"
            it.price > 500 -> "Moderate"
            else -> "Cheap"
        }
    )
}

val productCountByCategory =
    products.groupingBy { it.productCategory }
        .eachCount()

val maxPriceInEachCategory =
    products.groupingBy { it.productCategory }
        .reduce { _, acc, product ->
            if (product.price > acc.price) product else acc
        }

val priceExtremesByCategory =
    products.groupingBy { it.productCategory }
        .aggregate { _, acc: Pair<Product, Product>?, product, first ->
            if (first) {
                Pair(product, product)
            } else {
                val maxProduct =
                    if (product.price > acc!!.first.price) product
                    else acc.first
                val minProduct =
                    if (product.price < acc!!.second.price) product
                    else acc.second
                Pair(maxProduct, minProduct)
            }
        }
