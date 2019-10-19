package org.signcollector.server

import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
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
    fun index(): HttpEntity<String> {
        return ResponseEntity.status(200).body("")
    }
}
