package service

import model.Destination
import model.DiscountApplier

fun List<Destination>.applyDiscount(
    discountApplier: DiscountApplier,
): List<Destination> {
    return map(discountApplier::invoke)
}

fun String.toDestinationTags() =
    split(",")
        .mapNotNull { tag -> tag.trim().takeIf { it.isNotEmpty() } }
        .toSet()
