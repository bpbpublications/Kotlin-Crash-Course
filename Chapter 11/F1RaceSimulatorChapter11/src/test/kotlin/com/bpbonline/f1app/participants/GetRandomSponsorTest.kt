package com.bpbonline.f1app.participants
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlin.random.Random
import kotlin.test.*

class GetRandomSponsorTest {

    @BeforeTest
    fun setUp() {
        mockkObject(Random.Default)
    }

    @AfterTest
    fun tearDown() {
        unmockkObject(Random.Default)
    }

    @Test
    fun `getRandomSponsor returns null when random number is 0`() {
        every { Random.nextInt(0, 11) } returns 0

        val sponsor = getRandomSponsor()

        assertNull(sponsor, "should return null ")
    }

    @Test
    fun `getRandomSponsor returns Sponsor when random number is 10`() {
        every { Random.nextInt(0, 11) } returns 10

        val sponsor = getRandomSponsor()

        assertNotNull(sponsor, "should return sponsor ")
    }
}
