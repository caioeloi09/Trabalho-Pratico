package service.integration

import org.eng.soft.two.entity.Account
import org.eng.soft.two.service.BankService
import org.eng.soft.two.utils.FileManagerUtil
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class BankServiceIT {
    private lateinit var bankService: BankService

    @BeforeEach
    fun setUp() {
        bankService = BankService(FileManagerUtil("data/test.json"))
    }

    @AfterEach
    fun tearDown() {
        val testFile = File("data/test.json")
        if (testFile.exists()) {
            testFile.delete()
        }
        testFile.createNewFile()
    }

    @Test
    fun `should create account with valid initial balance`() {
        val initialBalance = 100.0

        bankService.createAccount(initialBalance)
        bankService.closeBank()

        val fileResult = FileManagerUtil("data/test.json").loadAccounts()
        assertEquals(1, fileResult.size)
        assertEquals(initialBalance, fileResult[1]!!.balance)
    }

    @Test
    fun `should not create account with negative initial balance`() {
        val initialBalance = -50.0

        bankService.createAccount(initialBalance)
        bankService.closeBank()

        val fileResult = FileManagerUtil("data/test.json").loadAccounts()
        assertEquals(0, fileResult.size)
    }

    @Test
    fun `should deposit amount into valid account`() {
        val account = Account(1L, 100.0)
        bankService.accounts[1L] = account
        val depositAmount = 50.0

        bankService.deposit(1L, depositAmount, "Depósito de teste")
        bankService.closeBank()

        val fileResult = FileManagerUtil("data/test.json").loadAccounts()

        assertEquals(1, fileResult.size)
        assertEquals(150.0, fileResult[1L]!!.balance)
    }

    @Test
    fun `should not deposit into invalid account`() {
        bankService.deposit(999L, 50.0, "Depósito inválido")
        bankService.closeBank()

        val fileResult = FileManagerUtil("data/test.json").loadAccounts()
        assertEquals(0, fileResult.size)
    }

    @Test
    fun `should withdraw amount from valid account`() {
        val account = Account(1L, 100.0)
        bankService.accounts[1L] = account
        val withdrawAmount = 30.0

        bankService.withdraw(1L, withdrawAmount, "Saque de teste")
        bankService.closeBank()

        val fileResult = FileManagerUtil("data/test.json").loadAccounts()

        assertEquals(1, fileResult.size)
        assertEquals(70.0, fileResult[1L]!!.balance)
    }
}