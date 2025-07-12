package com.aditya.springboot_kotlin.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
//            .authorizeHttpRequests { auth ->
//                auth
//                    .requestMatchers("/")
//                    .permitAll()
//                    .requestMatchers("/auth/**")
//                    .permitAll()
//                    .dispatcherTypeMatchers(
//                        DispatcherType.ERROR,
//                        DispatcherType.FORWARD
//                    )
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated()
//            }
//            .exceptionHandling { configurer ->
//                configurer
//                    .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//            }
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

    }
}