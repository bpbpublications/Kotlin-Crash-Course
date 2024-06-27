package transformations

data class Product(val name: String, val price: Double)
fun mapExample() {
    val products =
        listOf(Product("Shirt", 20.0), Product("Hat", 50.0))

    val productNames = products.map { it.name }
    // Result: ["Shirt", "Hat"]
}

fun flatMapExample() {
    data class Product(
        val name: String,
        val price: Double,
        val tags: List<String>,
    )
    val products = listOf(
        Product("Laptop", 1000.0, listOf("Electronics", "Portable")),
        Product( "Phone", 500.0, listOf("Electronics", "Mobile"))
    )
    val allTags = products.flatMap { it.tags }
    // Result: ["Electronics", "Portable", "Electronics", "Mobile"]
}

fun flattenExample(){
    // Define two groups of products
    val group1 = listOf(
        Product("Laptop", 1000.0),
        Product("Phone", 500.0)
    )
    val group2 = listOf(
        Product("Tablet", 300.0),
        Product("Headphones", 100.0)
    )
    // Group them together
    val productGroups: List<List<Product>> = listOf(group1, group2)

    // Flatten the grouped list
    val allProducts = productGroups.flatten()
    // Result: [Product(name=Laptop, price=1000.0), Product(name=Phone, price=500.0), Product(name=Tablet, price=300.0), Product(name=Headphones, price=100.0)]

    println(allProducts)

}

fun associateExample(){
    // Sample list of products
    val products = listOf(
        Product("Laptop", 1000.0),
        Product("Phone", 500.0),
        Product("Headphones", 100.0),
        Product("Tablet", 300.0)
    )

    // Create a map with product names as keys and their prices as values
    val nameToPriceMap = products.associate { it.name to it.price }

    // Print the result
    println(nameToPriceMap)
   // Result: {"Laptop"=1000.0, "Phone"=500.0, "Headphones"=100.0, "Tablet"=300.0}

}

fun toSetExample(){
    // Sample list of products
    val products = listOf(
        Product("Laptop", 1000.0),
        Product("Phone", 500.0),
        Product("Headphones", 100.0),
        Product("Tablet", 300.0)
    )

    val productNamesSet = products.map { it.name }.toSet()

}
