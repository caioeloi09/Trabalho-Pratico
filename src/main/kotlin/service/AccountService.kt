package org.eng.soft.two.service

import org.eng.soft.two.entity.Account

class AccountService {
    fun deposit(account: Account, amount: Double) {
        if (amount > 0) {
            account.balance += amount
            println("Depósito de R$$amount realizado com sucesso! Novo saldo: R$${account.balance}")
        } else {
            println("O valor do depósito deve ser positivo.")
        }
    }

    fun withdraw(account: Account, amount: Double) {
        if (amount > 0 && amount <= account.balance) {
            account.balance -= amount
            println("Saque de R$$amount realizado com sucesso! Saldo restante: R$$account.balance")
        } else if (amount > account.balance) {
            println("Saldo insuficiente para realizar o saque.")
        } else {
            println("O valor do saque deve ser positivo.")
        }
    }
}