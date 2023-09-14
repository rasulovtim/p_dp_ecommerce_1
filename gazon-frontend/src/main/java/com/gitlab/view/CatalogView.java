package com.gitlab.view;

// Импортируйте необходимые классы Vaadin
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.gitlab.clients.ProductClient;
import com.gitlab.dto.ProductDto;
import com.vaadin.flow.server.VaadinRequest;

import java.util.List;
import java.util.stream.Collectors;

@Route("catalog")
public class CatalogView extends VerticalLayout {
    private final ProductClient productClient;
    private final Grid<ProductDto> productGrid;

    public CatalogView(ProductClient productClient) {
        this.productClient = productClient;

        productGrid = new Grid<>(ProductDto.class);
        productGrid.setColumns("name", "description", "price");
        productGrid.setSizeFull();

        add(productGrid);

        String query = VaadinRequest.getCurrent().getParameter("query");

        if (query != null && !query.isEmpty()) {
            List<ProductDto> allProducts = productClient.getAll().getBody();

            if (allProducts != null) {
                List<ProductDto> filteredProducts = allProducts.stream()
                        .filter(product -> product.getName().contains(query))
                        .collect(Collectors.toList());

                productGrid.setItems(filteredProducts);
            }
        }
    }
}