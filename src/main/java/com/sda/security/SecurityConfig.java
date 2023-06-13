package com.sda.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)
                    ))
                .authorizeHttpRequests(auth ->
                    auth.requestMatchers("/**", "/auth/**", "/quiz/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                    )
                .build();

//        http.csrf().disable()
//                .authorizeHttpRequests((authorize) ->
//                        //authorize.anyRequest().authenticated()
//                        authorize.requestMatchers("/auth/**").permitAll()
//                                .requestMatchers("/security").authenticated()
//                );
//        return http.build();

    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //    @Bean
//    public UserDetailsService userDetailsService(){
////        UserDetails admin = User.withUsername("admin")
////                .password(encoder.encode("123")).roles("ADMIN").build();
////        UserDetails user = User.withUsername("user")
////                .password(encoder.encode("123")).roles("USER").build();
////        return new InMemoryUserDetailsManager(admin,user);
//        return new AuthorUserDetailsService();
//    }
}
