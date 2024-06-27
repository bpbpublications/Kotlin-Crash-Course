package run

data class Product(
    var id: Int,
    var name: String,
    var price: Double,
)

fun getProductById(id: Int): Product? {
    // Fetch the product from the catalog...
    // For demonstration purposes, let's assume we couldn't find the product and return null.
    return null
}

fun updateProductName(
    productId: Int,
    newName: String,
): String = getProductById(productId)?.run {
    name = newName
    "Product updated to: $name"
} ?: "Product with ID $productId not found."

