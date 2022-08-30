package com.poc.application.poc1.config;

import org.glassfish.jersey.server.ResourceConfig;

import com.poc.application.poc1.UserResource;
import com.poc.application.poc1.exception.AccessDeniedExceptionMapper;
import com.poc.application.poc1.exception.AuthenticationExceptionMapper;
import com.poc.application.poc1.security.AuthenticationFilter;
import com.poc.application.poc1.security.AuthorizationFilter;
import com.poc.application.poc1.security.CacheControllFilter;

public class AppConfig extends ResourceConfig {

	public AppConfig() {


		register(UserResource.class);

		register(AuthenticationFilter.class);
		register(AuthorizationFilter.class);
		register(CacheControllFilter.class);

		register(AccessDeniedExceptionMapper.class);
		register(AuthenticationExceptionMapper.class);

	}
}