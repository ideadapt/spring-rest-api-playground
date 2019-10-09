package org.signcollector.server

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name="users")
data class User(
        @Id
        @get: NotBlank
        val name: String = ""
)
