package service

import org.eng.soft.two.service.SecurityService
import org.eng.soft.two.entity.Account
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SecurityServiceTest {

    private val securityService = SecurityService()

    @Test
    fun `checkPassword returns false for valid password with digits and letters`() {
        val validPassword = "Pass123"
        assertFalse(securityService.checkPassword(validPassword))
    }

    @Test
    fun `checkPassword returns true for invalid password without digits and letters`() {
        val invalidPassword = "12345"
        assertTrue(securityService.checkPassword(invalidPassword))

        val invalidPassword2 = "abc"
        assertTrue(securityService.checkPassword(invalidPassword2))
    }

    @Test
    fun `assertPasswordCorrect returns true when passwords do not match`() {
        val account = Account(1, 200.0, "password1")
        val password = "wrongPassword"

        assertTrue(securityService.assertPasswordCorrect(password, account))
    }

    @Test
    fun `assertPasswordCorrect returns false when passwords match`() {
        val account = Account(1, 200.0, "password1")
        val password = "password1"

        assertFalse(securityService.assertPasswordCorrect(password, account))
    }
}
