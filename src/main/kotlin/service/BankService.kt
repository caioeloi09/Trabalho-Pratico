package org.eng.soft.two.service

import org.eng.soft.two.entity.Account
import org.eng.soft.two.enum.TransactionTypeEnum
import org.eng.soft.two.utils.FileManagerUtil
import org.eng.soft.two.vo.TransactionVO

class BankService(private val fileManager: FileManagerUtil) {
    val accounts: MutableMap<Long, Account> = fileManager.loadAccounts()
    private var nextAccountNumber = accounts.keys.maxOrNull()?.plus(1) ?: 1L
    private val accountService: AccountService = AccountService()

    fun createAccount(initialBalance: Double): Long {
        if (initialBalance < 0) {
            println("O saldo inicial não pode ser negativo.")
            return -1
        }
        val account = Account(nextAccountNumber, initialBalance)
        accounts[nextAccountNumber] = account
        println("Conta criada com sucesso! Número da conta: ${account.id}, Saldo inicial: R$${account.balance}")
        return nextAccountNumber++
    }

    fun transfer(fromAccount: Long, toAccount: Long, amount: Double, description: String) {
        val source = getAccount(fromAccount)
        val destination = getAccount(toAccount)

        if (!isValidTransfer(source, destination, amount)) {
            println("Transefência inválida, retornando.")
            return
        }

        val vo = TransactionVO(amount, TransactionTypeEnum.TRANSFER, description)

        if (source!!.balance >= amount) {
            accountService.withdraw(source, vo)
            accountService.deposit(destination!!, vo)
            println("Transferência de R$${amount} da conta ${fromAccount} para a conta ${toAccount} realizada com sucesso!")
        } else {
            println("Saldo insuficiente na conta $fromAccount para realizar a transferência.")
        }
    }

    fun deposit(toAccount: Long, amount: Double, description: String) {
        val source = getAccount(toAccount)
        if (isValidAccount(source)) {
            accountService.deposit(source!!, TransactionVO(amount, TransactionTypeEnum.DEPOSIT, description))
        } else{
            println("Conta inválida. Retornando.")
            return
        }
    }

    fun withdraw(toAccount: Long, amount: Double, description: String) {
        val source = getAccount(toAccount)
        if (isValidAccount(source)) {
            accountService.withdraw(source!!, TransactionVO(amount, TransactionTypeEnum.WITHDRAW, description))
        } else {
            println("Conta inválida. Retornando.")
            return
        }
    }

    fun closeBank(){
        fileManager.saveAccounts(accounts)
    }

    fun printAccountStatement(accountNumber: Long) {
        val account = getAccount(accountNumber)
        if (!isValidAccount(account)) {
            println("Conta não encontrada.")
            return
        }
        println("\n=== Extrato da Conta ${account!!.id} ===")
        account.transactions.forEach {
            println("${it.timestamp} - ${it.type.name.uppercase()} - R$${it.amount} - ${it.description}")
        }
        println("Saldo atual: R$${account.balance}")
    }

    fun printBankSummary() {
        println("\n=== Resumo do Banco ===")
        println("Total de contas: ${accounts.size}")
        val totalBalance = accounts.values.sumOf { it.balance }
        println("Saldo total no banco: R$${"%.2f".format(totalBalance)}")
        println("=== Fim do Resumo ===")
    }

    private fun getAccount(accountNumber: Long): Account? {
        return accounts[accountNumber]
    }

    private fun isValidTransfer(source: Account?, destination: Account?, amount: Double): Boolean {
        return isValidAccount(source) && isValidAccount(destination) && amount > 0
    }

    private fun isValidAccount(account: Account?): Boolean {
        return account != null
    }
}