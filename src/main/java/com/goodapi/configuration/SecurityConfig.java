package com.goodapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.goodapi.repository.CustomerRepository;
import com.goodapi.security.JwtAuthenticationFilter;
import com.goodapi.security.RestAuthenticationEntryPoint;

/**
 * @author msaritas
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	private final CustomerRepository customerRepository;

	@Autowired
	public SecurityConfig(RestAuthenticationEntryPoint restAuthenticationEntryPoint,
			CustomerRepository customerRepository, ApplicationContext applicationContext) {
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
		this.customerRepository = customerRepository;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter tokenProcessingFilter = new JwtAuthenticationFilter(customerRepository);
		tokenProcessingFilter.setAuthenticationManager(authenticationManagerBean());
		RequestMatcher customer = new AntPathRequestMatcher("/customer/**");
		RequestMatcher order = new AntPathRequestMatcher("/order/**");
		RequestMatcher book = new AntPathRequestMatcher("/book/**");

		RequestMatcher orRequestMatcher = new OrRequestMatcher(customer, order, book);

		tokenProcessingFilter.setRequiresAuthenticationRequestMatcher(orRequestMatcher);
		tokenProcessingFilter.setContinueChainBeforeSuccessfulAuthentication(true);
		return tokenProcessingFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Restful
		// hence
		// stateless

//				.and().authorizeRequests()
//				.antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
//						"/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**")
//				.permitAll()

		// whitelist Swagger UI resources
//        // ... here goes your custom security configuration
//        antMatchers("/**").authenticated()

				.and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint) // Notice

				.and().headers().frameOptions().disable()

				// the
				// entry
				// point
				.and().addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // Notice
				// the
				// filter
				// .cors().configurationSource(buildCordConfiguration())
				// .and()

//				.csrf().disable()

				.anonymous().disable();
		// @formatter:on
	}

}
