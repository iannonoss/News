package com.example.news.config;
import com.example.news.security.AuthService;
import com.example.news.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter(){
        return new JwtRequestFilter();
    }

    public WebSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                .csrf().disable().authorizeRequests()
                .antMatchers("/login", "/register-user", "/refresh-token", "/register-author", "/register-mod").permitAll()
                 .antMatchers(HttpMethod.GET,"/profile").access("hasRole('ROLE_USER')")
                 .antMatchers(HttpMethod.PUT,"/profile").access("hasRole('ROLE_USER')")
                 .antMatchers("/deactivate").access("hasRole('ROLE_USER')")
                 .antMatchers(HttpMethod.POST,"/news").access("hasRole('ROLE_AUTHOR')")
                 .antMatchers(HttpMethod.PATCH,"/news/update/{id}/{title}").access("hasRole('ROLE_AUTHOR')")
                 .antMatchers(HttpMethod.PUT,"/news/updateAll/{id}").access("hasRole('ROLE_AUTHOR')")
                 .antMatchers(HttpMethod.DELETE,"/news/delete").access("hasRole('ROLE_AUTHOR')")
                 .antMatchers("/news/category").access("hasRole('ROLE_USER')")
                 .antMatchers("admin/list_of_news").access("hasRole('ROLE_MODERATOR')")
                 .anyRequest().authenticated()
                 .and().
                 sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                 http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
                 http.httpBasic();
                return  http.build();
    }

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager userDetailsService() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




}


