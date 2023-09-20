package com.gitlab.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

/**
 * Base class CommonView for views.
 * This class provides general functionality that can be used
 * in other views. It involves creating the first and second line,
 * as well as methods for creating icons with text.
 */
public abstract class CommonView extends FlexLayout {

    private SearchBar.SearchListener searchListener;

    public CommonView() {
        setWidthFull();
        setFlexWrap(FlexWrap.WRAP);
        getElement().getStyle()
                .set("max-width", "1280px")
                .set("margin", "0 auto");

        createFirstRow();
        createSecondRow();
    }

    private void createFirstRow() {
        RouterLink catalogLink = new RouterLink("Каталог", CatalogView.class);
        Button catalogButton = new Button("Каталог");
        catalogButton.addClickListener(event -> {
            UI ui = UI.getCurrent();
            if (ui != null) {
                ui.navigate(catalogLink.getHref());
            }
        });
        catalogButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout searchContainer = new HorizontalLayout();
        SearchBar searchBar = new SearchBar();
        searchContainer.add(searchBar);

        Div loginButton = createIconAndTextDiv(VaadinIcon.SMILEY_O, "Войти");
        Div ordersDiv = createIconAndTextDiv(VaadinIcon.PACKAGE, "Заказы");
        Div favoritesButton = createIconAndTextDiv(VaadinIcon.HEART_O, "Избранное");
        Div cartButton = createIconAndTextDiv(VaadinIcon.CART_O, "Корзина");

        FlexLayout firstRow = new FlexLayout(catalogButton, searchContainer, loginButton, ordersDiv, favoritesButton, cartButton);
        firstRow.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("gap", "10px")
                .set("max-width", "100%")
                .set("width", "100%")
                .set("justify-content", "space-between");

        add(firstRow);
    }

    private void createSecondRow() {
        FlexLayout otherButtonsDiv = new FlexLayout();
        otherButtonsDiv.setFlexWrap(FlexWrap.WRAP);

        String[] buttonNames = {
                "Билеты, отели, туры",
                "Одежда и обувь",
                "Электроника",
                "Дом и сад",
                "Детские товары",
                "Premium",
                "Бренды",
                "Продукты питания",
                "Бытовая техника"
        };

        VaadinIcon[] buttonIcons = {
                VaadinIcon.FLIGHT_TAKEOFF,
                VaadinIcon.FEMALE,
                VaadinIcon.MOBILE,
                VaadinIcon.HOME,
                VaadinIcon.CHILD,
                VaadinIcon.GIFT,
                VaadinIcon.TAGS,
                VaadinIcon.CROSS_CUTLERY,
                VaadinIcon.PRESENTATION
        };

        for (int i = 0; i < buttonNames.length; i++) {
            Div buttonDiv = createIconAndTextDiv(buttonIcons[i], buttonNames[i]);
            otherButtonsDiv.add(buttonDiv);
        }

        add(otherButtonsDiv);
    }

    protected Div createIconAndTextDiv(VaadinIcon icon, String text) {
        Icon vaadinIcon = new Icon(icon);
        Span spanText = new Span(text);

        vaadinIcon.getStyle()
                .set("width", "18px")
                .set("height", "18px")
                .set("margin-right", "auto")
                .set("margin-left", "auto");

        spanText.getStyle().set("font", "bold 12px Arial, sans-serif");

        Div iconAndTextDiv = new Div(vaadinIcon, spanText);

        iconAndTextDiv.getStyle().set("display", "flex");

        if (text.equals("Войти") || text.equals("Заказы") || text.equals("Избранное") || text.equals("Корзина")) {
            iconAndTextDiv.getStyle().set("flex-direction", "column");
        } else {
            vaadinIcon.getStyle()
                    .set("margin-right", "10px")
                    .set("margin-left", "15px");
        }

        return iconAndTextDiv;
    }
}