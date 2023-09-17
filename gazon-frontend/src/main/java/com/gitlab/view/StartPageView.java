package com.gitlab.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

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

        Div historyContainer = new Div();
        VerticalLayout historyLayout = new VerticalLayout();
        H2 history = new H2("Истории");
        history.getStyle()
                .set("text-align", "left")
                .set("font-weight", "bold");
        historyLayout.add(history);

        historyContainer.add(historyLayout);
        historyContainer.getStyle().set("margin-top", "20px");
        add(historyContainer);

        FlexLayout imageContainer = new FlexLayout();
        imageContainer.getStyle().set("display", "flex")
                .set("flex-wrap", "nowrap")
                .set("gap", "20px");

        String[] imageUrls = {
                "https://ir.ozone.ru/s3/cms/98/t81/wc150/270_480_russian_beauty.jpg",
                "https://ir.ozone.ru/s3/cms/6e/t9b/wc150/preview-270x480-min.jpg",
                "https://ir.ozone.ru/s3/cms/81/t79/wc150/0.jpg",
                "https://ir.ozone.ru/s3/cms/02/t92/wc150/preview-270x480.jpg",
                "https://ir.ozone.ru/s3/cms/1f/t1c/wc150/270_480.jpg",
                "https://ir.ozone.ru/s3/cms/34/t54/wc150/270_480.jpg",
                "https://ir.ozone.ru/s3/cms/f4/t93/wc150/270_480_cover.jpg",
                "https://ir.ozone.ru/s3/cms/07/t80/wc150/270_480.jpg",
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
            image.getStyle()
                    .set("width", "160px")
                    .set("height", "150px");
            imageContainer.getStyle().set("max-width", "1280px");
            imageContainer.add(image);
        }

        Div imageScrollContainer = new Div(imageContainer);
        imageScrollContainer.getStyle()
                .set("overflow-x", "auto")
                .set("white-space", "nowrap")
                .set("margin", "10px 0");

        add(imageScrollContainer);

        H2 allStoresHeading = new H2("Магазины");
        allStoresHeading.getStyle().set("font-weight", "bold").set("color", "blue");

        FlexLayout storeContainer1 = createStoreContainer(
                "RIVASSA",
                "ОДЕЖДА РУЧНОЙ РАБОТЫ!",
                "https://ir.ozone.ru/s3/multimedia-q/wc200/6614783414.jpg",
                "https://ir.ozone.ru/s3/multimedia-6/wc200/6612931626.jpg",
                "https://ir.ozone.ru/s3/multimedia-l/wc200/6611740293.jpg"
        );
        storeContainer1.getStyle()
                .set("border", "1px solid #ccc")
                .set("padding", "10px")
                .set("margin-right", "10px");

        FlexLayout storeContainer2 = createStoreContainer(
                "byDeryaeva",
                "ТАЛИСМАНЫ АЛЁНЫ ДЕРЯЕВОЙ",
                "https://ir.ozone.ru/s3/multimedia-h/wc200/6407871629.jpg",
                "https://ir.ozone.ru/s3/multimedia-r/wc200/6439230195.jpg",
                "https://ir.ozone.ru/s3/multimedia-h/wc200/6407871629.jpg"
        );
        storeContainer2.getStyle()
                .set("border", "1px solid #ccc")
                .set("padding", "10px")
                .set("margin-right", "10px");

        FlexLayout horizontalStoreContainer = new FlexLayout(storeContainer1, storeContainer2);
        horizontalStoreContainer.getStyle()
                .set("display", "flex")
                .set("flex-direction", "row");

        Div storesContainer = new Div(allStoresHeading, horizontalStoreContainer);
        storesContainer.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column");

        add(storesContainer);
    }

    private FlexLayout createStoreContainer(String storeName, String storeDescription, String... imageUrls) {
        FlexLayout storeContainer = new FlexLayout();
        storeContainer.setClassName("a3142-a2 cw1");

        VerticalLayout verticalStoreInfo = new VerticalLayout();

        Label storeLabel = new Label(storeName);
        storeLabel.getStyle().set("font-weight", "bold");

        Paragraph storeDescriptionParagraph = new Paragraph(storeDescription);

        verticalStoreInfo.add(storeLabel, storeDescriptionParagraph);

        for (String imageUrl : imageUrls) {
            Image image = new Image(imageUrl, "");
            image.getStyle().set("width", "160px");
            image.getStyle().set("height", "150px");
            storeContainer.add(image);
        }

        storeContainer.add(verticalStoreInfo);

        return storeContainer;
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

