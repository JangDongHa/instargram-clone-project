package com.clone.instargram.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        // axios default withcredentials true
        config.addAllowedOriginPattern("*"); // 모든 IP에 대한 응답을 허용
        //config.addExposedHeader("Authorization");

        config.addExposedHeader("Authorization");
        config.addAllowedHeader("*"); // 모든 Header에 대한 응답을 허용
        config.addAllowedMethod("*"); // 모든 메소드(GET, POST, PUT....)에 대한 응답을 허용

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}