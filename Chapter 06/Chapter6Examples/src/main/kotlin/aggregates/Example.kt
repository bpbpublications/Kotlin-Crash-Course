package aggregates

val productsPrices =
    listOf(1000.0, 500.0, 1500.0, 2000.0)
val totalRevenue = productsPrices.sum()
val averagePrice = productsPrices.average()
val maxPrice = productsPrices.maxOrNull()
val minPrice = productsPrices.minOrNull()
val premiumProductCount =
    productsPrices.count { it > 1000 }
val discountedPrice =
    productsPrices.reduce { acc, price -> acc + price * 0.9 }
val productNames =
    listOf("laptop", "phone", "headphones")
val productPricesWithTax =
    productsPrices.fold(0.0) { acc, price ->
        acc + when {
            price <= 500 -> price
            price <= 1500 -> price + 0.10 * price
            else -> price + 0.20 * price
        }
    }

fun getAveragePriceInCategory(category: ProductCategory) =
    products.asSequence()
        .filter { it.productCategory == category }
        .map { it.price }
        .average()

fun getNumberOfItemsInCategory(category: ProductCategory) =
    products
        .count { it.productCategory == category }

enum class ProductCategory(val displaySequence: Int) {
    ELECTRONICS(2), FURNITURE(4), APPLIANCES(3), CLOTHING(
        1
    ),
    FOOD(6), BEDDING(5), KITCHENWARE(7), TOYS(8)
}

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val productCategory: ProductCategory,
)

val products = listOf(
    Product(
        2,
        "Phone",
        450.0,
        ProductCategory.ELECTRONICS
    )
)