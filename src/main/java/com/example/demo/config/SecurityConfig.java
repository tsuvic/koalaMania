package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Configuration
	public static class AppSecurityConfigureAdapter extends WebSecurityConfigurerAdapter {

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**");
		}

		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/insert").authenticated()
					.antMatchers("/edit/*").authenticated()
					.antMatchers("/post/postInsert").authenticated()
					.antMatchers("/delete/*").authenticated()
					.antMatchers("/api/users/checkAuthenticated").authenticated()

			.and()
					.exceptionHandling()

			.and()
				.formLogin()
				.loginProcessingUrl("/oauth/twitter/access")
				.loginPage("http://127.0.0.1:3002/auth")

			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID").invalidateHttpSession(true)
			.deleteCookies("autoLogin");
		}

	}
}