package org.eng.soft.two.service

import org.eng.soft.two.entity.Account
import org.eng.soft.two.entity.Transaction
import org.eng.soft.two.vo.TransactionVO
import java.time.LocalDateTime

class AccountService {
    private var transactionIdCounter = 1L

    fun deposit(account: Account, vo: TransactionVO) {
        if (vo.amount > 0) {
            account.balance += vo.amount
            println("Depósito de R$${vo.amount} realizado com sucesso! Novo saldo: R$${account.balance}")
            addingTransaction(account, vo)
        } else {
            println("O valor do depósito deve ser positivo.")
        }
    }

    fun withdraw(account: Account, vo: TransactionVO) {
        if (vo.amount > 0 && vo.amount <= account.balance) {
            account.balance -= vo.amount
            println("Saque de R$${vo.amount} realizado com sucesso! Saldo restante: R$${account.balance}")
            addingTransaction(account, vo)
        } else if (vo.amount > account.balance) {
            println("Saldo insuficiente para realizar o saque.")
        } else {
            println("O valor do saque deve ser positivo.")
        }
    }

    private fun addingTransaction(account: Account, vo: TransactionVO) {
        account.transactions.add(Transaction(
            id = transactionIdCounter++,
            accountId = account.id,
            type = vo.type,
            amount = vo.amount,
            timestamp = LocalDateTime.now().toString(),
            description = vo.description
        ))
    }
}