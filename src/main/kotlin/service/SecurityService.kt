package org.eng.soft.two.service

import org.eng.soft.two.entity.Account

class SecurityService {
    fun checkPassword(password: String): Boolean {
        if (isValidPasswordLength(password) && passwordContainsDigitsAndLetters(password)) return false
        println("Senha não é valida. Tente novamente!")
        return true
    }

    fun assertPasswordCorrect(password: String, account: Account): Boolean {
        return password != account.password
    }

    private fun isValidPasswordLength(password: String): Boolean {
        return password.length >= 5
    }

    private fun passwordContainsDigitsAndLetters(password: String): Boolean {
        return  password.any { it.isLetter() } && password.any { it.isDigit() }
    }
}