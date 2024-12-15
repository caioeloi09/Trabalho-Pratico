package org.eng.soft.two.vo

import org.eng.soft.two.enum.TransactionTypeEnum

data class TransactionVO (
    val amount: Double,
    val type: TransactionTypeEnum,
    val description: String
)
