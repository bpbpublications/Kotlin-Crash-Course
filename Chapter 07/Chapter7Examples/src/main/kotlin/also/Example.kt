package also

object ProductCatalogRepository {

    fun addProduct(it: Product) {

    }
}

data class Product(
    val id: Int,
    val name: String,
    val description: String?,
    val price: Double,
)

val product =
    Product(1, name = "Watch", "iWatch", price = 250.0)
        .also {
            ProductCatalogRepository.addProduct(it)
            println("Added ${it.name} to catalog")
        }

val newProduct =
    Product(
        1,
        name = "Smartphone",
        "iPhone",
        price = 1000.0
    )
        .also {
            ProductCatalogRepository.addProduct(it)
            println("Added ${it.name} to catalog")
        }

val discountedProduct =
    product.copy(price = product.price * 0.9)
        .also {
            println("Discounted Price: ${it.price}")
        }

fun chainingExample() {
    Product(1, name = "Watch", "iWatch", price = 250.0)
        .also { validateProductDetails(it) }
        .also { ProductCatalogRepository.addProduct(it) }
}

fun validateProductDetails(product: Product) {
    // do some validation
}


