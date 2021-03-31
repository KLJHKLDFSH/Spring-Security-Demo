package com.example.demo.common;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, Map> {

	@Override
	public Map buildDetails(HttpServletRequest context) {
		Map map = new HashMap();
		map.put("name",context.getHeader("name"));
		return map;
	}
}