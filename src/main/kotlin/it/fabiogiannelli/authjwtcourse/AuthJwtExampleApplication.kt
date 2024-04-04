package it.fabiogiannelli.authjwtcourse

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@SpringBootApplication
class AuthJwtExampleApplication

fun main(args: Array<String>) {
    runApplication<AuthJwtExampleApplication>(*args)
}

@Component
@Order(1)
class Runner: CommandLineRunner {
    override fun run(vararg args: String?) {
        println("Hello, running!")
    }
}