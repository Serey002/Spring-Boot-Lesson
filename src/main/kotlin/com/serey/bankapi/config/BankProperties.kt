package com.serey.bankapi.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "bank")
data class BankProperties(
    val name: String,
    val country: String,
    val currency: String,
    val maxDeposit: Double,
    val minDeposit: Double,
    val maxWithdraw: Double,
    val minWithdraw: Double,
)
