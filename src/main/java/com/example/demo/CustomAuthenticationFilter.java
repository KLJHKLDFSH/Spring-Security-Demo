package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authRequest = null;

		if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
			ObjectMapper mapper = new ObjectMapper();
			try (InputStream is = request.getInputStream()) {
				Map<String,String> authenticationBean = mapper.readValue(is, Map.class);
				authRequest = new UsernamePasswordAuthenticationToken(
								authenticationBean.get("username"), authenticationBean.get("password"));
				setDetails(request, authRequest);
				return this.getAuthenticationManager().authenticate(authRequest);
			} catch (IOException e) {
				e.printStackTrace();
				authRequest = new UsernamePasswordAuthenticationToken("", "");
			}
		}else {
			return super.attemptAuthentication(request, response);
		}
		return authRequest;
	}
}
