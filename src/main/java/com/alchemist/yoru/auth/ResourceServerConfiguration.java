package com.alchemist.yoru.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Atul Mundaware
 * @since 17 04 2023
 */

@Configuration
@EnableResourceServer
@EnableTransactionManagement
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "microservice";
	private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('READ')";
	private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('WRITE')";
	private static final String SECURED_PATTERN = "/**";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.sessionManagement().disable()
				.authorizeRequests()
				.antMatchers("/user").permitAll()
				.antMatchers("/v2/api-docs","/configuration/ui",
							"/swagger-resources/**",
							"/configuration/security",
							"/swagger-ui.html",
							"/webjars/**").permitAll()
				.and()
				.requestMatchers().antMatchers(SECURED_PATTERN)
				.and()
				.authorizeRequests().antMatchers(HttpMethod.POST, SECURED_PATTERN)
				.access(SECURED_WRITE_SCOPE).anyRequest()
				.access(SECURED_READ_SCOPE);
	}
}
