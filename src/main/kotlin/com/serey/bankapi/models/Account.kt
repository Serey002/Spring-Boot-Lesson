package com.serey.bankapi.models

import com.serey.bankapi.enums.AccountStatus

data class Account(
    val id: Long,
    val accountNumber: String,
    val accountName: String,
    val balance: Double,
    val status: AccountStatus = AccountStatus.ACTIVE,
    val email: String,
)

