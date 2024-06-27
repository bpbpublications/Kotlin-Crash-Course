package iterators

val products = listOf(Product(2, "Phone", 450.0))
fun forLoopsExample() {
    for (product in products) {
        println(product.name)
    }
    // Using indices
    for (index in products.indices) {
        println("Product ${index + 1}: ${products[index].name}")
    }

    // Using withIndex()
    for ((index, product) in products.withIndex()) {
        println("Product ${index + 1}: ${product.name}")
    }
}

fun forEachExample() {
    products.forEach { product ->
        println(product.name)
    }
    products.forEachIndexed { index, product ->
        println("Product ${index + 1}: ${product.name}")
    }

}

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String = "Electronics",
)

fun customerInterators() {
    class ProductCatalog(val products: List<Product>) : Iterable<Product> {
        // Custom iterator for sorted products
        override fun iterator(): Iterator<Product> {
            return products
                .sortedWith(compareBy({ it.category }, { it.price }))
                .iterator()
        }
    }

    // Usage example:
    val catalog = ProductCatalog(listOf(/* products */))
    for (product in catalog) {
        println("Category: ${product.category}, Price: ${product.price}")
    }

}

