package com.sai.home.TutorialService.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class TutorialSwagger {
	
	@Bean
	public Docket getApi() {
		
		/*
		 * ApiInfo apiInfo = new ApiInfoBuilder().title("Employee Rest API")
		 * .description("Employee API for creating and managing employees") .contact(new
		 * Contact("Sai Vartha", null, "saivartha@home.com")) .build();
		 * 
		 * 
		 * return new Docket(DocumentationType.SWAGGER_2) .apiInfo(apiInfo);
		 */
		ApiInfo apiInfo = new ApiInfoBuilder().title("Tutorials Rest API")
				 .description("Tutorial API for creating and managing Tutorials") .contact(new
				  Contact("Sai Vartha", null, "saivartha@home.com")) .build();
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/tutorials/*"))
                .build();
		
		
				//.build();
				/*
				 * .select() .apis(RequestHandlerSelectors.basePackage(
				 * "com.sai.home.TutorialService.controller"))
				 * .paths(PathSelectors.ant("/api/tutorials/*"))
				 */
		        
	}

}
