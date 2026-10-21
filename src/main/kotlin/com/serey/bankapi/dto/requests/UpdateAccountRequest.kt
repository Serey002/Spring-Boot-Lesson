package com.serey.bankapi.dto.requests

import com.serey.bankapi.enums.AccountStatus

data class UpdateAccountRequest(
    val accountName: String?,
    val balance: Double?,
    val status: AccountStatus?
)
