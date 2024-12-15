package org.eng.soft.two.entity

data class Account (
    val id: Int,
    val number: Long,
    val digit: Long,
    var balance: Double
)