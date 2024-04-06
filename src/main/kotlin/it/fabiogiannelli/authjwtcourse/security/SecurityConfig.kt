package it.fabiogiannelli.authjwtcourse.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(val customUserDetailService: CustomUserDetailService) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests { request ->
                    request
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
            }
            .httpBasic(withDefaults())
            .build()
    }

//    Have to comment cause at the moment
//    the current UserDetailService is CustomUserDetailService class
//    
//    @Bean
//    @Throws(Exception::class)
//    fun users(): UserDetailsService {
//        val admin = User.builder()
//            .username("admin")
//            .password("admin")
//            .roles("ADMIN")
//            .build()
//        val fabio = User.builder()
//            .username("fabio")
//            .password("6715")
//            .roles("USER")
//            .build()
//
//        return InMemoryUserDetailsManager(admin, fabio)
//    }

    // Here comes the authManager after creating CustomuserDetailService
    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    // this instead encodes the password
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}