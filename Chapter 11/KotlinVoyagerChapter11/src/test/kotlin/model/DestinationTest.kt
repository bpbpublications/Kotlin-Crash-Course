package model
import kotlin.test.*

class DestinationTest {
    private lateinit var destination1: Destination
    private lateinit var destination2: Destination

    @BeforeTest
    fun setUp() {
        destination1 = Destination(
            "New York",
            400.0,
            "The city that never sleeps",
            setOf("city trip", "america")
        )
        destination2 = Destination(
            "Ho Chi Minh City",
            450.0,
            "The dynamic hub of Vietnam",
            setOf("city trip", "asya")
        )
    }

    @Test
    fun `combineWith should combine two destinations`(){
        val combined = destination1 combineWith destination2
        assertEquals(425.0, combined.price)
        assertEquals("New York & Ho Chi Minh City", combined.name)
    }

    @Test
    fun `plus should combine two destinations`(){
        val combined = destination1 + destination2
        assertEquals(425.0, combined.price)
        assertEquals("New York & Ho Chi Minh City", combined.name)
    }
}