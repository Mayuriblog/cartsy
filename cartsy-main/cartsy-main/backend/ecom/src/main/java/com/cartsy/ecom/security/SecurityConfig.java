package com.cartsy.ecom.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(
	    securedEnabled = true,
	    jsr250Enabled = true,
	    prePostEnabled = true
	)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override 
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();

        // Set unauthorized requests exception handler
        http = http
            .exceptionHandling()
            .authenticationEntryPoint(
                (request, response, ex) -> {
                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        ex.getMessage()
                    );
                }
            )
            .and();

        // Set permissions on endpoints
        http.authorizeRequests()
            // Our public endpoints
        	.antMatchers("/api/v1/register").permitAll()
            .antMatchers("/api/v1/public/**").permitAll()
            .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/private/products").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/private/categories").permitAll()


            
            // Our private endpoints
            .antMatchers("/api/v1/private/admin/**").hasRole("CARTSY_ADMIN")
            .antMatchers("/api/v1/private/buyers/**").hasRole("CARTSY_BUYER")
            .antMatchers("/api/v1/private/address/**").hasRole("CARTSY_BUYER")
            .antMatchers("/api/v1/private/paymentinfo/**").hasRole("CARTSY_BUYER")
            .antMatchers("/api/v1/private/reviews/**").hasRole("CARTSY_BUYER")
            
            .antMatchers(HttpMethod.POST, "/api/v1/private/products").hasRole("CARTSY_SELLER")
            .antMatchers("/api/v1/private/sellers/**").hasRole("CARTSY_SELLER")
            .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(
            jwtTokenFilter,
            UsernamePasswordAuthenticationFilter.class
        );
    }

   
}
