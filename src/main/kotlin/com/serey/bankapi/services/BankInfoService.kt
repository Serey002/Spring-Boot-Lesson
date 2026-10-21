package com.serey.bankapi.services

import com.serey.bankapi.config.BankProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class BankInfoService (

    @Value("\${spring.application.name}")
    private val applicationName: String,
    @Value("\${spring.application.version}")
    private val version: String,

    //Read Configuration with @Value
    @Value("\${bank.name}")
    private val bankName: String,

    @Value("\${bank.country}")
    private val country: String,

    @Value("\${bank.currency}")
    private val currency: String,

    //Use BankProperties: group configuration
    private val bankProperties: BankProperties,
){

    init {
        println("Application Name: $applicationName")
        println("Spring Boot running with version $version")

        println(getBankInfo())

        println(getBankInformation())
    }

    fun getBankInfo(): String {
        return  "$bankName - $country - $currency with @Value"
    }

    fun getBankInformation(): String {
        return "${bankProperties.name} - " +
                "${bankProperties.country} - " +
                bankProperties.currency + " with @ConfigurationProperties"
    }
}