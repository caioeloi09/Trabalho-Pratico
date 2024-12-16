package org.eng.soft.two.utils

import org.eng.soft.two.entity.Account
import org.eng.soft.two.service.SecurityService

class ReadingUtils {
    private val securityService: SecurityService = SecurityService()

    fun readPassword(account: Account): String {
        var wrongPassword = true
        var password = ""

        while (wrongPassword) {
            println("Digite a senha da conta:")
            password = readlnOrNull() ?: ""

            if (!securityService.assertPasswordCorrect(password, account)) {
                wrongPassword = false
            } else {
                println("Senha incorreta. Gostaria de voltar ou tentar novamente?\n1: Voltar\n2: Tentar novamente")

                val choice = try {
                    readlnOrNull()?.toInt() ?: 0
                } catch (e: NumberFormatException) {
                    0
                }

                if (choice == 1) {
                    wrongPassword = false
                    password = ""
                }
            }
        }
        return password
    }
}