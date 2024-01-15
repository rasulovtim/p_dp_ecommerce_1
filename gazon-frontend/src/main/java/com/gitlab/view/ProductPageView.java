package com.gitlab.view;

import com.gitlab.clients.ProductPageClient;
import com.gitlab.dto.ProductDto;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Route(value = "product")
public class ProductPageView extends CommonView implements HasUrlParameter<String> {
    private final ProductPageClient productPageClient;
    private final FlexLayout contentContainer;

    public ProductPageView(ProductPageClient productPageClient) {
        this.productPageClient = productPageClient;
        contentContainer = new FlexLayout();
        contentContainer.setWidth("1100px");
        contentContainer.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        contentContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        add(contentContainer);
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            try {
                getProductDtoById(Long.parseLong(parameter));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getProductDtoById(Long id) throws InterruptedException {
        ResponseEntity<ProductDto> response = productPageClient.get(id);
        HttpStatus statusCode = response.getStatusCode();

        if (statusCode.is2xxSuccessful()) {
            displayProductPage(response.getBody());
        } else if (statusCode == HttpStatus.NO_CONTENT) {
            displayNoResult();
        } else {
            displayError();
        }
    }

    private void displayProductPage(ProductDto product) {
        contentContainer.removeAll();
        contentContainer.add(new ProductPage(product));
    }

    private void displayNoResult() {
        contentContainer.removeAll();
        contentContainer.add(new H2("Ничего не найдено"));
    }

    private void displayError() {
        contentContainer.removeAll();
        contentContainer.add(new H2("Произошла ошибка при отображении товара"));
    }

    public static class ProductPage extends Composite<HorizontalLayout> {

        public ProductPage(ProductDto productDto) {

        }
    }
}
