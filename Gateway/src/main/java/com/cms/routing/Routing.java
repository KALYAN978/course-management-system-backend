package com.cms.routing;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Routing {

	@Bean
	RouteLocator configure(RouteLocatorBuilder builder) {
		return builder.routes().route(r -> r.path("/courses/**").uri("lb://COURSE-SERVICE"))
				.route(r -> r.path("/associate/**").uri("lb://ASSOCIATE-SERVICE"))
				.route(r -> r.path("/admission/**").uri("lb://ADMISSION-SERVICE")).build();
	}

}
