package com.wildcodeschool.spring.security.security_configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.wildcodeschool.spring.security.persistence.enums.RoleEnum;
import com.wildcodeschool.spring.security.utils.BCryptManagerUtil;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final String adminRole = RoleEnum.ADMINISTRATOR.name();

	private final UserDetailsService userDetailsService;

	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}


	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(BCryptManagerUtil.passwordencoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/auth**").authenticated()
			.antMatchers("/auth/admin**").hasAuthority(adminRole)
			.anyRequest().permitAll()
		.and()
			.exceptionHandling().accessDeniedPage("/errorAccessUnAuthorized")
		.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/auth").failureUrl("/error")
				.usernameParameter("username").passwordParameter("password")
				.and()
				.logout().invalidateHttpSession(true).logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.and()
				.csrf()
				.and()
				.sessionManagement().maximumSessions(1)
				.expiredUrl("/login");
	}
}
