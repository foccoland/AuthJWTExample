package it.fabiogiannelli.authjwtcourse

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/")
    fun home(): String {
        return "Welcome!"
    }

    @GetMapping("/secured")
    fun secured(): String {
        return "Welcome, but secured!"
    }
}