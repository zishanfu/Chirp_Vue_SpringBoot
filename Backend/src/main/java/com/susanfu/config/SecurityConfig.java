package com.susanfu.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.susanfu.service.UserSecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserSecurityService userSecurityService;
	
	private static final String SALT = "salt"; //Salt should be protected carefully
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	private static final String[] PUBLIC_MATCHES = {
			"/css/**",
			"/js/**",
			"/images/**",
			"/message/**",
			"/signup",
			"/tokenValidation"
	};
	
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers(PUBLIC_MATCHES).permitAll().anyRequest().authenticated();
		http.csrf().disable().cors().disable().formLogin().defaultSuccessUrl("/").loginPage("/login")
				.failureHandler(new AuthFailureHandler()).permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.deleteCookies("remeber-me").permitAll().and().rememberMe();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
}
