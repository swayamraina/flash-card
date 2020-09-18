package dev.swayamraina.flashcard.web.routes.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("config") @PropertySource(value = "classpath:server.properties")
public class Config {

    @Value("${header.user}") private String user;
    public String user () { return user; }

    @Value("${header.auth}") private String auth;
    public String auth () { return auth; }

    @Value("${enable.bloom}") private boolean bloom;
    public boolean bloom () { return bloom; }

    @Value("${storage.format}") private String format;
    public String format () { return format; }

}
