package it.fabiogiannelli.authjwtcourse.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val username: String,

    val password: String,

    @ManyToMany(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    @JoinTable(
        name = "user_roles",
        joinColumns = arrayOf(
            JoinColumn(
                name = "user_id",
                referencedColumnName = "id"
            )
        ),
        inverseJoinColumns = arrayOf(
            JoinColumn(
                name = "role_id",
                referencedColumnName = "id"
            )
        )
    )
    val roles: MutableList<Role> = mutableListOf()

)