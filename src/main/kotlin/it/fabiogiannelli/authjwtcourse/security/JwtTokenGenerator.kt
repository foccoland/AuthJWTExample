package it.fabiogiannelli.authjwtcourse.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import it.fabiogiannelli.authjwtcourse.security.SecurityConstants.Companion.JWTExpiration
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenGenerator {

    fun generateToken(authentication: Authentication): String {
        val username = authentication.name
        val currentDate = Date()
        val expireDate = Date(currentDate.time + JWTExpiration)

        val token = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(expireDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
        println("New token :")
        println(token)
        return token
    }

    fun getUsernameFromJWT(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        return claims.subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            throw AuthenticationCredentialsNotFoundException("JWT expired or incorrect.")
        }
    }

    companion object {
        private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS512)
    }

}