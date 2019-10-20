package org.signcollector.server

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {

	override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
		response.writer.append("{ \"status\": \"ok\" }")
		response.addHeader("content-type", "application/json")
	}
}