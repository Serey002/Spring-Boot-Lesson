package com.serey.bankapi.controllers

import com.serey.bankapi.dto.responses.AccountRespond
import com.serey.bankapi.dto.requests.CreateAccountRequest
import com.serey.bankapi.dto.requests.DepositRequest
import com.serey.bankapi.dto.requests.UpdateAccountRequest
import com.serey.bankapi.dto.requests.WithdrawRequest
import com.serey.bankapi.dto.requests.searchAndFilterAccountRequest
import com.serey.bankapi.enums.AccountStatus
import com.serey.bankapi.exception.AccountNotFoundException
import com.serey.bankapi.models.Account
import com.serey.bankapi.services.AccountService
import com.serey.bankapi.utils.AccountNumberGenerator
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api")
class AccountController(
    private val accountService: AccountService,
    private val accountNumberGenerator: AccountNumberGenerator) {

    @GetMapping("/accounts")
    fun getAllAccounts(
        @ModelAttribute request: searchAndFilterAccountRequest
    ): ResponseEntity<AccountRespond<List<Account?>>> {
        val accounts = accountService.getAccounts().filter { account ->
            //search
            val matchAccountName = request.accountName.isNullOrBlank() ||
                                   account.accountName.contains(request.accountName, true)

            //filter
            val filterAccountStatus = request.accountStatus.isNullOrBlank() ||
                                      account.status.name.equals(request.accountStatus,  true)
            matchAccountName && filterAccountStatus
        }

        val message = if (accounts.isEmpty()) {
            throw AccountNotFoundException("Accounts not found")
        }else{
            "Account retrieved successfully"
        }

        return ResponseEntity.status(HttpStatus.OK).body(AccountRespond(
           success = true,
            message = message,
            data = accounts
        ))
    }

    @GetMapping("account/{id}")
    fun getAccountById(@PathVariable id: Long): ResponseEntity<AccountRespond<Account?>> {
        val account = accountService.getAccountById(id)
        return ResponseEntity.status(HttpStatus.OK).body(account)
    }

    @PostMapping("/account/create")
    fun createAccount(
        @Valid
        @RequestBody account: CreateAccountRequest): ResponseEntity<AccountRespond<Account>> {
        val newId = (accountService.getAccounts().maxOfOrNull { it.id } ?: 0) + 1
        val newAccountNumber = accountNumberGenerator.generate(newId.toString())
        val account = Account(
            id = newId,
            accountNumber = newAccountNumber,
            accountName = account.accountName ?: throw IllegalArgumentException("Account name is required"),
            balance = account.balance ?: 0.0,
            status = AccountStatus.ACTIVE,
            email = account.email ?: throw IllegalArgumentException("Email is required")
        )
        val newAccount = accountService.createAccount(account)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            newAccount
        )
    }

    @PutMapping("account/update/{id}")
    fun updateAccount( @PathVariable id: Long, @RequestBody account: UpdateAccountRequest): ResponseEntity<AccountRespond<Account?>> {
        val updateAccount = accountService.updateAccount(id, account)
        return ResponseEntity.status(HttpStatus.OK).body(
            updateAccount
        )
    }

    @DeleteMapping("/account/delete/{id}")
    fun deleteAccountById(@PathVariable id: Long): ResponseEntity<AccountRespond<Account>> {
        val deleteAccount = accountService.deleteAccount(id)
        return ResponseEntity.status(HttpStatus.OK).body(deleteAccount)
    }

    @PostMapping("/account/{id}/deposit")
    fun deposit(
        @PathVariable id: Long,
        @Valid
        @RequestBody depositRequest: DepositRequest):
        ResponseEntity<AccountRespond<Account>> {
        val updateAccountBalance = accountService.deposit(
            id = id,
            amount = depositRequest.amount ?: throw IllegalArgumentException("Deposit amount is required") )
        return  ResponseEntity.status(HttpStatus.OK).body(updateAccountBalance)
    }

    @PostMapping("/account/{id}/withdraw")
    fun withdraw(
        @PathVariable id: Long,
        @Valid
        @RequestBody withdrawRequest: WithdrawRequest):
        ResponseEntity<AccountRespond<Account>> {
        val updateAccountBalance = accountService.withdraw(
            id = id,
            amount = withdrawRequest.amount ?: throw IllegalArgumentException("Withdraw amount is required") )
        return ResponseEntity.status(HttpStatus.OK).body(updateAccountBalance)
    }
}