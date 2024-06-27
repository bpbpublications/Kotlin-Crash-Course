package sets

import java.util.*

// Immutable set
val categories: Set<ProductCategory> =
    ProductCategory.values().toSet()

val householdCategories: Set<ProductCategory> = setOf(
    ProductCategory.FURNITURE,
    ProductCategory.BEDDING,
    ProductCategory.KITCHENWARE
)

val personalCategories: Set<ProductCategory> = setOf(
    ProductCategory.CLOTHING,
    ProductCategory.TOYS,
    ProductCategory.BEDDING,
)

// Mutable set
val mutableCategories: MutableSet<ProductCategory> =
    mutableSetOf()


fun loopExample() {
    for (category in categories) {
        println(category)
    }
}

fun addItemsExample() {
    mutableCategories.add(ProductCategory.CLOTHING)
    mutableCategories.remove(ProductCategory.FURNITURE)
}

fun checkExample() {
    val hasElectronics =
        ProductCategory.ELECTRONICS in categories
}

// Result: [FURNITURE, BEDDING, KITCHENWARE, CLOTHING, TOYS]
val combinedCategories =
    householdCategories union personalCategories

// Result: [BEDDING]
val commonCategories =
    householdCategories intersect personalCategories

// Result: [FURNITURE, KITCHENWARE]
val exclusiveHousehold =
    householdCategories subtract personalCategories

val productIds = listOf(1001, 1001, 1002, 1003)
val uniqueIds = productIds.toSet()

val idList = uniqueIds.toList()

fun equalityExample() {
    val set1 = setOf(
        ProductCategory.FURNITURE,
        ProductCategory.BEDDING,
        ProductCategory.KITCHENWARE
    )
    val set2 = setOf(
        ProductCategory.BEDDING,
        ProductCategory.FURNITURE,
        ProductCategory.KITCHENWARE
    )
    // Prints true as both have the same elements.
    println(set1 == set2)
    // Prints false as both references point to different object.
    println(set1 === set2)
}

enum class ProductCategory {
    ELECTRONICS, CLOTHING, FURNITURE, BEDDING, KITCHENWARE, TOYS
}

val categorySet = hashSetOf(
    ProductCategory.FURNITURE,
    ProductCategory.ELECTRONICS,
)
val orderedCategories = linkedSetOf(
    ProductCategory.FURNITURE,
    ProductCategory.ELECTRONICS
)
val sortedCategories = TreeSet<ProductCategory>()



