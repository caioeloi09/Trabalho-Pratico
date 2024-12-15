package org.eng.soft.two

import org.eng.soft.two.service.BankService
import org.eng.soft.two.utils.FileManagerUtil
import java.util.*

fun main() {
    val bankSystem = BankService(FileManagerUtil("data/accounts.json"))
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\n=== Sistema Bancário ===")
        println("1. Criar nova conta")
        println("2. Realizar depósito")
        println("3. Realizar saque")
        println("4. Transferir entre contas")
        println("5. Ver extrato de conta")
        println("6. Resumo do banco")
        println("7. Sair")
        print("Escolha uma opção: ")

        when (scanner.nextInt()) {
            1 -> {
                print("Digite o saldo inicial: ")
                val initialBalance = scanner.nextDouble()
                bankSystem.createAccount(initialBalance)
            }
            2 -> {
                var description = ""
                print("Digite o número da conta: ")
                val accountNumber = scanner.nextLong()
                print("Digite o valor do depósito: ")
                val amount = scanner.nextDouble()
                println("Gostaria de adicionar uma descrição para o depósito?")
                println("1. Sim\n2. Não")
                if (scanner.nextInt() == 1) {
                    scanner.nextLine()
                    print("Digite a descrição: ")
                    description = scanner.nextLine()
                }
                bankSystem.deposit(accountNumber, amount, description)
            }
            3 -> {
                var description = ""
                print("Digite o número da conta: ")
                val accountNumber = scanner.nextLong()
                print("Digite o valor do saque: ")
                val amount = scanner.nextDouble()
                println("Gostaria de adicionar uma descrição para o saque?")
                println("1. Sim\n2. Não")
                if (scanner.nextInt() == 1) {
                    scanner.nextLine()
                    print("Digite a descrição: ")
                    description = scanner.nextLine()
                }
                bankSystem.withdraw(accountNumber, amount, description)
            }
            4 -> {
                var description = ""
                print("Digite o número da conta de origem: ")
                val fromAccount = scanner.nextLong()
                print("Digite o número da conta de destino: ")
                val toAccount = scanner.nextLong()
                print("Digite o valor da transferência: ")
                val amount = scanner.nextDouble()
                println("Gostaria de adicionar uma descrição para a transferência?")
                println("1. Sim\n2. Não")
                if (scanner.nextInt() == 1) {
                    scanner.nextLine()
                    print("Digite a descrição: ")
                    description = scanner.nextLine()
                }
                bankSystem.transfer(fromAccount, toAccount, amount, description)
            }
            5 -> {
                print("Digite o número da conta: ")
                val accountNumber = scanner.nextLong()
                bankSystem.printAccountStatement(accountNumber)
            }
            6 -> bankSystem.printBankSummary()
            7 -> {
                println("Encerrando o sistema. Até logo!")
                bankSystem.closeBank()
                return
            }
            else -> println("Opção inválida.")
        }
    }
}