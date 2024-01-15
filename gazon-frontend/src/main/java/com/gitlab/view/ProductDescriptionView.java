package com.gitlab.view;

import com.gitlab.clients.ProductDescriptionClient;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "product/id", layout = MainLayout.class)
public class ProductDescriptionView extends VerticalLayout {
    private final ProductDescriptionClient productDescriptionClient;

    public ProductDescriptionView(ProductDescriptionClient productDescriptionClient) {
        this.productDescriptionClient = productDescriptionClient;
    }
}
