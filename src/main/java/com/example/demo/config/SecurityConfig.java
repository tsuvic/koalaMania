package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authorize -> authorize
						.antMatchers("/").permitAll()
						.antMatchers("/insert").authenticated()
						.antMatchers("/edit/*").authenticated()
						.antMatchers("/post/postInsert").authenticated()
						.antMatchers("/delete/*").authenticated()
						.antMatchers("/api/users/posts").authenticated()
				).exceptionHandling(e -> e
						.authenticationEntryPoint(authenticationEntryPoint())
				).csrf(csrf -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				).formLogin(login -> login
						.loginProcessingUrl("/oauth/twitter/access")
						.loginPage("/login")
				).logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.deleteCookies("JSESSIONID").invalidateHttpSession(true)
						.deleteCookies("autoLogin")
				);
	}

	/* https://qiita.com/rubytomato@github/items/6c6318c948398fa62275#authenticationentrypoint */
	AuthenticationEntryPoint authenticationEntryPoint() {
		return new SimpleAuthenticationEntryPoint();
	}

}