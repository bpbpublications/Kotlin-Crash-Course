package basics
import kotlin.test.*

class CalculatorTest {
    private lateinit var calculator: Calculator

    @BeforeTest
    fun setUp() {
        // Initialize the System Under Test
        calculator = Calculator()
    }

    @Test
    fun testAddition() {
        assertEquals(5, calculator.add(2, 3), "2 + 3 = 5")
    }

    @Test
    fun `when 2 substruct from 3 then result is 1`() {
        assertEquals(1, calculator.subtract(3, 2), "3 - 2 = 1")
    }
}