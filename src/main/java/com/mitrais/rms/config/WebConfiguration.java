package com.mitrais.rms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//Add bootstrap resource
		registry.addResourceHandler("/bootstrap/**") //
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/3.3.6/");
		
		//Add jquery resource
		registry.addResourceHandler("/jquery/**") //
		.addResourceLocations("classpath:/META-INF/resources/webjars/jquery/1.9.1/");
	}

}
