package model

fun interface DiscountApplier {
    operator fun invoke(destination: Destination): Destination
}
