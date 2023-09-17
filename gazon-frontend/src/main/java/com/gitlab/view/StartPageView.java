package com.gitlab.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class StartPageView extends CommonView {

    public StartPageView() {
        createHistorySection();
        createImageSection();
        createStoresSection();
    }

    private void createHistorySection() {
        VerticalLayout historyContainer = new VerticalLayout();
        H2 history = new H2("Истории");
        history.getStyle()
                .set("text-align", "left")
                .set("font-weight", "bold");
        historyContainer.add(history);
        historyContainer.getStyle().set("margin-top", "20px");
        add(historyContainer);
    }

    private void createImageSection() {
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
    }

    private void createStoresSection() {
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

        VerticalLayout verticalStoreInfo = new VerticalLayout();

        H2 storeLabel = new H2(storeName);
        storeLabel.getStyle().set("font-weight", "bold");

        Div storeDescriptionDiv = new Div();
        storeDescriptionDiv.setText(storeDescription);

        verticalStoreInfo.add(storeLabel, storeDescriptionDiv);

        for (String imageUrl : imageUrls) {
            Image image = new Image(imageUrl, "");
            image.getStyle().set("width", "160px");
            image.getStyle().set("height", "150px");
            storeContainer.add(image);
        }

        storeContainer.add(verticalStoreInfo);

        return storeContainer;
    }
}