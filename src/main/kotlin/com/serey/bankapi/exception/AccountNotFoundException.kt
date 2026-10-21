package com.serey.bankapi.exception

class AccountNotFoundException(
    message: String = "Account not found"
): RuntimeException(message)