package objects

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlin.test.Test
import kotlin.test.assertEquals

class ClassWithCompanionObject {
    fun useCompanionMethod() = myCompanionMethod()

    companion object {
        fun myCompanionMethod(): String =
            "Original Response"
    }
}

internal class ClassWithCompanionObjectTest {
    @Test
    fun testMockObject() {
        // This tells MockK to mock the companion object
        mockkObject(ClassWithCompanionObject.Companion)
        // Here we specify that whenever myCompanionMethod is called
        every {
            ClassWithCompanionObject.myCompanionMethod()
        } returns "Mock Response"
        // When useCompanionMethod is called, it calls myCompanionMethod,
        // which has been mocked to return "Mock Response"
        val actualResponse = ClassWithCompanionObject().useCompanionMethod()
        // We assert that the actual response matches the expected response
        assertEquals("Mock Response", actualResponse)
        // Finish mocking to avoid side effects
        unmockkObject(ClassWithCompanionObject.Companion)
    }
}




