package com.serey.bankapi.utils

import org.springframework.stereotype.Service

@Service
class AccountNumberGenerator {
    fun generate(number: String): String {
        return number.padStart(6, '0')
    }
}