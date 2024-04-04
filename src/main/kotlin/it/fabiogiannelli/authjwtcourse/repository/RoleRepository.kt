package it.fabiogiannelli.authjwtcourse.repository

import it.fabiogiannelli.authjwtcourse.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Int> {
    fun findByName(name: String): Role?
}