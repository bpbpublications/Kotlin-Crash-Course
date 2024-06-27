package lambdas

fun applyDiscount(
    price: Double,
    discountStrategy: (Double) -> Double,
) = discountStrategy(price)


// 10% off
val seasonalDiscount = { price: Double -> price * 0.9 }

// 15% off
val promotionalDiscount =
    { price: Double -> price * 0.85 }

val finalPrice1 = applyDiscount(1000.0, seasonalDiscount)
val finalPrice2 =
    applyDiscount(1000.0, promotionalDiscount)

val destinations =
    listOf("Paris", "London", "Bangkok", "Sydney")
val sortedDestinations =
    destinations.sortedBy { it.length }

data class Destination(
    val name: String,
    val price: Double,
)

fun exampleLambdaWithDynamicUserPreference() {
    val destinations = listOf(
        Destination("Paris", 300.50),
        Destination("London", 250.75),
        Destination("Bangkok", 450.25),
        Destination("Sydney", 800.99)
    )

    val maxPrice =
        400.00 // This comes from dynamic user preferences

    val affordable =
        destinations.filter { it.price <= maxPrice }
}

fun applyDiscountWithValidation(
    price: Double,
    discountStrategy: (Double) -> Double,
): Result<Double> {
    return if (price > 0) {
        Result.success(discountStrategy(price))
    } else {
        Result.failure(
            IllegalArgumentException(
                "Price must be positive to apply discount."
            )
        )
    }
}


fun useDiscountWithValidation() {
    val discountedPriceResult =
        applyDiscountWithValidation(
            1000.0,
            seasonalDiscount,
        )
    discountedPriceResult.onSuccess { price ->
        println("Discounted price: $price")
    }.onFailure { error ->
        println("Error applying discount: ${error.message}")
    }
}



