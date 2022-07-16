package com.gajyoung.scentaur

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerIT(@Autowired private val template: TestRestTemplate) {

    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun getHello() {
        val response = template.getForEntity("/greet", String::class.java)
        assertThat(response.body).isEqualTo("Greetings from Spring Boot!")
    }
}
