package filterandsearch

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
)

val products = listOf(Product(2, "Phone", 450.0))


val expensiveProducts = products.filter { it.price > 500 }
val affordableProducts = products.filterNot { it.price > 500 }

val nullableProducts = listOf(Product(2, "Phone", 450.0), null)
val nonNullProducts: List<Product> = nullableProducts.filterNotNull()

val laptop = products.find { it.name == "Laptop" }
val index = products.indexOfFirst { it.price < 200 }

val hasExpensiveProducts = products.any { it.price > 1000 }
val areAllProductsAffordable = products.all { it.price < 2000 }
val noCheapProducts = products.none { it.price < 50 }