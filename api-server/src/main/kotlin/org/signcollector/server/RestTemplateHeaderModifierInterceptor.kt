package org.signcollector.server

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import java.io.IOException

class RestTemplateHeaderModifierInterceptor : ClientHttpRequestInterceptor {

	@Throws(IOException::class)
	override fun intercept(
			request: HttpRequest,
			body: ByteArray,
			execution: ClientHttpRequestExecution): ClientHttpResponse {

		val response = execution.execute(request, body)

		val headerNames = listOf("access-control", "vary", HttpHeaders.SERVER, HttpHeaders.SET_COOKIE).joinToString("|")
		response.headers.filter { it.key.toLowerCase().contains(Regex("/($headerNames)/ig")) }
				.forEach {
					response.headers.remove(it.key)
				}
		return response
	}
}