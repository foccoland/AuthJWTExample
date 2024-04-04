package it.fabiogiannelli.authjwtcourse.model

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    val name: String,

)