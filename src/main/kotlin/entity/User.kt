package org.eng.soft.two.entity

data class User (
    val name: String,
    val document: String,
    val email: String,
    val account: Account
)