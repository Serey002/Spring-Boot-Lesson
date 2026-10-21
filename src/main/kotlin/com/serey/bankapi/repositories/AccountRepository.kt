package com.serey.bankapi.repositories

import com.serey.bankapi.enums.AccountStatus
import com.serey.bankapi.models.Account
import org.springframework.stereotype.Repository

@Repository
class AccountRepository {
    private val accounts = mutableListOf<Account>(
        Account(
            id = 1,
            accountNumber = "000001",
            accountName = "Serey Phem",
            balance = 1500.50,
            status = AccountStatus.ACTIVE
        ),
        Account(
            id = 2,
            accountNumber = "000002",
            accountName = "Jane Smith",
            balance = 3420.00,
            status = AccountStatus.ACTIVE
        ),
        Account(
            id = 3,
            accountNumber = "000003",
            accountName = "Acme Corp Ltd.",
            balance = 125000.75,
            status = AccountStatus.ACTIVE
        ),
        Account(
            id = 4,
            accountNumber = "000004",
            accountName = "Bob Johnson",
            balance = 45.20,
            status = AccountStatus.BLOCKED
        ),
        Account(
            id = 5,
            accountNumber = "000005",
            accountName = "Alice Brown",
            balance = 890.00,
            status = AccountStatus.CLOSED
        ),
        Account(
            id = 6,
            accountNumber = "000006",
            accountName = "Charlie Green",
            balance = 5430.10,
            status = AccountStatus.ACTIVE
        ),
        Account(
            id = 7,
            accountNumber = "000007",
            accountName = "Global Tech Inc.",
            balance = 67800.00,
            status = AccountStatus.ACTIVE
        ),
        Account(
            id = 8,
            accountNumber = "000008",
            accountName = "Diana Prince",
            balance = 0.00,
            status = AccountStatus.BLOCKED
        ),
        Account(
            id = 9,
            accountNumber = "000009",
            accountName = "Evan Wright",
            balance = 1240.55,
            status = AccountStatus.CLOSED
        ),
        Account(
            id = 10,
            accountNumber = "000010",
            accountName = "Fiona Gallagher",
            balance = 9820.40,
            status = AccountStatus.ACTIVE
        )
    )

    fun findAll(): List<Account> {
        return accounts
    }

    fun findByAccountId(id: Long): Account? {
        return accounts.find { it.id == id }
    }

    fun save(account: Account): Account {
        val index = accounts.indexOfFirst { it.id == account.id }
        if(index == -1) {
            //create
            accounts.add(account)
        } else {
            //update
            accounts[index] = account
        }
        return account
    }


    fun delete(id: Long): Boolean {
       return accounts.removeIf { it.id == id }
    }
}