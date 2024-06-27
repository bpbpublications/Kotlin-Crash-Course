package namingandorganisation.classesinterfacesobjectstypealiases

interface ProductCatalog

typealias ProductList = List<Product>

object ShopManager

class Product(
    val id: Int,
    val name: String,
    val price: Double,
) {

    // Read-only property
    val priceWithTax: Double
        get() = price * (1 + TAX_RATE)

    // Initialization block
    init {
        require(id > 0) { "Id must be greater than 0" }
        require(name.isNotBlank()) { "Name must not be blank" }
        require(price >= 0.0) { "Price must be non-negative" }
    }

    //Secondary constructor
    constructor(id: Int, price: Double) :
            this(id, DEFAULT_NAME, price)

    // Delegate property
    val displayName: String by lazy { "$name (ID: $id)" }

    // Public function
    fun applyDiscount(discountRate: Double): Double {
        return price * (1 - discountRate)
    }

    // Other functions (internal, protected, private) go here

    // Class constants
    companion object {
        const val DEFAULT_NAME = "Awesome product"
        const val TAX_RATE = 0.1
    }
}

