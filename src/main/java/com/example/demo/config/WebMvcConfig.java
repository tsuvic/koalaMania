package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
//https://b1san-blog.com/post/spring/spring-cors/
//https://zenn.dev/luvmini511/articles/d8b2322e95ff40
//SecurityCongfigに記載のhttp.corsを削除し、WebMvcConfigに記載
//No 'Access-Control-Allow-Origin' header is present on the requested resourceを解消

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/api/users/**")
                .allowedOrigins("http://127.0.0.1:8080","http://localhost:8080","https://koalamania.herokuapp.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}