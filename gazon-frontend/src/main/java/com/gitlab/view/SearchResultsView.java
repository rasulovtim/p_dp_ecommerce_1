package com.gitlab.view;

import com.gitlab.clients.ProductImageClient;
import com.gitlab.clients.ProductSearchClient;
import com.gitlab.clients.ReviewClient;
import com.gitlab.dto.ProductDto;
import com.gitlab.dto.ProductImageDto;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import com.vaadin.flow.server.StreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.util.List;

@Route("search")
public class SearchResultsView extends CommonView implements HasUrlParameter<String> {
    private final ProductSearchClient productSearchClient;
    private final ProductImageClient productImageClient;
    private final ReviewClient reviewClient;
    private final FlexLayout contentContainer;

    public SearchResultsView(ProductSearchClient productSearchClient, ProductImageClient productImageClient, ReviewClient reviewClient) {
        this.productSearchClient = productSearchClient;
        this.productImageClient = productImageClient;
        this.reviewClient = reviewClient;
        //поменять раскладку на список вместо плитки
        contentContainer = new FlexLayout();
        contentContainer.setWidth("1100px");
        contentContainer.setFlexDirection(FlexDirection.ROW);
        contentContainer.setFlexWrap(FlexWrap.WRAP);
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
            ResponseEntity<List<ProductDto>> response = productSearchClient.search(query);
            HttpStatus statusCode = response.getStatusCode();

            if (statusCode.is2xxSuccessful()) {
                List<ProductDto> products = response.getBody();

                if (products != null) {
                    displaySearchResults(products.stream().map(this::getProductView).toList());
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

    private void displaySearchResults(List<ProductComponent> products) {
        contentContainer.removeAll();
        if (!products.isEmpty()) {
            for (ProductComponent product : products) {
                contentContainer.add(product);
            }
        } else {
            displayNoResults();
        }
    }

    private ProductComponent getProductView(ProductDto productDto) {
        return new ProductComponent(productDto.getName(), productDto.getPrice().toString(), getImages(productDto),
                productDto.getDescription(), productDto.getRating(), getReviewAmount(productDto));
    }

    private Image getImages(ProductDto productDto) {
        List<ProductImageDto> productImageDtos = productImageClient.getAllByProductId(productDto.getId()).getBody();
        Image resImage;
        if (productImageDtos != null && !productImageDtos.isEmpty()) {
            StreamResource sr = new StreamResource("image", () -> new ByteArrayInputStream(productImageDtos.get(0).getData()));
            sr.setContentType("image/png");
            resImage = new Image(sr, "profile-picture");

        } else {
            resImage = new Image("https://cdn-icons-png.flaticon.com/512/4054/4054617.png", "no image");
        }
        resImage.setWidth("222px");
        resImage.setHeight("267px");
        return resImage;
    }

    private Long getReviewAmount(ProductDto productDto) {
        ResponseEntity<Long> reviewAmount = reviewClient.getReviewAmount(productDto.getId());
        return reviewAmount.getBody();
    }

    private void displayNoResults() {
        contentContainer.removeAll();
        contentContainer.add(new H2("Ничего не найдено"));
    }

    private void displayError() {
        contentContainer.removeAll();
        contentContainer.add(new H2("Произошла ошибка при выполнении поиска"));
    }

    public static class ProductComponent extends Composite<VerticalLayout> {
        private final Image productImage;
        private final Label productName;
        private final Label productPrice;
        private final Label productDescription;
        private final Label productRating;
        private final Label productReviews;

        public ProductComponent(String name, String price, Image image, String description, String rating, Long reviewAmount) {
            productImage = image;
            productName = new Label(name);
            productPrice = new Label(price + " руб.");
            //нет структуры описания продуктов, поэтому пока просто берем первые 100 символов
            productDescription = new Label(description.substring(0, Math.min(description.length(), 100)) + "...");
            productRating = new Label(rating);
            productReviews = new Label(reviewAmount + " отзывов");
            productName.setMaxWidth("250px");

            productName.getElement().getStyle().set("overflow", "hidden");
            productName.getElement().getStyle().set("text-overflow", "ellipsis");
            productName.getElement().getStyle().set("white-space", "nowrap");
            productName.getElement().getStyle().set("cursor", "pointer");

            productName.addAttachListener(e -> {
                Tooltip tooltip = Tooltip.forComponent(productName);
                tooltip.setPosition(Tooltip.TooltipPosition.BOTTOM);
                tooltip.setText(name);
            });

            getContent().add(productImage, productPrice, productName, productDescription, productRating, productReviews);
            getContent().setHeight("400px");
            getContent().setWidth("267px");

        }
    }
}