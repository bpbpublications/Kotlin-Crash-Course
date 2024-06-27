package lists

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
)

val products = listOf(
    Product(1, "Laptop", 1000.0),
    Product(2, "Phone", 1500.0),
    Product(3, "Watch", 250.0),
    Product(4, "Tablet", 500.0),
)

// Immutable list
val products1: List<Product> = listOf(Product(1, "Laptop", 1000.0))
// Mutable list
val products2: MutableList<Product> = mutableListOf()

// Represents bulk discounts: [0%, 2%, 4%, 6%, 8%, ... 18%]
val bulkDiscountPercentages = List(10) { it * 2 }

// Results in [1001, 1002, ... 1010] representing a range of product IDs
val productIds = (1001..1010).toList()

val firstProduct = products[0]

fun buildInExamples(){
    val firstProduct = products.first()
    val lastProduct = products.last()
}

// Returns null if index 5 is out of bounds.
val product = products.getOrNull(5)

val electronics = listOf(
    Product(1, "Laptop", 1000.0),
    Product(2, "Phone", 500.0),
)
val furniture = listOf(
    Product(3, "Chair", 50.0),
    Product(4, "Table", 100.0),
)
val allProducts = electronics + furniture  // Combines both lists

val newProduct = Product(5, "Sofa", 300.0)
// Adds newProduct to the end of allProducts
val updatedCatalog = allProducts + newProduct

fun mutableExamples(){
    val products = mutableListOf<Product>()
    products.add(Product(4, "Monitor", 250.0))
    products.add(1, Product(5, "Keyboard", 50.0))
    products.removeAt(1)
    products.clear()
}

val sub = products.subList(0, 2)
val reversed = products.reversed()
val shuffled = products.shuffled()
val sliced = products.slice(0..2)

fun equalityExample(){
    val list1: List<Product> = listOf(Product(1, "Laptop", 1000.0))
    val list2: List<Product> = listOf(Product(1, "Laptop", 1000.0))
    println(list1 == list2)  // Prints true.
    println(list1 === list2)  // Prints false
}

val fasterReadProducts = arrayListOf(product)