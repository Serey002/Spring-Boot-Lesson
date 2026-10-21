package com.serey.bankapi.exception

import com.serey.bankapi.dto.responses.AccountRespond
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException::class)
    fun handleAccountNotFoundException(
        exception: AccountNotFoundException
    ): ResponseEntity<AccountRespond<Nothing>> {
        val response = AccountRespond<Nothing>(
            success = false,
            message = exception.message ?: "Account not found",
            data = null
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrorException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<AccountRespond<Nothing>> {
        val message = exception.bindingResult
            .fieldErrors
            .joinToString("; ") {
                error -> "${error.field}: ${error.defaultMessage}"
            }
        val response = AccountRespond<Nothing>(
            success = false,
            message = message,
            data = null
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            response
        )
    }

    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleInsufficientBalanceException(
        exception: InsufficientBalanceException
    ): ResponseEntity<AccountRespond<Nothing>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            AccountRespond(
                success = false,
                message = exception.message ?: "Insufficient balance"
            )
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        exception: IllegalArgumentException
    ): ResponseEntity<AccountRespond<Nothing>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            AccountRespond(
                success = false,
                message = exception.message ?: "Invalid input"
            )
        )
    }
}