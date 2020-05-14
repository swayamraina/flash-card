package dev.swayamraina.flashcard.service.github.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration @PropertySource(value = "classpath:github.properties")
public class Config {

    @Value("${github.username}") private String username;
    public String username () { return username; }

    @Value("${github.email}") private String email;
    public String email () { return email; }

    @Value("${github.access.token}") private String token;
    public String token () { return token; }

    @Value("${github.repo}") private String repo;
    public String repo () { return repo; }

}
