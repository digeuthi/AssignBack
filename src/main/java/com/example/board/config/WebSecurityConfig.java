package com.example.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.board.filter.JwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {


        httpSecurity
                .cors().and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests()
                .antMatchers("/board/list").permitAll()
                .antMatchers(HttpMethod.GET, "/board").permitAll()
                .antMatchers(HttpMethod.POST).hasRole("USER")
                .antMatchers(HttpMethod.PATCH).hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/board/{boardNumber}").hasRole("USER")
                .antMatchers("/board/list/blocked").hasRole("ADMIN")
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }
    // private Customizer<CorsConfigurer<HttpSecurity>> withDefaults() {
    //     return null;
    // }

}
