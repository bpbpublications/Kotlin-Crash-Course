package composition



data class Destination(
    val name: String,
    val price: Double,
    val description: String,
    val tags: Set<String> = setOf(),
)

private val allDestinations = mutableListOf(
    Destination(
        "Paris",
        300.0,
        "The city of love",
        setOf("city trip", "europe")
    ),
    Destination(
        "London",
        350.0,
        "The heart of England",
        setOf("city trip", "europe")
    ),
    Destination(
        "New York",
        400.0,
        "The city that never sleeps",
        setOf("city trip", "america")
    ),
    Destination(
        "Ho Chi Minh City",
        450.0,
        "The dynamic hub of Vietnam",
        setOf("city trip", "asya")
    )
)

val preferredTags = setOf("beach", "historic")
val priceRange = 100.0 to 500.0

val finalDestinations = allDestinations
    .filter { it.tags.intersect(preferredTags).isNotEmpty() }
    .filter { it.price in priceRange.first..priceRange.second }
    .sortedBy { it.price }


fun exampleErrorHanlding(){
    val tags = setOf("beach", "historic")
    fun getPreferredTags(): Result<Set<String>> = runCatching {
        // Simulate a condition that might throw an exception
        if (tags.isNotEmpty()) tags else throw IllegalArgumentException("Tags cannot be empty")
    }

    val finalDestinationsResult = getPreferredTags().map { preferredTags ->
        allDestinations
            .filter { it.tags.intersect(preferredTags).isNotEmpty() }
            .filter { it.price in priceRange.first..priceRange.second }
            .sortedBy { it.price }
    }

    // Usage example
    finalDestinationsResult.fold(
        onSuccess = { finalDestinations ->
            println("Filtered destinations: $finalDestinations")
        },
        onFailure = { error ->
            println("Error retrieving preferred tags: ${error.localizedMessage}")
        }
    )

}



