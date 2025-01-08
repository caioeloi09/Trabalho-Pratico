package utils

import io.mockk.*
import org.eng.soft.two.entity.Account
import org.eng.soft.two.service.SecurityService
import org.eng.soft.two.utils.ReadingUtils
import org.junit.jupiter.api.*
import kotlin.test.assertEquals

class ReadingUtilsTest {

    private val securityService: SecurityService = mockk()
    private val readingUtils = ReadingUtils()


    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should return correct password when input is valid`() {
        val validPassword = "password1"

        every { securityService.assertPasswordCorrect(validPassword, mockAccount()) } returns true

        mockInputOutput(listOf(validPassword))

        val result = readingUtils.readPassword(mockAccount())

        assertEquals(validPassword, result)
    }

    @Test
    fun `should handle incorrect password with retry`() {
        val incorrectPassword = "wrong_pass"
        val correctPassword = "password1"

        every { securityService.assertPasswordCorrect(incorrectPassword, mockAccount()) } returns true
        every { securityService.assertPasswordCorrect(correctPassword, mockAccount()) } returns true

        mockInputOutput(listOf(incorrectPassword, "2", correctPassword))

        val result = readingUtils.readPassword(mockAccount())

        assertEquals(correctPassword, result)
    }

    @Test
    fun `should exit correctly when choice to back`() {
        val incorrectPassword = "wrong_pass"

        every { securityService.assertPasswordCorrect(incorrectPassword, mockAccount()) } returns true

        mockInputOutput(listOf(incorrectPassword, "1"))

        val result = readingUtils.readPassword(mockAccount())

        assertEquals("", result)
    }

    private fun mockInputOutput(inputs: List<String>) {
        val inputIterator = inputs.iterator()

        mockkStatic(::readlnOrNull)

        every { readlnOrNull() } answers { inputIterator.next() }
    }

    private fun mockAccount(): Account =
        Account(
            id = 1,
            balance = 200.0,
            password = "password1",
        )
}

