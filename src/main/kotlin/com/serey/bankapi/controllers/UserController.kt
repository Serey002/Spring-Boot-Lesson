package com.serey.bankapi.controllers

import com.serey.bankapi.models.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/users") // base URL
class UserController {
    private val users = mutableListOf(
        User("0001", "Serey", 100.0),
        User("0002", "Molika", 200.0),
        User("0003", "Ya", 300.0)

    )

//    @GetMapping("/api/users")
    @GetMapping
    fun getUsers(): List<User> {
        return users
    }

//    @RequestMapping("/{id}", method = [RequestMethod.GET])
    @GetMapping("/{id}")
    fun getUser(@PathVariable(name = "id") userId: String): ResponseEntity<User> {
        val findUser = users.find { it.userId == userId }
        if (findUser === null) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(findUser)
    }

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        users.add(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: String, @RequestBody updateUser: User): ResponseEntity<User> {
        val index = users.indexOfFirst { it.userId == id }
        if (index == -1) {
            return ResponseEntity.notFound().build()
        }
        users[index] = updateUser
        return ResponseEntity.ok(updateUser)
    }

    @PatchMapping("/{id}")
    fun updateUserName(@PathVariable id: String, @RequestBody updateUser: User): ResponseEntity<User> {
        val index = users.indexOfFirst { it.userId == id }
        if (index == -1) {
            return ResponseEntity.notFound().build()
        }

        val currentUser = users[index]
        val updatedUser = currentUser.copy(
            userName = updateUser.userName ?: currentUser.userName,
            balance = updateUser.balance ?: currentUser.balance
        )

        users[index] = updatedUser
        return ResponseEntity.ok(updatedUser)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<MutableList<User>> {
        val index = users.indexOfFirst { it.userId == id }
        if (index == -1) {
            return ResponseEntity.notFound().build()
        }

        users.removeAt(index)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchUser(@RequestParam userName: String): List<User> {
        return users.filter { it.userName == userName }
    }

}