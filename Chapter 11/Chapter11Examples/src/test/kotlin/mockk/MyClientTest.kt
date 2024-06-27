package mockk

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MyClientTest {
    private lateinit var myClient: MyClient

    val service = mockk<MyService>()
    val serviceRelaxed = mockk<MyService>(relaxed = true)
    val serviceWithAnswer = mockk<MyService> {
        every { this@mockk.someFunction(any()) } answers { "Mock response" }
    }

    @MockK
    lateinit var serviceWithAnnotation: MyService

    @RelaxedMockK
    lateinit var serviceRelaxedWithAnnotation: MyService

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        myClient = MyClient(service)
    }

    @AfterTest
    fun tearDown() {
        clearMocks(
            service,
            serviceRelaxed,
            serviceWithAnswer,
            serviceWithAnnotation,
            serviceRelaxedWithAnnotation
        )
        unmockkAll()
    }


    @Test
    fun `performAction should return formatted response`() {
        // Given: The stubbing phase
        every { service.someFunction(any()) } returns "Mock response"
        // When: The action phase
        val result = myClient.performAction("Some input")
        // Then: The verification phase
        assertEquals(
            "MyService returned Mock response",
            result
        )
        verify(exactly = 1) { service.someFunction("Some input") }
    }

    @Test
    fun someTest2() {
        myClient = MyClient(serviceWithAnnotation)

        every { serviceWithAnnotation.someFunction(any()) } returns "Mock response"

        val result = myClient.performAction("Some input")

        assertEquals(
            "MyService returned Mock response",
            result
        )
        verify(exactly = 1) {
            serviceWithAnnotation.someFunction(
                "Some input"
            )
        }
    }

    @Test
    fun someTest3() {
        myClient = MyClient(serviceWithAnswer)

        val result = myClient.performAction("Some input")

        assertEquals(
            "MyService returned Mock response",
            result
        )
        verify(exactly = 1) {
            serviceWithAnswer.someFunction(
                "Some input"
            )
        }
    }

    @Test
    fun `performActionWithCallToSuspendingFunction should fetch data`() {
        // Given: The stubbing phase
        val serviceWithSuspendingFunction = mockk<MyService> {
            coEvery { this@mockk.fetchData(1) } returns "Mock response"
        }
        myClient = MyClient(serviceWithSuspendingFunction)
        // When: The action phase
        val result = myClient.performActionWithCallToSuspendingFunction(1)
        // Then: The verification phase
        assertEquals(
            "MyService returned Mock response",
            result
        )
        coVerify(exactly = 1) { serviceWithSuspendingFunction.fetchData(1) }
    }
}
