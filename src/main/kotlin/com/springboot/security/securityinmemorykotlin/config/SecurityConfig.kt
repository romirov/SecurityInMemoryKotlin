package com.springboot.security.securityinmemorykotlin.config

import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@EnableWebSecurity
class SecurityConfig {
  companion object: KLogging()

  @Bean
  fun userDetailsService(): UserDetailsService {
    val userDetailsManager = InMemoryUserDetailsManager()
    val userDetails = User.withUsername("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("USER", "ADMIN")
    userDetailsManager.createUser(userDetails.build())
    return userDetailsManager
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Throws(Exception::class)
  fun configure(http: HttpSecurity) {
    // Set session management to stateless
    http.csrf().disable()
    http
      .authorizeRequests()
      .antMatchers("/index").hasRole("ADMIN")
      .anyRequest()
      .authenticated()
      .and()
      .formLogin()
      .and()
      .rememberMe()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .logout()
      .logoutUrl("/logout")
      .logoutSuccessUrl("/login")
      .deleteCookies("remember-me")
  }
}