package com.example.application.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
@Secured("ROLE_USER")
public class AboutView extends Div {

    public AboutView() {
        addClassName("about-view");
        add(new Text("Content placeholder"));
    }

}
