package dev.mark.jewelsstorebackend.auth;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ProblemDetailsAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

	private final ObjectMapper mapper;

	public ProblemDetailsAuthenticationEntryPoint(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		this.delegate.commence(request, response, authException);
		if (authException.getCause() instanceof JwtValidationException validation) {
			ProblemDetail detail = ProblemDetail.forStatus(401);
			detail.setType(URI.create("https://tools.ietf.org/html/rfc6750#section-3.1"));
			detail.setTitle("Invalid Token");
			detail.setProperty("errors", validation.getErrors());
			this.mapper.writeValue(response.getWriter(), detail);
		}
	}
}