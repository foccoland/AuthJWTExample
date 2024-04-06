package it.fabiogiannelli.authjwtcourse.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(): OncePerRequestFilter() {

    lateinit var tokenGenerator: JwtTokenGenerator
    lateinit var customUserDetailService: CustomUserDetailService

    constructor(
        @Autowired tokenGenerator: JwtTokenGenerator,
        @Autowired customUserDetailService: CustomUserDetailService
    ) : this() {
        this.tokenGenerator = tokenGenerator
        this.customUserDetailService
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        getJWTFromRequest(request)?.let { token ->
            if (tokenGenerator.validateToken(token)) {
                val username = tokenGenerator.getUsernameFromJWT(token)
                val userDetails = customUserDetailService.loadUserByUsername(username)
                val authenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.authorities
                )
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun getJWTFromRequest(request: HttpServletRequest): String? {
        request.getHeader("Authorization")?.let { bearerToken ->
            if (bearerToken.isNotEmpty() && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7, bearerToken.length)
            }
        }
        return null
    }
}