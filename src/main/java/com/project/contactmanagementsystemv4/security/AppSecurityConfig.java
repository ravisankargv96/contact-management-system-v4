package com.project.contactmanagementsystemv4.security;

import com.project.contactmanagementsystemv4.security.jwt.*;
import com.project.contactmanagementsystemv4.security.authtokens.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{

    private final AuthTokenService authTokenService;

    public AppSecurityConfig(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable().cors().disable();

        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                //.antMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                //.antMatchers(HttpMethod.POST, "/api/v1/contacts/**").permitAll()
                //.antMatchers("/**").permitAll() //got access to all the endpoints
                //.antMatchers("/cms/api/v1/contacts/**").permitAll()
                .antMatchers("/cms/api/v1/users/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(new JWTAuthenticationFilter(), AnonymousAuthenticationFilter.class);
        //http.addFilterBefore(new AuthTokenAuthenticationFilter(authTokenService), AnonymousAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }
}
