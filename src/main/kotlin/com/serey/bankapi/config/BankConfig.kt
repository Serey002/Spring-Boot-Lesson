package com.serey.bankapi.config

import com.serey.bankapi.utils.AccountNumberGenerator
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(BankProperties::class)
class BankConfig {

    @Bean
    fun accountNumberGenerator(): AccountNumberGenerator {
        println("Test")
        return AccountNumberGenerator()
    }
}