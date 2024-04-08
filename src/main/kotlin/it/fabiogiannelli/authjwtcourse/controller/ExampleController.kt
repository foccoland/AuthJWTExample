package it.fabiogiannelli.authjwtcourse.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController {

    @GetMapping("/example")
    fun example(): ResponseEntity<String> {
        return ResponseEntity("Hello, protected!", HttpStatus.OK)
    }
}