package com.serey.bankapi.exception

class DuplicateEmailException(
    message: String = "Email already exists"
): RuntimeException(message)