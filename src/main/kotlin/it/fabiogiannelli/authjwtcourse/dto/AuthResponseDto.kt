package it.fabiogiannelli.authjwtcourse.dto

data class AuthResponseDto(
    val accessToken: String,
    val tokenType: String = "Bearer "
)