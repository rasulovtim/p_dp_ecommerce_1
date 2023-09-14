package com.gitlab.view;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("")
public class StartPageView extends FlexLayout {
    public StartPageView() {
        setWidthFull();
        setFlexWrap(FlexWrap.WRAP);
        getElement().getStyle().set("max-width", "1280px");
        getElement().getStyle().set("margin", "0 auto");

        RouterLink catalogLink = new RouterLink("Каталог", CatalogView.class);
        Button catalogButton = new Button("Каталог");
        catalogButton.addClickListener(event -> catalogLink.getUI().ifPresent(ui -> ui.navigate(catalogLink.getHref())));

        catalogButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout searchContainer = new HorizontalLayout();

        SearchBar searchBar = new SearchBar();
        TextField searchField = searchBar.getSearchField();

        searchContainer.add(searchBar);

        Div loginButton = createIconAndTextDiv(VaadinIcon.SMILEY_O, "Войти");
        Div ordersDiv = createIconAndTextDiv(VaadinIcon.PACKAGE, "Заказы");
        Div favoritesButton = createIconAndTextDiv(VaadinIcon.HEART_O, "Избранное");
        Div cartButton = createIconAndTextDiv(VaadinIcon.CART_O, "Корзина");

        FlexLayout firstRow = new FlexLayout(catalogButton, searchContainer, loginButton, ordersDiv, favoritesButton, cartButton);
        firstRow.getStyle().set("display", "flex");
        firstRow.getStyle().set("align-items", "center");
        firstRow.getStyle().set("gap", "10px");
        firstRow.getStyle().set("max-width", "100%");
        firstRow.getStyle().set("width", "100%");
        firstRow.getStyle().set("justify-content", "space-between");

        add(firstRow);

        FlexLayout otherButtonsDiv = new FlexLayout();
        otherButtonsDiv.setFlexWrap(FlexWrap.WRAP);

        Div ticketsButton = createIconAndTextDiv(VaadinIcon.FLIGHT_TAKEOFF, "Билеты, отели, туры");
        Div clothingButton = createIconAndTextDiv(VaadinIcon.FEMALE, "Одежда и обувь");
        Div electronicsButton = createIconAndTextDiv(VaadinIcon.MOBILE, "Электроника");
        Div homeButton = createIconAndTextDiv(VaadinIcon.HOME, "Дом и сад");
        Div kidsButton = createIconAndTextDiv(VaadinIcon.CHILD, "Детские товары");
        Div premiumButton = createIconAndTextDiv(VaadinIcon.GIFT, "Premium");
        Div brandsButton = createIconAndTextDiv(VaadinIcon.TAGS, "Бренды");
        Div foodButton = createIconAndTextDiv(VaadinIcon.CROSS_CUTLERY, "Продукты питания");
        Div appliancesButton = createIconAndTextDiv(VaadinIcon.PRESENTATION, "Бытовая техника");

        otherButtonsDiv.add(ticketsButton, clothingButton, electronicsButton, homeButton,
                kidsButton, premiumButton, brandsButton, foodButton, appliancesButton);

        add(otherButtonsDiv);

        Div historyContainer = new Div();
        H2 history = new H2("Истории");
        history.getStyle().set("text-align", "left");
        historyContainer.add(history);
        historyContainer.getStyle().set("margin-top", "20px");
        add(historyContainer);

        FlexLayout imageContainer = new FlexLayout();
        imageContainer.getStyle().set("display", "flex");
        imageContainer.getStyle().set("flex-wrap", "nowrap");
        imageContainer.getStyle().set("gap", "20px");

        String[] imageUrls = {
                "https://ir.ozone.ru/s3/cms/98/t81/wc150/270_480_russian_beauty.jpg",
                "https://ir.ozone.ru/s3/cms/6e/t9b/wc150/preview-270x480-min.jpg",
                "https://ir.ozone.ru/s3/cms/81/t79/wc150/0.jpg",
                "https://ir.ozone.ru/s3/cms/02/t92/wc150/preview-270x480.jpg",
                "https://ir.ozone.ru/s3/cms/64/te5/wc150/preview-270x480_aeroflot-min.jpg",
                "https://ir.ozone.ru/s3/cms/9e/t2e/wc150/preview-270x480-min.jpg",
                "https://ir.ozone.ru/s3/cms/3b/t1a/wc150/preview-270x480-min.jpg",
                "https://ir.ozone.ru/s3/cms/d6/t17/wc150/preview-270x480-min.jpg",
                "https://ir.ozone.ru/s3/cms/29/ta8/wc150/preview-270x480-min.jpg",
                "https://ir.ozone.ru/s3/cms/64/t51/wc150/270x480.jpg",
                "https://ir.ozone.ru/s3/cms/27/tca/wc150/preview_270x480.jpg",
                "https://ir.ozone.ru/s3/cms/c4/t75/wc150/preview_270x480.jpg",
                "https://ir.ozone.ru/s3/cms/af/tb7/wc150/preview-270x480-min.jpg",
                "https://ir.ozone.ru/s3/cms/0f/t5e/wc150/preview_270x480.jpg"
        };

        for (String imageUrl : imageUrls) {
            Image image = new Image(imageUrl, "");
            image.getStyle().set("width", "160px");
            image.getStyle().set("height", "150px");
            imageContainer.getStyle().set("max-width", "1280px");
            imageContainer.add(image);
        }

        Div imageScrollContainer = new Div(imageContainer);
        imageScrollContainer.getStyle().set("overflow-x", "auto");
        imageScrollContainer.getStyle().set("white-space", "nowrap");
        imageScrollContainer.getStyle().set("margin", "10px 0");

        add(imageScrollContainer);

        H2 allStoresHeading = new H2("Все магазины");

        allStoresHeading.getStyle().set("font-weight", "bold").set("color", "blue");

        add(allStoresHeading);

        searchField.addKeyPressListener(Key.ENTER, event -> performSearch(searchField));

        firstRow.getStyle().set("margin-bottom", "20px");

        otherButtonsDiv.getStyle().set("margin-bottom", "20px");
    }

    private void performSearch(TextField searchField) {
        String query = searchField.getValue();
        if (!query.isEmpty()) {
            Map<String, List<String>> parametersMap = new HashMap<>();
            parametersMap.put("text", Collections.singletonList(query));

            QueryParameters queryParameters = new QueryParameters(parametersMap);

            UI.getCurrent().navigate("search", queryParameters);
        }
    }

    private Div createIconAndTextDiv(VaadinIcon icon, String text) {
        Icon vaadinIcon = new Icon(icon);
        Span spanText = new Span(text);

        vaadinIcon.getStyle().set("width", "18px");
        vaadinIcon.getStyle().set("height", "18px");

        vaadinIcon.getStyle().set("margin-right", "auto");
        vaadinIcon.getStyle().set("margin-left", "auto");

        spanText.getStyle().set("font", "bold 12px Arial, sans-serif");

        Div iconAndTextDiv = new Div(vaadinIcon, spanText);

        iconAndTextDiv.getStyle().set("display", "flex");

        if (text.equals("Войти") || text.equals("Заказы") || text.equals("Избранное") || text.equals("Корзина")) {
            iconAndTextDiv.getStyle().set("flex-direction", "column");
        } else {
            vaadinIcon.getStyle().set("margin-right", "10px");
            vaadinIcon.getStyle().set("margin-left", "15px");
        }

        return iconAndTextDiv;
    }
}