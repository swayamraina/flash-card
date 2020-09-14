package dev.swayamraina.flashcard.web.routes.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component public class Authentication extends HandlerInterceptorAdapter {

    private static final String USER = "x-user-id";
    private static final String PWD = "x-auth-id";

    private Config config;

    @Autowired public Authentication (Config config) { this.config = config; }


    @Override public boolean preHandle (
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        String user = request.getHeader(USER);
        String pwd = request.getHeader(PWD);
        return config.user().equals(user) && config.auth().equals(pwd);
    }

}
