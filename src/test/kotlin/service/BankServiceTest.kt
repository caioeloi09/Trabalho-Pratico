package service

import io.mockk.every
import io.mockk.mockk
import org.eng.soft.two.entity.Account
import org.eng.soft.two.service.BankService
import org.eng.soft.two.utils.FileManagerUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BankServiceTest {

    private val mockFileManager = mockk<FileManagerUtil>(relaxed = true)
    private lateinit var bankService: BankService

    @BeforeEach
    fun setUp() {
        every { mockFileManager.loadAccounts() } returns mutableMapOf()
        bankService = BankService(FileManagerUtil("data/test.json"))
    }

    @Test
    fun `should create account with valid initial balance`() {
        val initialBalance = 100.0
        val accountNumber = bankService.createAccount(initialBalance)

        assertEquals(1, bankService.accounts.size, "Deveria haver uma conta criada.")
        assertEquals(initialBalance, bankService.accounts[accountNumber]?.balance, "O saldo inicial está incorreto.")
    }

    @Test
    fun `should not create account with negative initial balance`() {
        val initialBalance = -50.0
        val accountNumber = bankService.createAccount(initialBalance)

        assertEquals(-1, accountNumber, "Conta com saldo inicial negativo não deveria ser criada.")
        assertTrue(bankService.accounts.isEmpty(), "Nenhuma conta deveria ser criada.")
    }

    @Test
    fun `should deposit amount into valid account`() {
        val account = Account(1L, 100.0)
        bankService.accounts[1L] = account
        val depositAmount = 50.0

        bankService.deposit(1L, depositAmount, "Depósito de teste")

        assertEquals(150.0, account.balance, "O saldo da conta não foi atualizado corretamente.")
    }

    @Test
    fun `should not deposit into invalid account`() {
        bankService.deposit(999L, 50.0, "Depósito inválido")

        assertTrue(bankService.accounts.isEmpty(), "Não deveria haver contas para depósito inválido.")
    }

    @Test
    fun `should withdraw amount from valid account`() {
        val account = Account(1L, 100.0)
        bankService.accounts[1L] = account
        val withdrawAmount = 30.0

        bankService.withdraw(1L, withdrawAmount, "Saque de teste")

        assertEquals(70.0, account.balance, "O saldo da conta não foi atualizado corretamente.")
    }

    @Test
    fun `should not withdraw amount greater than account balance`() {
        val account = Account(1L, 100.0)
        bankService.accounts[1L] = account

        bankService.withdraw(1L, 150.0, "Saque inválido")

        assertEquals(100.0, account.balance, "O saldo da conta não deveria ter sido alterado.")
    }

    @Test
    fun `should transfer amount between valid accounts`() {
        val sourceAccount = Account(1L, 100.0)
        val destinationAccount = Account(2L, 50.0)
        bankService.accounts[1L] = sourceAccount
        bankService.accounts[2L] = destinationAccount

        val transferAmount = 30.0
        bankService.transfer(1L, 2L, transferAmount, "Transferência de teste")

        assertEquals(70.0, sourceAccount.balance, "O saldo da conta de origem não foi atualizado corretamente.")
        assertEquals(80.0, destinationAccount.balance, "O saldo da conta de destino não foi atualizado corretamente.")
    }

    @Test
    fun `should not transfer amount if source account has insufficient funds`() {
        val sourceAccount = Account(1L, 20.0)
        val destinationAccount = Account(2L, 50.0)
        bankService.accounts[1L] = sourceAccount
        bankService.accounts[2L] = destinationAccount

        val transferAmount = 30.0
        bankService.transfer(1L, 2L, transferAmount, "Transferência inválida")

        assertEquals(20.0, sourceAccount.balance, "O saldo da conta de origem não deveria ter sido alterado.")
        assertEquals(50.0, destinationAccount.balance, "O saldo da conta de destino não deveria ter sido alterado.")
    }

    @Test
    fun `should print account statement for valid account`() {
        val account = Account(1L, 100.0, mutableListOf())
        bankService.accounts[1L] = account


        assertDoesNotThrow { bankService.printAccountStatement(1L) }
    }

    @Test
    fun `should not print account statement for invalid account`() {
        assertDoesNotThrow { bankService.printAccountStatement(999L) }
    }
}
