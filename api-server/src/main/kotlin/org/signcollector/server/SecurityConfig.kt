package org.signcollector.server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.util.CollectionUtils
import org.springframework.web.client.RestTemplate
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*
import javax.sql.DataSource


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

	var authEntryPoint = HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
	var logoutSuccessHandler = LogoutSuccessHandler()
	val failureHandler = SimpleUrlAuthenticationFailureHandler()

	//see https://github.com/eugenp/tutorials/blob/master/spring-security-rest/src/main/java/org/baeldung/security/SecurityJavaConfig.java
	@Autowired
	lateinit var loginSuccessHandler: LoginSuccessHandler

	@Autowired
    lateinit var dataSource: DataSource

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
                .formLogin().successHandler(loginSuccessHandler).failureHandler(failureHandler)
                .and()
				.logout().logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .csrf().disable()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
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

	@Bean
	fun restTemplate(): RestTemplate {
		val restTemplate = RestTemplate()

		var interceptors: MutableList<ClientHttpRequestInterceptor> = restTemplate.interceptors
		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = ArrayList()
		}
		interceptors.add(RestTemplateHeaderModifierInterceptor())
		restTemplate.interceptors = interceptors
		return restTemplate
	}

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.jdbcAuthentication()
                .passwordEncoder(passwordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select name,password,enabled from users where name=?")
                .authoritiesByUsernameQuery("select name, role from user_roles where name=?")
    }
}
