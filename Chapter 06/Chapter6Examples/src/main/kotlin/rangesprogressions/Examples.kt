package rangesprogressions

fun closedEndedRangeExample(){
    val budgetRange = 200.0..500.0  // Equivalent to 200.0.rangeTo(500.0)
    val phone = Product(2, "Phone", 450.0)
    if (phone.price in budgetRange) {
        println("${phone.name} fits within the budget!")
    }
}

fun openEndedRangeExample(){
    val exclusiveDiscountRange = 200.0..<500.0  // Equivalent to 200.0.rangeUntil(500.0)
    val tablet = Product(3, "Tablet", 500.0)
    if (tablet.price !in exclusiveDiscountRange) {
        println("${tablet.name} is not eligible for the discount!")
    }
}

fun progressionExample(){
    val productTags = 1..10 // A range.
    for (tag in productTags step 2) { // A progression with a step of 2.
        println("Product with tag $tag is on discount!")
    }
}

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
)
