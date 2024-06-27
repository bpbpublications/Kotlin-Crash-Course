package sorting

import java.util.*
import kotlin.collections.LinkedHashSet

val prices = listOf(10.0, 5.0, 20.0, 15.0)

// Sorting in ascending order
val sortedPrices = prices.sorted()
// [5.0, 10.0, 15.0, 20.0]

// Sorting in descending order
val sortedPricesDesc = prices.sortedDescending()
//  [20.0, 15.0, 10.0, 5.0]

data class Product(val name: String, val price: Double)

val products =
    listOf(Product("Shirt", 20.0), Product("Hat", 50.0))

// Sorting in ascending order by name
val sortedByName = products.sortedBy { it.name }
// [Product(name=Hat, price=50.0), Product(name=Shirt, price=20.0)]

// Sorting in descending order by name
val sortedByNameDesc =
    products.sortedByDescending { it.name }
// [Product(name=Shirt, price=20.0), Product(name=Hat, price=50.0)]

val reversedProducts = products.reversed()

fun naturalOrderExamples() {
    data class Product(
        val name: String,
        val price: Double,
    ) : Comparable<Product> {
        override fun compareTo(other: Product): Int {
            return price.compareTo(other.price)
        }
    }

    val products: List<Product> = listOf(
        Product("Shirt", 20.0),
        Product("Hat", 15.0)
    )
    val sortedProducts = products.sorted()
    // [Product(name=Hat, price=15.0), Product(name=Shirt, price=20.0)]

    val productSet = LinkedHashSet<Product>()
    productSet.add(Product( "Laptop", 1000.0))
    //... add more products

    val productTreeSet = TreeSet<Product>()
    productTreeSet.add(Product("Laptop", 1000.0))
    //... kept in order based on our Comparable implementation

}

fun main() {
    naturalOrderExamples()
}

