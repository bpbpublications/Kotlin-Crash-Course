package service

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import model.Destination
import model.DiscountApplier
import kotlin.test.BeforeTest
import kotlin.test.*

class DestinationServicesKtTest {
    private val discountApplierMock = mockk<DiscountApplier>()
    private lateinit var destinationList: List<Destination>
    private lateinit var destination1: Destination
    private lateinit var destination2: Destination

    private val discountDestination1Mock = mockk<Destination>()
    private val discountDestination2Mock = mockk<Destination>()

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
        destinationList = listOf(
            destination1,
            destination2,
        )
    }

    @Test
    fun `should apply discount to destinations`() {
        // Given
        every {
            discountApplierMock.invoke(destination1)
        } returns discountDestination1Mock
        every {
            discountApplierMock.invoke(destination2)
        } returns discountDestination2Mock
        // When
        val discountedDestinationList = destinationList.applyDiscount(
            discountApplierMock
        )
        // Then
        assertEquals(2, discountedDestinationList.size)
        assertEquals(discountDestination1Mock, discountedDestinationList[0])
        assertEquals(discountDestination2Mock, discountedDestinationList[1])
    }

    @AfterTest
    fun tearDown() {
        // Reset the mock after each test
        clearMocks(discountApplierMock)
    }

}
