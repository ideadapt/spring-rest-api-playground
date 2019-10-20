package org.signcollector.server

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import javax.validation.Valid

@RestController
@CrossOrigin
class UserController(private val users: UserRepository) {

    @Autowired
    lateinit var rest: RestTemplate

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

    @GetMapping("legacy/client-info")
    fun legacy(): ResponseEntity<String> {
        val headers = HttpHeaders()
        // TODO use credentials from spring session
        headers.setBasicAuth("test", "test")
        val request = HttpEntity(null, headers)
        val url = "http://localhost:3000/server/admin/app.api.php?request_action=get_Client"
        val response = rest.exchange(url, HttpMethod.GET, request, String::class.java)

        //no good, not possible to inject headers
        //val response = rest.getForEntity(url, String::class.java)
        return response
    }

    @GetMapping("/session")
    fun session(session: Authentication?): JsonNode {
        if(session == null){
            return JsonNodeFactory.instance.objectNode()
        }
        return ObjectMapper().readTree(ObjectMapper().writeValueAsString(session))
    }
}
