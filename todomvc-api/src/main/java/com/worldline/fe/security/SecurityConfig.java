/*
 * Copyright 2016 (C) Worldline - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.worldline.fe.security;

import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * Security configuration.
 *
 * @author Nicolas Carlier <nicolas.carlier@worldline.com>
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  /**
   * Registers the KeycloakAuthenticationProvider with the authentication
   * manager.
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(keycloakAuthenticationProvider());
  }

  /**
   * Defines the session authentication strategy.
   *
   * @return the session authentication strategy
   */
  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .logout().logoutSuccessUrl("/bye");
  }

}
