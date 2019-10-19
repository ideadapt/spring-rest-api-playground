package org.signcollector.server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.sql.DataSource


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var dataSource: DataSource

	var authEntryPoint: HttpStatusEntryPoint = HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)

	//see https://github.com/eugenp/tutorials/blob/master/spring-security-rest/src/main/java/org/baeldung/security/SecurityJavaConfig.java
	@Autowired
	lateinit var successHandler: AuthSuccessHandler

	val failureHandler = SimpleUrlAuthenticationFailureHandler()

	@Throws(Exception::class)
    override fun configure(security: HttpSecurity) {
        security.cors()
				.and()
				.authorizeRequests()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
				.and()
				.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/users").hasRole("USER")
                    .antMatchers("/").permitAll()
				.and()
                .httpBasic().disable()
                .formLogin().successHandler(successHandler).failureHandler(failureHandler)
                .and()
				.logout()
                .and()
                .csrf().disable()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.jdbcAuthentication()
                .passwordEncoder(passwordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select name,password,enabled from users where name=?")
                .authoritiesByUsernameQuery("select name, role from user_roles where name=?")
    }
}
