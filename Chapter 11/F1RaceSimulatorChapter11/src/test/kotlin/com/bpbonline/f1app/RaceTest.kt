package com.bpbonline.f1app
import com.bpbonline.f1app.participants.Driver
import com.bpbonline.f1app.participants.RaceCar
import com.bpbonline.f1app.participants.Team
import io.mockk.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class RaceTest {

    private lateinit var race: Race
    private val raceResult = mockk<Race.Result>(relaxed = true)
    private val raceCar = mockk<RaceCar>(relaxed = true)
    private val driver = mockk<Driver>(relaxed = true)
    private val team = mockk<Team>(relaxed = true)

    @BeforeTest
    fun setUp() {
        race = Race(numberOfLaps = 1, teams = listOf())
        mockkStatic(::generateRaceEvent)

    }

    @AfterTest
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `simulateLap should throw YellowFlagException on breakdown`() =
        runTest {
            every { generateRaceEvent() } returns RaceEvent.BREAKDOWN
            every { raceResult.component1() } returns team
            every { raceResult.component2() } returns driver
            every { raceResult.component3() } returns raceCar
            every { raceCar.carNumber } returns 1

            val exception = assertFailsWith<YellowFlagException> {
                race.simulateLap(raceResult)
            }
            assertEquals("Car 1 broke down - pit stop!", exception.message)
            verify { raceCar.isPitStopNeeded = true }
        }

}
