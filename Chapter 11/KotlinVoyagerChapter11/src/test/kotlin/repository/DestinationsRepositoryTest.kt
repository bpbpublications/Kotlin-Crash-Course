package repository

import model.Destination
import kotlin.test.*

class DestinationsRepositoryTest {
    @Test
    fun `filterDestinations should return correct destinations`() {
        // Given
        val criteria: (Destination) -> Boolean = { it.price < 400 }
        // When
        val result = DestinationsRepository.filterDestinations(criteria)
        // Then
        assertTrue(result.all { it.price < 400 })
        assertEquals(2, result.size)
    }

    @Test
    fun `addDestination should add a new destination`() {
        // Given
        val newDestination = Destination(
            "Tokyo",
            500.0,
            "The capital of Japan",
            setOf("city trip", "asia"),
        )
        // When
        DestinationsRepository.addDestination(newDestination)
        // Then
        val tokyoDestinations = DestinationsRepository.filterDestinations(
            { it.name == "Tokyo" }
        )
        assertTrue(
            tokyoDestinations.isNotEmpty(),
            "Tokyo should be added to destinations"
        )
    }

}

