package com.serey.bankapi.controllers

import com.serey.bankapi.models.Hello
import com.serey.bankapi.services.HelloService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController(private val helloService: HelloService) {
    @GetMapping("/hello")
    fun getHello(): Hello {
        return helloService.hello()
    }

}