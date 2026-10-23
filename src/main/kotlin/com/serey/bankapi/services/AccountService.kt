package com.serey.bankapi.services

import com.serey.bankapi.config.BankProperties
import com.serey.bankapi.dto.requests.UpdateAccountRequest
import com.serey.bankapi.dto.responses.AccountRespond
import com.serey.bankapi.enums.AccountStatus
import com.serey.bankapi.exception.AccountNotFoundException
import com.serey.bankapi.exception.DuplicateEmailException
import com.serey.bankapi.exception.InsufficientBalanceException
import com.serey.bankapi.models.Account
import com.serey.bankapi.repositories.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val bankInfo: BankProperties
    ) {

    fun getAccounts(): List<Account> {
        return accountRepository.findAll()
    }

    fun getAccountById(id: Long): AccountRespond<Account?> {
            val account = accountRepository.findByAccountId(id) ?: return throw AccountNotFoundException(
            "Account not found"
            )
        return AccountRespond(
            success = true,
            message = "Get account successful",
            data = account
        )
    }

    fun createAccount(account: Account): AccountRespond<Account> {
        if (accountRepository.existsByEmail(account.email)) {
            throw DuplicateEmailException(
                "Email ${account.email} already exists"
            )
        }
        accountRepository.save(account)
        return AccountRespond(
            success = true,
            message = "Account created successful",
            data = account
        )
    }

    fun updateAccount(id: Long, account: UpdateAccountRequest): AccountRespond<Account?> {
        val existingAccount = accountRepository.findByAccountId(id) ?: throw AccountNotFoundException(
            "Account not found"
        )
        val updatedAccount = existingAccount.copy(
            id = existingAccount.id,
            accountName = account.accountName?: existingAccount.accountName,
            balance = account.balance ?: existingAccount.balance,
            status = account.status ?: existingAccount.status,
            email = account.email?: existingAccount.email,
        )
        accountRepository.save(updatedAccount)
        return AccountRespond(
            success = true,
            message = "Account updated successful",
            data = updatedAccount
        )
    }

    fun deleteAccount(id: Long): AccountRespond<Account> {
        val account = accountRepository.findByAccountId(id) ?: throw AccountNotFoundException(
            "Account not found"
        )

        if (account.balance != 0.0) {
           throw IllegalArgumentException(
               "Cannot delete account with a non-zero balance",
        )}

        accountRepository.delete(id)
        return AccountRespond(
            success = true,
            message = "Deleted account id $id successfully",
            data = account
        )
    }


    fun deposit(id: Long, amount: Double): AccountRespond<Account> {
        val existAccount = accountRepository.findByAccountId(id) ?: return throw AccountNotFoundException(
            "Account not found"
        )

       return when {
            existAccount.status != AccountStatus.ACTIVE -> AccountRespond(
                success = false,
                message = "Account must be ACTIVE"
            )

//            amount <= bankInfo.minDeposit -> throw IllegalArgumentException(
//                "Amount must be great than ${bankInfo.minDeposit} ${bankInfo.currency}"
//            )
//
//            amount > bankInfo.maxDeposit -> throw IllegalArgumentException(
//                "Account cannot deposit over ${bankInfo.maxDeposit} ${bankInfo.currency}"
//            )

            else -> { val updateAccount = accountRepository.save(existAccount.copy(
                balance = existAccount.balance + amount
            ))
                AccountRespond(
                    success = true,
                    message = "Deposit successful",
                    data = updateAccount,
                )
            }
        }
    }

    fun withdraw(id: Long, amount: Double): AccountRespond<Account> {
        val existAccount = accountRepository.findByAccountId(id) ?: throw AccountNotFoundException(
            "Account not found"
        )
        return when {
            existAccount.status != AccountStatus.ACTIVE -> AccountRespond(
                success = false,
                message = "Account must be ACTIVE",
            )

            existAccount.balance < amount -> throw InsufficientBalanceException(
                "Insufficient balance"
            )

            amount <= bankInfo.minWithdraw -> throw IllegalArgumentException(
                "Amount must be great than ${bankInfo.minWithdraw} ${bankInfo.currency}",
            )

            else -> { val updateAccount = accountRepository.save(existAccount.copy(
                balance = existAccount.balance - amount
            ))
                AccountRespond(
                    success = true,
                    message = "Account withdraw successfully",
                    data = updateAccount
                )
            }
        }
    }
}