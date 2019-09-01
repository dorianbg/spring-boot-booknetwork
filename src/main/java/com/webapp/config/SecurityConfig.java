package com.webapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select email as principal, password as credentails, true from users where email=?")  // table behind user entity
		.authoritiesByUsernameQuery("select user_email as principal, role_name as role from user_roles where user_email=?")  // this table exists from many-to-many relationship
		.passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");  // done with influence from this video: https://www.youtube.com/watch?v=RWJ_8lu-Rks&t=722s
	}
   
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/register", "/", "/about", "/login", "/error", "/css/**", "/webjars/**").permitAll()
				.antMatchers( "/book", "/author").hasAnyRole("USER,ADMIN")
				.antMatchers("/users").hasRole("ADMIN")
				.and().formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/").and().logout().logoutSuccessUrl("/login");
	}

}
