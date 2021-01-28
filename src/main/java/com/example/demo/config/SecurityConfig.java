package com.example.demo.config;

import com.example.demo.common.HxbAuthProvider;
import com.example.demo.handler.EntryPointHander;
import com.example.demo.handler.LoginFailureHandler;
import com.example.demo.handler.LoginSuccessHandler;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthService authService;
    @Autowired
    private HxbAuthProvider hxbAuthProvider;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private EntryPointHander entryPointHander;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(hxbAuthProvider)
         //认证通过后是否抹掉密码，默认为true
//           .eraseCredentials(false)
//           .userDetailsService(authService)
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .formLogin()
//                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
//                .defaultSuccessUrl("/hello")
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPointHander)
            .and()
                .authorizeRequests()
                .antMatchers("/login")
                .permitAll()
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("login.html");
        super.configure(web);
    }
}
