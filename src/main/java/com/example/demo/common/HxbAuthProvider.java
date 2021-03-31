package com.example.demo.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HxbAuthProvider implements AuthenticationProvider {

	@Autowired
	public RestTemplate restTemplate;


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = (String) authentication.getPrincipal();

		String password = (String)authentication.getCredentials();
		
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("username",userName);
		jsonObject.addProperty("password",password);

//		ResponseEntity<JsonObject> response =
//				restTemplate.postForEntity("/Other/login",gson.toJson(jsonObject),JsonObject.class);
		UserDetails user = UserList.getUserByUserName(userName);
		if (user == null){
			throw new UsernameNotFoundException("username:"+ userName +"not found");
		}
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(userName, password, user.getAuthorities());
		return usernamePasswordAuthenticationToken;
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
