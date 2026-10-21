package com.serey.bankapi.controllers

import com.serey.bankapi.services.BankInfoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class BankController(private val bankInfoService: BankInfoService) {
    @GetMapping("/info")
    fun getBankInfo(): String{
        return bankInfoService.getBankInfo()
    }

    @GetMapping("/information")
    fun getBankInformation(): String {
        return bankInfoService.getBankInformation()
    }
}