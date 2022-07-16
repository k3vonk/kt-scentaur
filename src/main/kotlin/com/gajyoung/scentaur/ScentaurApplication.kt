package com.gajyoung.scentaur

import com.gajyoung.scentaur.storage.IStorageService
import com.gajyoung.scentaur.storage.StorageProperties
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties(StorageProperties::class)
class ScentaurApplication {

    @Bean
    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
        return CommandLineRunner {
            println("Let's insepct the beans provided by Spring Boot:")

            ctx.beanDefinitionNames.sorted().forEach { println(it) }
        }
    }

    @Bean
    internal fun init(storageService: IStorageService) = CommandLineRunner {
        storageService.deleteAll()
        storageService.init()
    }
}

fun main(args: Array<String>) {
    runApplication<ScentaurApplication>(*args)
}
