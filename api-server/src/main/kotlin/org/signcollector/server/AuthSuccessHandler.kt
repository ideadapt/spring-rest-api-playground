package org.signcollector.server

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {
	var requestCache = HttpSessionRequestCache()

	override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
		val savedRequest: SavedRequest? = requestCache.getRequest(request, response)

		response.writer.append("{ \"status\": \"ok\" }")

		if (savedRequest == null) {
			clearAuthenticationAttributes(request)
			return
		}
		val targetUrlParameter = getTargetUrlParameter()
		if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response)
			clearAuthenticationAttributes(request)
			return
		}

		clearAuthenticationAttributes(request)
	}
}