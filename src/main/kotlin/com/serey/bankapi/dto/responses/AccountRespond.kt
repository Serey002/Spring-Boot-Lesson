package com.serey.bankapi.dto.responses

data class AccountRespond<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)
