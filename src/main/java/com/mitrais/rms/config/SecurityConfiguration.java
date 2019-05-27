package com.mitrais.rms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticateProvider authenProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests()
				.antMatchers("/","/index", "/login", "/register", "/js/**", "/css/**", "/fonts/**", "/images/**","/jquery/**",
				"/bootstrap/**").permitAll().anyRequest().authenticated().and().httpBasic();

		http.authorizeRequests().and().formLogin().loginPage("/login").loginProcessingUrl("/login")
				.defaultSuccessUrl("/loginSuccess").failureUrl("/login?error=true").usernameParameter("username")
				.passwordParameter("password")
				.and().logout().logoutUrl("/logout");

	}

}
