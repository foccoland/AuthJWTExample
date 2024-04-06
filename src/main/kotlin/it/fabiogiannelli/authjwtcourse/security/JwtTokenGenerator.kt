package it.fabiogiannelli.authjwtcourse.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import it.fabiogiannelli.authjwtcourse.security.SecurityConstants.Companion.JWTExpiration
import it.fabiogiannelli.authjwtcourse.security.SecurityConstants.Companion.JWT_SECRET
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenGenerator {

    val signingKey: SecretKey
        get() {
            val stringInByte = JWT_SECRET.toByteArray(Charsets.UTF_8)
            return Keys.hmacShaKeyFor(stringInByte)
        }

    fun generateToken(authentication: Authentication): String {
        val username = authentication.name
        val currentDate = Date()
        val expireDate = Date(currentDate.time + JWTExpiration)

        val token = Jwts.builder()
            .subject(username)
            .issuedAt(currentDate)
            .expiration(expireDate)
            .signWith(signingKey)
            .compact()

        return token
    }

    fun getUsernameFromJWT(token: String): String {
        val claims = Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
        return claims.subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token)
            return true
        } catch (e: Exception) {
            throw AuthenticationCredentialsNotFoundException("JWT expired or incorrect.")
        }
    }

}