package com.serey.bankapi.repositories

import com.serey.bankapi.models.User
import org.springframework.stereotype.Repository

//@Repository is normally used for the data-access layer.
//This class is responsible for accessing data.
@Repository
class Repository {
    fun findUserByUserId(userId: Number): User? {
        println("Finding user: $userId")
        return null
    }
}