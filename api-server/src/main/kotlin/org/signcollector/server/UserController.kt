package org.signcollector.server

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@CrossOrigin
class UserController(private val users: UserRepository) {

    @GetMapping("/users")
    fun all() : List<User> {
       return users.findAll();
    }

    @PostMapping("/users")
    fun create(@Valid @RequestBody user: User) : User {
        return users.save(user)
    }

    @GetMapping("/")
    fun index(): JsonNode {
        return JsonNodeFactory.instance.nullNode()
    }

    @GetMapping("/session")
    fun session(session: Authentication?): JsonNode {
        if(session == null){
            return JsonNodeFactory.instance.objectNode()
        }
        return ObjectMapper().readTree(ObjectMapper().writeValueAsString(session))
    }
}
