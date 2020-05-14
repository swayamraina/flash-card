package dev.swayamraina.flashcard.web.routes.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("auth") @PropertySource(value = "classpath:header.properties")
public class Config {

    @Value("${header.user}") private String user;
    public String user () { return user; }

    @Value("${header.auth}") private String auth;
    public String auth () { return auth; }

}
