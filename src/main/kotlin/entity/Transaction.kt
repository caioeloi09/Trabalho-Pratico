package org.eng.soft.two.entity

import org.eng.soft.two.enum.TransactionTypeEnum

data class Transaction(
    val id: Long,
    val accountId: Long,
    val type: TransactionTypeEnum,
    val amount: Double,
    val timestamp: String,
    val description: String
)
