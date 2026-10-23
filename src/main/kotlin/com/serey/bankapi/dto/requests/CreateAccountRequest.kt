package com.serey.bankapi.dto.requests

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class CreateAccountRequest(

    @field:NotBlank(message = "Account name is required")
    @field:Size(
        min = 2,
        max = 50,
        message = "Account name must be between 2 and 50 characters")
    val accountName: String? = null,

    @field:DecimalMin(
        value = "0.0",
        message = "Balance cannot be negative"
    )
    val balance: Double? = null,

    @field:NotBlank(message = "Account email is required")
    @field:Email(message = "Email format is invalid")
    val email: String? = null
)