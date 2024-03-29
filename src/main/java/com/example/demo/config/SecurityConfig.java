package com.example.demo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
				.cors(cors -> cors
						.configurationSource(corsConfigurationSource())
				).authorizeRequests(authorize -> authorize
						.antMatchers("/").permitAll()
						.antMatchers("/insert").authenticated()
						.antMatchers("/edit/*").authenticated()
						.antMatchers("/post/postInsert/*").authenticated()
						.antMatchers("/delete/*").authenticated()
						.antMatchers("/api/users/posts").authenticated()
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

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:8080","http://localhost:8080","https://koalamania.herokuapp.com","http://localhost:3000","https://loquacious-pasca-67dd24.netlify.app"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/users/**", configuration);
		return source;
	}

}