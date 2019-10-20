package org.signcollector.server

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogoutSuccessHandler : HttpStatusReturningLogoutSuccessHandler() {
	override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
		response.writer.append("{ \"status\": \"ok\" }")
		response.addHeader("content-type", "application/json")
		super.onLogoutSuccess(request, response, authentication)
	}
}