package com.example.application.views.login;

import java.util.Collections;

import com.example.application.util.CustomRequestCache;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver { // 
    public static final String ROUTE = "login";

    private LoginOverlay login = new LoginOverlay(); // 

    @Autowired
    public LoginView(AuthenticationManager authenticationManager, // 
            CustomRequestCache requestCache) {
        // configures login dialog and adds it to the main view
        login.setOpened(true);
        login.setTitle("Spring Secured Vaadin");
        login.setDescription("Login Overlay Example");

        add(login);

        login.setAction("login");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) { // 
        // inform the user about an authentication error
        // (yes, the API for resolving query parameters is annoying...)
        if (!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList())
                .isEmpty()) {
            login.setError(true); // 
        }
    }
}