package com.gajyoung.scentaur

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/greet")
    fun index(): String {
        return "Greetings from Spring Boot!"
    }
}
