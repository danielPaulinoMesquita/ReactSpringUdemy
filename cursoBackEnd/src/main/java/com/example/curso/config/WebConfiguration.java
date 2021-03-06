package com.example.curso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        // addMapping = são os endpoints da api permitidos e allowedOrigins = é a url do front end que vai fazer requisição
        // corsRegistry.addMapping("/**").allowedOrigins("urlOrigemFront").allowedMethods();
        corsRegistry.addMapping("/**").allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
    }
}
