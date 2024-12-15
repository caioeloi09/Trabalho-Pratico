package org.eng.soft.two.entity

data class Account (
    val id: Long,
    var balance: Double,
    val transactions: MutableList<Transaction> = mutableListOf()
)