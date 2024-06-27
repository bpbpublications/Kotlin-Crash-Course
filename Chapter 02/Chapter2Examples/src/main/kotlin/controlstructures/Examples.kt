package controlstructures

fun ifElseExample() {
    val order: Order = getOrderById(1)
    if (order.hasDiscount()) {
        // If order has a discount, apply it
        applyDiscount(order)
    } else {
        // Otherwise, proceed without discount
        proceedWithoutDiscount(order)
    }

}

fun whenExample() {
    val productCategory = getProductCategory()

    // Handle different product categories
    when (productCategory) {
        "Electronics" -> handleElectronics()
        "Clothing" -> handleClothing()
        "Groceries" -> handleGroceries()
        else -> handleOthers()
    }

}

fun forLoopExample() {
    // Iterate over a range of numbers
    for (i in 1..5) {
        println("Processing order $i")
    }

    // Iterate over a list of products
    val productList = getProductList()
    for (product in productList) {
        println("Processing product: ${product.name}")
    }
}

fun whileLoopExample() {
    var stock = getStock("ProductA")

    // Keep selling until the stock is empty
    while (stock > 0) {
        sellProduct("ProductA")
        stock--
    }
}

fun sellProduct(s: String) {

}

fun getStock(s: String): Int = 5

fun getProductList(): List<Product> = emptyList()

class Product(val name: String)

fun handleOthers() {
}

fun handleGroceries() {
}

fun handleClothing() {
}

fun handleElectronics() {
}

fun getProductCategory(): String = "Clothing"

fun proceedWithoutDiscount(order: Order) {

}

fun applyDiscount(order: Order) {

}

fun getOrderById(id: Int): Order = Order()

class Order {
    fun hasDiscount(): Boolean = false
}
