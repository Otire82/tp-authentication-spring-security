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
// TODO : EnableWebSecurity and extends WebSecurityConfigurerAdapter
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
			.antMatchers( // TODO : Add pages require authentication
					).authenticated()
			.antMatchers( // TODO : Add pages require to be Admin
).hasAuthority(adminRole)
			.anyRequest().permitAll()
		.and()
			.exceptionHandling().accessDeniedPage( // TODO : Add page to error access denied
					)
		.and()
			.formLogin()
				.loginPage( // TODO : Add login page
						)
				.defaultSuccessUrl(// TODO : success auhentication page
						).failureUrl( // TODO : failure auhentication page
								)
				.usernameParameter( // TODO : username field
						
						).passwordParameter( // TODO : password field
								)
				.and()
				.logout().invalidateHttpSession(true).logoutUrl( // TODO : logout page
						)
				.logoutSuccessUrl( // TODO : the logout success page => ?
						)
				.and()
				.csrf()
				.and()
				.sessionManagement().maximumSessions(1)
				.expiredUrl( // TODO : Expired Url => ?
						
						);
	}
}
