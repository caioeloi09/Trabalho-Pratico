package org.eng.soft.two.entity

data class Account (
    val id: Long,
    var balance: Double,
    val transactions: MutableList<Transaction> = mutableListOf(),
    val password: String
){
    constructor(id: Long, balance: Double, password: String): this(
        id = id,
        balance = balance,
        transactions = mutableListOf(),
        password = password
    )
}