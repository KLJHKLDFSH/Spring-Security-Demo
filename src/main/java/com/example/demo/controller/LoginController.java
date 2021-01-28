package com.example.demo.controller;

import com.example.demo.model.LoginUser;
import com.example.demo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthService authService;

    @GetMapping("/loginPage")
    public String loginPage(){
       return "login.html";
    }


    @PostMapping("/loginForm")
    @ResponseBody
    public String loginForm(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("userName:"+username + "| password:"+password );
        UserDetails userDetails = authService.loadUserByUsername(username);
        if(!password.equals(userDetails.getPassword())){
            return "wrong password,check and retry please!";
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return "login seuccess: \n"+SecurityContextHolder.getContext().getAuthentication().toString();
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        LoginUser userDetails = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  "hello :"+userDetails.getName();
    }
}
