package com.serey.bankapi.exception

class InsufficientBalanceException(
    message: String = "Insufficient balance"
): RuntimeException(message) {}