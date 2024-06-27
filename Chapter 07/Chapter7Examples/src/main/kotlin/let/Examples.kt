package let

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
)

fun fetchProductFromDatabase(): Product? = null

fun nullSafetyExample() {
    // Product is nullable as fetchProductFromDatabase can return null
    val product: Product? = fetchProductFromDatabase()
    product?.let {
        println("Product ID: ${it.id}, Product Name: ${it.name}")
    }
}

fun addProductToCatalog(product: Product) {
    product.let {
        val fullDetail = "${it.name} - ${it.description}"
        // Use fullDetail for further processing.
    }
    // fullDetail isn't accessible outside the let block.
}

fun displayDiscountedPrice(
    product: Product,
    discount: Double,
): String {
    return product.let {
        val discountedPrice =
            it.price - (it.price * discount / 100)
        "Price: $discountedPrice"
    }
}

fun example() {
    println(
        formatProductDetails(
            applyDiscount(
                getProduct(123)
            )
        )
    )

    getProduct(123)
        .let(::applyDiscount)
        .let(::formatProductDetails)
        .also(::println)

}

fun formatProductDetails(product: Product) =
    product.toString()

fun getProduct(id: Int): Product {
    TODO()
}


fun applyDiscount(product: Product): Product {
    // Imagine this method applies a discount to the product and returns the updated product
    return product
}

fun addFeaturedLabel(product: Product): Product {
    // This method adds a 'FEATURED' label to the product and returns it
    return product
}

fun formatForDisplay(product: Product): String {
    // Formats the product into a display string
    return "Featured: ${product.name} at ${product.price}"
}

fun enhanceProductInfo(product: Product): String {
    return product
        .let(::applyDiscount) // Applies discount and returns the Product
        .let(::addFeaturedLabel) // Adds a featured label and returns the Product
        .let(::formatForDisplay) // Formats the product for display and returns a String
}

