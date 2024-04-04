package it.fabiogiannelli.authjwtcourse.controller

import it.fabiogiannelli.authjwtcourse.dto.RegisterDto
import it.fabiogiannelli.authjwtcourse.model.UserEntity
import it.fabiogiannelli.authjwtcourse.repository.RoleRepository
import it.fabiogiannelli.authjwtcourse.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController @Autowired constructor(
    val authenticationManager: AuthenticationManager,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDto): ResponseEntity<String> {
        if (userRepository.existsByUsername(registerDto.username)) {
            return ResponseEntity("Username is taken!", HttpStatus.BAD_REQUEST)
        }
        val user = UserEntity(
            username = registerDto.username,
            password = passwordEncoder.encode(registerDto.password)
        )
        val role = roleRepository.findByName("USER")

        if (role?.name == null) {
            return ResponseEntity("Role USER does not exists", HttpStatus.BAD_REQUEST)
        }
        user.roles.add(role)
        userRepository.save(user)
        return ResponseEntity("User registered success!", HttpStatus.OK)
    }
}