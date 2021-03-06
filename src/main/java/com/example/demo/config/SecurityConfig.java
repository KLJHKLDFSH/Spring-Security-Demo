package com.example.demo.config;

import com.example.demo.CustomAuthenticationFilter;
import com.example.demo.common.HxbAuthProvider;
import com.example.demo.handler.EntryPointHander;
import com.example.demo.handler.LoginFailureHandler;
import com.example.demo.handler.LoginSuccessHandler;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, Map> myAuthenticationDetailsSource;
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
            .addFilterAt(getCustomAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .csrf().disable()
            .formLogin()
                .authenticationDetailsSource(myAuthenticationDetailsSource)
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

    CustomAuthenticationFilter getCustomAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }
}
