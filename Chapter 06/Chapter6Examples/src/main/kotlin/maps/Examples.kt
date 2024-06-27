package maps

// Read-only map
val productPrices: Map<Int, Double> =
    mapOf(1 to 1000.0, 2 to 500.0)

// Mutable map
val inventory: MutableMap<Int, Int> =
    mutableMapOf(1 to 10, 2 to 15)

val priceOfLaptop = productPrices[1]

// Returns 0.0 if key 3 is not present.
val price = productPrices.getOrDefault(3, 0.0)

fun addOrUpdateExample() {
    // Adds or updates the inventory for product ID 3
    inventory[3] = 20
}

fun removeExample() {
    inventory.remove(2)  // Removes the entry with key 2
}

fun checkExample() {
    val hasProduct1 = 1 in productPrices
    val hasPrice1000 =
        productPrices.containsValue(1000.0)
}

fun iterateExample(){
    for ((productId, count) in inventory) {
        println("Product ID: $productId has $count items in stock.")
    }
}

fun equalsExample(){
    val map1: Map<Int, Double> = mapOf(1 to 1050.0, 2 to 555.0)
    val map2: Map<Int, Double> = mapOf(2 to 555.0, 1 to 1050.0)
    // Prints true since both maps have the same key-value pairs.
    println(map1 == map2)
    // Prints false since they are different objects in memory.
    println(map1 === map2)
}

fun main() {
    equalsExample()
}