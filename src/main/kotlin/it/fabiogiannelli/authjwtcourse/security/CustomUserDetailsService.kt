package it.fabiogiannelli.authjwtcourse.security

import it.fabiogiannelli.authjwtcourse.model.Role
import it.fabiogiannelli.authjwtcourse.model.UserEntity
import it.fabiogiannelli.authjwtcourse.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.function.Function
import java.util.stream.Collectors

@Service
class CustomUserDetailsService @Autowired constructor(val userRepository: UserRepository): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        username?.let {
            try {
                val user = userRepository.findByUsername(it)
                user?.let { u ->
                    return User(
                        u.username,
                        u.password,
                        u.roles.map { role ->
                            SimpleGrantedAuthority(role.name)
                        })
                }
            } catch (e: UsernameNotFoundException) {
                throw UsernameNotFoundException("Username not found!")
            }
        } ?: kotlin.run {
            throw UsernameNotFoundException("Username not found!")
        }
    }
}