package it.fabiogiannelli.authjwtcourse.repository

import it.fabiogiannelli.authjwtcourse.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Int> {

    fun findByUsername(username: String): UserEntity?
    fun existsByUsername(username: String): Boolean
}