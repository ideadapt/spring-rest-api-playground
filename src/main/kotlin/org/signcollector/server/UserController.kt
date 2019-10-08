package org.signcollector.server

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
}
