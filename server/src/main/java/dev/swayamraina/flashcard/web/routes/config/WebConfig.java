package dev.swayamraina.flashcard.web.routes.config;

import dev.swayamraina.flashcard.web.routes.interceptor.Authentication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration public class WebConfig implements WebMvcConfigurer {

    private Authentication authentication;

    public WebConfig (Authentication authentication) {
        this.authentication = authentication;
    }

    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authentication);
    }

}
