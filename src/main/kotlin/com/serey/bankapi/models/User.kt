package com.serey.bankapi.models

data class User (
    val userId: String? = null,
    val userName: String? = null,
    var balance: Double? = null,
)