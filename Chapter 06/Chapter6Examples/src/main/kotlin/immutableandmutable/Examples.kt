package immutableandmutable

// Holds popular products immutably
data class PopularProducts(val products: List<Product>) {
    // Prints product names safely
    fun display() = products.forEach { println(it.name) }
}

// Extension function to update popular products
fun PopularProducts.updateProducts(
    newProducts: List<Product>,
) = copy(products = newProducts)


data class Product(val name: String)

private val _allProducts = mutableListOf<Product>()

val allProducts: List<Product>
    get() = _allProducts.toList()
