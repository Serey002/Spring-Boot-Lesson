package com.serey.bankapi.dto.requests

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class WithdrawRequest(
    @field:NotNull(message = "Withdraw is required")
    @field:NotBlank(message = "Please enter your amount")
    @field:DecimalMin(
        value = "0.0",
        inclusive = false,
        message = "Withdraw must be greater than 0.0"
    )
    @field:DecimalMax(
        value = "100000.0",
        inclusive = false,
        message = "Withdraw must be less than 100000.0"
    )
    val amount: Double? = null,
)
