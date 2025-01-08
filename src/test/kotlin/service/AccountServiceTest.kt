package service

import org.eng.soft.two.entity.Account
import org.eng.soft.two.enum.TransactionTypeEnum
import org.eng.soft.two.service.AccountService
import org.eng.soft.two.vo.TransactionVO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AccountServiceTest {

    private lateinit var accountService: AccountService
    private lateinit var testAccount: Account

    @BeforeEach
    fun setUp() {
        accountService = AccountService()
        testAccount = Account(
            id = 1L,
            balance = 100.0,
            transactions = mutableListOf()
        )
    }

    @Test
    fun `should deposit amount to account and add transaction`() {
        // Mock Data
        val depositVO = TransactionVO(
            amount = 50.0,
            type = TransactionTypeEnum.DEPOSIT,
            description = "Depósito de teste"
        )

        // Action
        accountService.deposit(testAccount, depositVO)

        // Assert
        assertEquals(150.0, testAccount.balance, "O saldo não foi atualizado corretamente após o depósito.")
        assertEquals(1, testAccount.transactions.size, "A transação não foi registrada.")
        val transaction = testAccount.transactions[0]
        assertEquals(TransactionTypeEnum.DEPOSIT, transaction.type, "O tipo de transação está incorreto.")
        assertEquals(50.0, transaction.amount, "O valor da transação está incorreto.")
        assertEquals("Depósito de teste", transaction.description, "A descrição da transação está incorreta.")
    }

    @Test
    fun `should not deposit negative amount`() {
        // Mock Data
        val depositVO = TransactionVO(
            amount = -50.0,
            type = TransactionTypeEnum.DEPOSIT,
            description = "Depósito inválido"
        )

        // Action
        accountService.deposit(testAccount, depositVO)

        // Assert
        assertEquals(100.0, testAccount.balance, "O saldo foi alterado incorretamente.")
        assertTrue(testAccount.transactions.isEmpty(), "Não deveria haver transações registradas.")
    }

    @Test
    fun `should withdraw amount from account and add transaction`() {
        // Mock Data
        val withdrawVO = TransactionVO(
            amount = 30.0,
            type = TransactionTypeEnum.WITHDRAW,
            description = "Saque de teste"
        )

        // Action
        accountService.withdraw(testAccount, withdrawVO)

        // Assert
        assertEquals(70.0, testAccount.balance, "O saldo não foi atualizado corretamente após o saque.")
        assertEquals(1, testAccount.transactions.size, "A transação não foi registrada.")
        val transaction = testAccount.transactions[0]
        assertEquals(TransactionTypeEnum.WITHDRAW, transaction.type, "O tipo de transação está incorreto.")
        assertEquals(30.0, transaction.amount, "O valor da transação está incorreto.")
        assertEquals("Saque de teste", transaction.description, "A descrição da transação está incorreta.")
    }

    @Test
    fun `should not withdraw amount greater than balance`() {
        // Mock Data
        val withdrawVO = TransactionVO(
            amount = 150.0,
            type = TransactionTypeEnum.WITHDRAW,
            description = "Saque maior que saldo"
        )

        // Action
        accountService.withdraw(testAccount, withdrawVO)

        // Assert
        assertEquals(100.0, testAccount.balance, "O saldo foi alterado incorretamente.")
        assertTrue(testAccount.transactions.isEmpty(), "Não deveria haver transações registradas.")
    }

    @Test
    fun `should not withdraw negative amount`() {
        // Mock Data
        val withdrawVO = TransactionVO(
            amount = -30.0,
            type = TransactionTypeEnum.WITHDRAW,
            description = "Saque inválido"
        )

        // Action
        accountService.withdraw(testAccount, withdrawVO)

        // Assert
        assertEquals(100.0, testAccount.balance, "O saldo foi alterado incorretamente.")
        assertTrue(testAccount.transactions.isEmpty(), "Não deveria haver transações registradas.")
    }
}
