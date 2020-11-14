package com.example.curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
public class CursoApplication implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry){
		// addMapping = são os endpoints da api permitidos e allowedOrigins = é a url do front end que vai fazer requisição
		// corsRegistry.addMapping("/**").allowedOrigins("urlOrigemFront").allowedMethods();
		corsRegistry.addMapping("/**").allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
	}

	public static void main(String[] args) {
		SpringApplication.run(CursoApplication.class, args);
	}

}
