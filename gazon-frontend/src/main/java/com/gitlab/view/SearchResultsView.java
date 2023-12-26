package com.gitlab.view;

import com.gitlab.clients.ProductSearchClient;
import com.gitlab.dto.ProductDto;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Route("search")
public class SearchResultsView extends CommonView implements HasUrlParameter<String> {
    private final ProductSearchClient searchProductClient;
    private final VerticalLayout contentContainer;

    public SearchResultsView(ProductSearchClient searchProductClient) {
        this.searchProductClient = searchProductClient;
        contentContainer = new VerticalLayout();
        add(contentContainer);
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            try {
                performSearch(parameter);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void performSearch(String query) throws InterruptedException {
        if (!query.isEmpty()) {
            ResponseEntity<List<ProductDto>> response = searchProductClient.search(query);
            HttpStatus statusCode = response.getStatusCode();

            if (statusCode.is2xxSuccessful()) {
                List<ProductDto> products = response.getBody();

                if (products != null) {
                    displaySearchResults(products);
                } else {
                    displayNoResults();
                }
            } else if (statusCode == HttpStatus.NO_CONTENT) {
                displayNoResults();
            } else {
                displayError();
            }
        }
    }

    private void displaySearchResults(List<ProductDto> products) {
        contentContainer.removeAll();

        if (!products.isEmpty()) {
            for (ProductDto product : products) {
                Label nameLabel = new Label(product.getName());
                Label priceLabel = new Label("Price: " + product.getPrice());

                contentContainer.add(nameLabel, priceLabel);
            }
        } else {
            displayNoResults();
        }
    }

    private void displayNoResults() {
        contentContainer.removeAll();
        contentContainer.add(new H2("Ничего не найдено"));
    }

    private void displayError() {
        contentContainer.removeAll();
        contentContainer.add(new H2("Произошла ошибка при выполнении поиска"));
    }
}