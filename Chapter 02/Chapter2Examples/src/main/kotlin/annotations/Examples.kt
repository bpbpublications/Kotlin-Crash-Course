package annotations

@Deprecated("Use calculateDiscountedPrice() instead")
fun oldPriceCalculation() {
    // ...
}

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class MinPrice(val value: Double)

data class Product(
    val id: Int, val name: String,
    @MinPrice(0.0) val price: Double
)
