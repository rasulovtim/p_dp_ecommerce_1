package com.gitlab.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Gazon Admin")
@Route(value = "admin", layout = MainLayout.class)
public class AdminView extends HorizontalLayout {

    private final TextField name;

    public AdminView() {
        name = new TextField("Your name");
        var sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> Notification.show("Hello " + name.getValue()));
        sayHello.addClickShortcut(Key.ENTER);
        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        add(name, sayHello);
    }
}