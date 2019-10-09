package org.signcollector.server

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@CrossOrigin
class UserController(private val users: UserRepository) {

    @GetMapping("/")
    fun index() : HttpEntity<String> {
        return ResponseEntity.status(401).header("WWW-Authenticate", "Basic realm=\"no access dude\"").body("")
    }

    @GetMapping("/users")
    fun all() : List<User> {
       return users.findAll();
    }

    @PostMapping("/users")
    fun create(@Valid @RequestBody user: User) : User {
        return users.save(user)
    }

    @GetMapping("/logout")
    fun logout(): HttpEntity<String> {
        return ResponseEntity.status(303).header("Location", "/").build();
    }
}
