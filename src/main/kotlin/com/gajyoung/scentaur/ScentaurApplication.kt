package com.gajyoung.scentaur

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ScentaurApplication {

	@Bean
	fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
		return CommandLineRunner {
			println("Let's insepct the beans provided by Spring Boot:")

			ctx.beanDefinitionNames.sorted().forEach { println(it) }
		}
	}
}

fun main(args: Array<String>) {
	runApplication<ScentaurApplication>(*args)
}
