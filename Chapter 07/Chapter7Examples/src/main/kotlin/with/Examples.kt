package with

data class Product(
    var name: String,
    var price: Double,
    var description: String,
)

fun updateProductDetails(product: Product): String =
    with(product) {
        name = "Updated Name"
        price = 99.99
        description = "This is an updated description."
        "Product updated: $product"
    }


fun updateProductDetails(productUpdateDTO: ProductUpdateDTO) {
    // Inside the 'with' block, we can access productUpdateDTO's properties directly.
    with(productUpdateDTO) {
        newPrice?.let {
            ProductCatalogRepository.updateProductPriceById(
                productId,
                it
            )
        }
        stockAdjustment?.let {
            InventoryRepository.updateStock(
                productId,
                it
            )
        }
    }
}

object InventoryRepository {
    fun updateStock(productId: Int, count: Int) {
    }

}

object ProductCatalogRepository {
    fun updateProductPriceById(
        productId: Int,
        price: Double,
    ) {
    }
}

data class ProductUpdateDTO(
    val productId: Int,
    val newPrice: Double?,
    val stockAdjustment: Int?,
)

fun updateProductInformation(product: Product) {
    // Appropriate use of 'with' for non-nullable Product object
    with(product) {
        name = "Eco-friendly Cup"
        price = 5.99
        description = "A reusable and biodegradable cup."
        // Additional operations can be added here if necessary
    }
    // The product object's properties are updated directly
}

fun letContrastExample(){
    fun updateProductInformation(product: Product) {
        product.let {
            it.name = "Eco-friendly Cup"
            it.price = 5.99
            it.description = "A reusable and biodegradable cup."
            // Less readable due to repetitive 'it'
        }
    }
}

