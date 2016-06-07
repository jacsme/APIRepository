package com.wom.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport{
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
		configurer.favorPathExtension(false).favorParameter(true);
	}	
	
	/**
	 * This class will get the full path of request. Especially those with dot parameter.
	 **/
	
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
		handlerMapping.setUseSuffixPatternMatch(false);
		handlerMapping.setUseTrailingSlashMatch(false);
		handlerMapping.setAlwaysUseFullPath(true);
		return handlerMapping;
	}
	
}
