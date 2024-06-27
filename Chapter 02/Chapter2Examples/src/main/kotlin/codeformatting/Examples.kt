package codeformatting

import org.junit.jupiter.api.BeforeAll
import java.util.*

class Examples1 {
    class Product(val id: Int, val name: String, val price: Double) {
        fun displayProductInfo() {
            println("Product ID: $id, Product Name: $name")
        }
    }

    class ShoppingCart {
        fun addProduct(product: Product) {
            // Code for adding an item to the shopping cart
        }

        fun removeProduct(product: Product) {
            // Code for removing an item from the shopping cart
        }
    }

    val p1 = Product(1, "Laptop", 1200.0);
    val p = p1.price

    // Preferred
    fun calculateDiscount(price: Double, discount: Double): Double {
        return if (price > 0) price * (1 - discount / 100) else 0.0
    }
}

class Examples2 {
    // No space before the opening parenthesis.
    class Product(val name: String, val stock: MutableList<Int>)

    // A class declaration with proper spacing around angle brackets.
    class ShoppingCart<Item>(val items: List<Item>)

    fun main() {
        // Proper spacing in the constructor call.
        val product = Product("Sample", mutableListOf(10, 8, 6))

        // No spaces around the "range to" operator.
        for (i in 0..2) {
            // Proper spacing around the unary operator '--'.
            product.stock[i]--
        }

        // No space after the opening parenthesis.
        val cart = ShoppingCart(listOf(product))

        // No spaces around '?' and '.' operators.
        println(cart.items.firstOrNull()?.name)
    }
}

class Examples3 {
    open class Product(val id: Int, val name: String, val price: Double)
    class SpecialProduct(
        id: Int,
        name: String,
        price: Double,
        discount: Double,
    ) : Product(id, name, price),
        Discountable,
        LimitedEdition { /*...*/ }

    interface LimitedEdition
    interface Discountable
}

class Examples4 {
    private val shop = Shop()
    private val defaultCurrency: Currency? =
        CurrencyConverter.getDefaultCurrencyForShop(shop)

    class CurrencyConverter {
        companion object {
            fun getDefaultCurrencyForShop(shop: Shop): Currency? = null
        }

    }

    class Shop {
        val products = listOf<Product>()
        val hasProducts: Boolean get() = products.isNotEmpty()

        fun addProduct(
            product: Product,
            quantity: Int = 1,
            discount: Double,
        ): Receipt {
            return Receipt()
        }

        fun calculateTotal(
            items: List<Product>,
            taxRate: Double,
            discount: Double,
        ) =
            items.sumOf { it.price * (1 + taxRate) * (1 - discount) }

    }

    class Receipt {

    }

    class Product(val price: Double, val quantity: Int) {
        // backing property
        private var _productName: String = "Awesome product"

        // property with complex setter
        var productName: String
            get() = _productName
            set(value) {
                // Your setter logic here
            }

        // better
        fun totalPrice() = price * quantity
    }

}

class Examples5 {
    val orderItems = listOf<Item>()

    // A constructor call with named parameters and trailing commas
    val customer = Customer(
        id = 123,
        firstName = "John", lastName = "Doe",
        email = "john.doe@example.com",
    )

    val shop: Shop = Shop()


    fun exampleFunctionCalls() {

        // A single-line function call
        calculateTotal(orderItems)

        // A multiline function call with closely related arguments
        processOrder(
            orderId = 567,
            customerId = 123,
            shippingMethod = "Ground", paymentMethod = "Credit Card",
            orderTotal = 1500.0,
        )

        val orderId = 1
        val discountPercentage = 0.5
        val discountedItems = shop
            .getOrder(orderId)
            ?.getItemsWithDiscount(discountPercentage)
            ?.map { it.product }
            ?.toList()
    }

    fun exampleLambdas() {
        val products = listOf<Product>()
        val productMap = mapOf<String, Product>()

        val names = products.map { it.name }

        // Lambda with multiple parameters
        productMap.forEach { (name, product) ->
            println("Product name: $name, Product price: ${product.price}")
        }

        // Long lambda with braces and indents
        val discountedProducts = products.map { product ->
            val discount = if (product.price > 1.0) 0.2 else 0.1
            product.price * (1 - discount)
        }

        // Lambda as trailing argument
        products.forEach {
            println(it.name)
        }

    }

    fun exampleControlFlow() {
        val product = Product("Laptop", 555.5, 1, "Electronics")

        // if-else
        if (product.stock > 0) {
            product.stock--
            println("Product stock reduced")
        } else {
            println("No stock available")
        }

        // when
        when (product.category) {
            "Electronics" -> applyElectronicsDiscount(product)
            "Clothing" -> applyClothingDiscount(product)
            "Groceries" -> applyGroceryDiscount(product)
            else -> println("No discount available for this category")
        }

        // when multiline
        when (product.category) {
            "Electronics" -> {
                applyElectronicsDiscount(product)
                println("Electronics discount applied")
            }

            "Clothing" -> {
                applyClothingDiscount(product)
                println("Clothing discount applied")
            }

            "Groceries" -> {
                applyGroceryDiscount(product)
                println("Grocery discount applied")
            }

            else -> {
                println("No discount available for this category")
                notifyAdmin(product)
            }
        }

        val productList = listOf<Product>()

        // for loop
        for (product in productList) {
            if (product.stock > 0) {
                product.stock--
                println("Product stock reduced")
            } else {
                println("No stock available")
            }
        }

    }

    @JvmName("calculateTotalPrice")
    fun totalCost() { /*...*/
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setupBeforeAll() {
            // run before all tests
        }
    }

    class Shop {
        fun getOrder(orderId: Int): Order {
            return Order(orderId)
        }
    }

    class Order(val orderId: Int) {
        fun getItemsWithDiscount(discountPercentage: Double): List<Item> {
            return listOf()
        }
    }

    class Customer(id: Int, firstName: String, lastName: String, email: String)

    class Item(val product: Product)

    class Product(val name: String, val price: Double, var stock: Int, val category: String)

    private fun calculateTotal(orderItems: List<Item>) {

    }

    private fun processOrder(
        orderId: Int,
        customerId: Int,
        shippingMethod: String,
        paymentMethod: String,
        orderTotal: Double,
    ) {

    }

    private fun applyGroceryDiscount(product: Examples5.Product) {

    }

    private fun applyClothingDiscount(product: Examples5.Product) {

    }

    private fun applyElectronicsDiscount(product: Examples5.Product) {

    }

    private fun notifyAdmin(product: Examples5.Product) {

    }
}
