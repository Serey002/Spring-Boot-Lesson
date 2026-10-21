package com.serey.bankapi.services

import com.serey.bankapi.models.Hello
import org.springframework.stereotype.Service

//@Service is used for the service layer.
// This class contains application/business logic and should be managed as a Bean.
@Service
class HelloService {
    fun hello(): Hello {
        return Hello("Hello Spring Boot")
    }
}