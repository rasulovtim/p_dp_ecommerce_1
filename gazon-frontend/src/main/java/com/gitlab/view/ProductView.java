package com.gitlab.view;

import com.gitlab.clients.ProductClient;
import com.gitlab.dto.ProductDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "product", layout = MainLayout.class)
public class ProductView extends VerticalLayout {

    private final Grid<ProductDto> grid = new Grid<>(ProductDto.class, false);
    private final Editor<ProductDto> editor = grid.getEditor();
    private final ProductClient productClient;
    private final List<ProductDto> dataSource;

    public ProductView(ProductClient productClient) {
        this.productClient = productClient;
        this.dataSource = productClient.getAll().getBody().stream().collect(Collectors.toList());

        ValidationMessage idValidationMessage = new ValidationMessage();
        ValidationMessage productNameValidationMessage = new ValidationMessage();
        ValidationMessage productDescriptionValidationMessage = new ValidationMessage();
        ValidationMessage productStockCountValidationMessage = new ValidationMessage();
        ValidationMessage productImagesIdValidationMessage = new ValidationMessage();
        ValidationMessage productIsAdultValidationMessage = new ValidationMessage();
        ValidationMessage productCodeValidationMessage = new ValidationMessage();
        ValidationMessage productWeightValidationMessage = new ValidationMessage();
        ValidationMessage productPriceValidationMessage = new ValidationMessage();
        ValidationMessage productRatingValidationMessage = new ValidationMessage();

        Grid.Column<ProductDto> idColumn = createIdColumn();
        Grid.Column<ProductDto> productNameColumn = createProductNameColumn();
        Grid.Column<ProductDto> productDescriptionColumn = createProductDescriptionColumn();
        Grid.Column<ProductDto> productStockCountColumn = createProductStockCountColumn();
        Grid.Column<ProductDto> productImagesIdColumn = createProductImagesIdColumn();
        Grid.Column<ProductDto> productIsAdultColumn = createProductIsAdultColumn();
        Grid.Column<ProductDto> productCodeColumn = createProductCodeColumn();
        Grid.Column<ProductDto> productWeightColumn = createProductWeightColumn();
        Grid.Column<ProductDto> productPriceColumn = createProductPriceColumn();
        Grid.Column<ProductDto> productRatingColumn = createProductRatingColumn();

        Grid.Column<ProductDto> updateColumn = createEditColumn();
        createDeleteColumn();

        Binder<ProductDto> binder = createBinder();

        createIdField(binder, idValidationMessage, idColumn);
        createProductNameField(binder, productNameValidationMessage, productNameColumn);
        createProductDescriptionField(binder, productDescriptionValidationMessage, productDescriptionColumn);
        createProductStockCountField(binder, productStockCountValidationMessage, productStockCountColumn);
        createProductImagesIdField(binder, productImagesIdValidationMessage, productImagesIdColumn);
        createProductIsAdultField(binder, productIsAdultValidationMessage, productIsAdultColumn);
        createProductCodeField(binder, productCodeValidationMessage, productCodeColumn);
        createProductWeightField(binder, productWeightValidationMessage, productWeightColumn);
        createProductPriceField(binder, productPriceValidationMessage, productPriceColumn);
        createProductRatingField(binder, productRatingValidationMessage, productRatingColumn);

        Button updateButton = new Button("Update", e -> editor.save());
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel());

        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(updateButton, cancelButton);
        actions.setPadding(false);
        updateColumn.setEditorComponent(actions);

        addEditorListeners();
        grid.setItems(dataSource);
        addTheme();

        Div contentContainer = new Div();
        contentContainer.setSizeFull();
        Tabs tabs = createTabs(contentContainer);

        add(
                tabs,
                contentContainer,
                idValidationMessage,
                productNameValidationMessage,
                productDescriptionValidationMessage,
                productStockCountValidationMessage,
                productImagesIdValidationMessage,
                productIsAdultValidationMessage,
                productCodeValidationMessage,
                productWeightValidationMessage,
                productPriceValidationMessage,
                productRatingValidationMessage
        );
    }

    private Grid.Column<ProductDto> createIdColumn() {
        return grid.addColumn(productDto -> productDto.getId().intValue()).setHeader("Id").setWidth("120px").setFlexGrow(0);
    }

    private Grid.Column<ProductDto> createProductNameColumn() {
        return grid.addColumn(ProductDto::getName).setHeader("Product name").setWidth("650px");
    }

    private Grid.Column<ProductDto> createProductDescriptionColumn() {
        return grid.addColumn(ProductDto::getDescription).setHeader("Product description").setWidth("650px");
    }

    private Grid.Column<ProductDto> createProductStockCountColumn() {
        return grid.addColumn(ProductDto::getStockCount).setHeader("Product stock count").setWidth("650px");
    }

    private Grid.Column<ProductDto> createProductImagesIdColumn() {
        return grid.addColumn(ProductDto::getImagesId).setHeader("Product images").setWidth("650px");
    }

    private Grid.Column<ProductDto> createProductIsAdultColumn() {
        return grid.addColumn(ProductDto::getIsAdult).setHeader("Product adult").setWidth("650px");
    }

    private Grid.Column<ProductDto> createProductCodeColumn() {
        return grid.addColumn(ProductDto::getCode).setHeader("Product code").setWidth("650px");
    }

    private Grid.Column<ProductDto> createProductWeightColumn() {
        return grid.addColumn(ProductDto::getWeight).setHeader("Product weight").setWidth("650px");
    }

    private Grid.Column<ProductDto> createProductPriceColumn() {
        return grid.addColumn(ProductDto::getPrice).setHeader("Product price").setWidth("650px");
    }

    private Grid.Column<ProductDto> createProductRatingColumn() {
        return grid.addColumn(ProductDto::getRating).setHeader("Product rating").setWidth("650px");
    }

    private Grid.Column<ProductDto> createEditColumn() {
        return grid.addComponentColumn(product -> {
            Button updateButton = new Button("Update");
            updateButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(product);
            });
            return updateButton;
        });
    }

    private Grid.Column<ProductDto> createDeleteColumn() {
        return grid.addComponentColumn(product -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<ProductDto> dataProvider = (ListDataProvider<ProductDto>) grid.getDataProvider();
                    productClient.delete(product.getId());
                    dataProvider.getItems().remove(product);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

    private Binder<ProductDto> createBinder() {
        Binder<ProductDto> binder = new Binder<>(ProductDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);
        return binder;
    }

    private void createIdField(Binder<ProductDto> binder,
                               ValidationMessage idValidationMessage,
                               Grid.Column<ProductDto> idColumn) {
        IntegerField idField = new IntegerField();
        idField.setWidthFull();
        binder.forField(idField)
                .asRequired("Id must not be empty")
                .withStatusLabel(idValidationMessage)
                .bind(productDto -> productDto.getId().intValue(),
                        (productDto, integer) -> productDto.setId(integer.longValue()));
        idColumn.setEditorComponent(idField);
    }

    private void createProductNameField(Binder<ProductDto> binder,
                                        ValidationMessage productNameValidationMessage,
                                        Grid.Column<ProductDto> productNameColumn) {
        TextField productNameField = new TextField();
        productNameField.setWidthFull();
        binder.forField(productNameField).asRequired("Product name must not be empty")
                .withStatusLabel(productNameValidationMessage)
                .bind(ProductDto::getName, ProductDto::setName);
        productNameColumn.setEditorComponent(productNameField);
    }

    private void createProductDescriptionField(Binder<ProductDto> binder,
                                               ValidationMessage productDescriptionValidationMessage,
                                               Grid.Column<ProductDto> productDescriptionColumn) {
        TextField productDescriptionField = new TextField();
        productDescriptionField.setWidthFull();
        binder.forField(productDescriptionField).asRequired("Product description must not be empty")
                .withStatusLabel(productDescriptionValidationMessage)
                .bind(ProductDto::getDescription, ProductDto::setDescription);
        productDescriptionColumn.setEditorComponent(productDescriptionField);
    }


    private void createProductStockCountField(Binder<ProductDto> binder,
                                              ValidationMessage producStockCountValidationMessage,
                                              Grid.Column<ProductDto> productStockCountColumn) {
        IntegerField productStockCountField = new IntegerField();
        productStockCountField.setWidthFull();
        binder.forField(productStockCountField).asRequired("Product stock count must not be empty")
                .withStatusLabel(producStockCountValidationMessage)
                .bind(ProductDto::getStockCount, ProductDto::setStockCount);
        productStockCountColumn.setEditorComponent(productStockCountField);
    }

        private void createProductImagesIdField(Binder<ProductDto> binder,
                                        ValidationMessage productImagesIdValidationMessage,
                                        Grid.Column<ProductDto> productImagesIdColumn) {
            TextField productImagesIdField = new TextField();
        productImagesIdField.setWidthFull();
        binder.forField(productImagesIdField).asRequired("Product images id must not be empty")
                .withStatusLabel(productImagesIdValidationMessage)
                .bind(productDto -> Arrays.toString(productDto.getImagesId()),
                        (productDto1, imagesId) -> productDto1.setImagesId(new Long[]{Long.valueOf(imagesId)}));
        productImagesIdColumn.setEditorComponent(productImagesIdField);
    }

        private void createProductIsAdultField(Binder<ProductDto> binder,
                                        ValidationMessage productIsAdultValidationMessage,
                                        Grid.Column<ProductDto> productIsAdultColumn) {
        Select<String> productIsAdultField = new Select<>();
        productIsAdultField.setWidthFull();
        productIsAdultField.setEmptySelectionAllowed(true);
        productIsAdultField.setLabel("ProductIsAdult");
        productIsAdultField.setItems("true", "false");
        productIsAdultField.setPlaceholder("Select");
        productIsAdultColumn.setEditorComponent(productIsAdultField);
    }

    private void createProductCodeField(Binder<ProductDto> binder,
                                        ValidationMessage productCodeValidationMessage,
                                        Grid.Column<ProductDto> productCodeColumn) {
        TextField productCodeField = new TextField();
        productCodeField.setWidthFull();
        binder.forField(productCodeField).asRequired("Product code must not be empty")
                .withStatusLabel(productCodeValidationMessage)
                .bind(ProductDto::getCode, ProductDto::setCode);
        productCodeColumn.setEditorComponent(productCodeField);
    }

    private void createProductWeightField(Binder<ProductDto> binder,
                                        ValidationMessage productWeightValidationMessage,
                                        Grid.Column<ProductDto> productWeightColumn) {
        TextField productWeightField = new TextField();
        productWeightField.setWidthFull();
        binder.forField(productWeightField).asRequired("Product weight must not be empty")
                .withStatusLabel(productWeightValidationMessage)
                .bind(productDto -> String.valueOf(productDto.getWeight()),
                        (productDto1, weight) -> productDto1.setWeight(Long.valueOf(weight)));
        productWeightColumn.setEditorComponent(productWeightField);
    }

    private void createProductPriceField(Binder<ProductDto> binder,
                                        ValidationMessage productPriceValidationMessage,
                                        Grid.Column<ProductDto> productPriceColumn) {
        BigDecimalField productPriceField = new  BigDecimalField();
        productPriceField.setWidthFull();
        binder.forField(productPriceField).asRequired("Product price must not be empty")
                .withStatusLabel(productPriceValidationMessage)
                .bind(productDto1 -> productDto1.getPrice(), (productDto, price) -> productDto.setPrice(price));
        productPriceColumn.setEditorComponent(productPriceField);
    }

    private void createProductRatingField(Binder<ProductDto> binder,
                                         ValidationMessage productRatingValidationMessage,
                                         Grid.Column<ProductDto> productRatingColumn) {
        TextField productRatingField = new TextField();
        productRatingField.setWidthFull();
        binder.forField(productRatingField).asRequired("Product rating must not be empty")
                .withStatusLabel(productRatingValidationMessage)
                .bind(productDto -> String.valueOf(productDto.getRating()),
                        (productDto1, rating) -> productDto1. setRating(Byte.valueOf(rating)));
        productRatingColumn.setEditorComponent(productRatingField);
    }


    private void addEditorListeners() {
        editor.addSaveListener(e -> {
            productClient.update(e.getItem().getId(), e.getItem());
            grid.getDataProvider().refreshAll();
        });
    }

    private void addTheme() {
        getThemeList().clear();
        getThemeList().add("spacing-s");
    }

    private Tabs createTabs(Div contentContainer) {
        Tabs tabs = new Tabs();

        Tab tableTab = new Tab("Product table");
        FormLayout formLayout = new FormLayout();
        Tab createTab = createCreateTab(formLayout);

        contentContainer.add(grid);
        tabs.add(tableTab, createTab);
        tabs.setSelectedTab(tableTab);

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = tabs.getSelectedTab();
            if (selectedTab == tableTab) {
                contentContainer.removeAll();
                contentContainer.add(grid);
            } else if (selectedTab == createTab) {
                contentContainer.removeAll();
                contentContainer.add(formLayout);
                grid.getDataProvider().refreshAll();
            }
        });
        return tabs;
    }

    private Tab createCreateTab(FormLayout formLayout) {
        Tab createTab = new Tab("Create product");

        VerticalLayout verticalLayoutMain = new VerticalLayout();
        verticalLayoutMain.addClassName(LumoUtility.Gap.XSMALL);
        HorizontalLayout horizontalLayoutMain = new HorizontalLayout();
        horizontalLayoutMain.addClassName(LumoUtility.Gap.XSMALL);

        TextField productNameField = new TextField("Product Name");
        TextField productDescriptionField = new TextField("Product Description");
        IntegerField productStockCountField = new IntegerField("Product StockCount");
        TextField productImagesIdField = new TextField("Product Images");

        Select<String> productIsAdultField = new Select<>();
        productIsAdultField.setWidthFull();
        productIsAdultField.setEmptySelectionAllowed(true);
        productIsAdultField.setLabel("Product Is Adult");
        productIsAdultField.setItems("true", "false");
        productIsAdultField.setPlaceholder("Select");
        add(productIsAdultField);

        TextField productCodeField = new TextField("Product Code");
        TextField productWeightField = new TextField("Product Weight");
        BigDecimalField productPriceField = new BigDecimalField("ProductPrice");
        IntegerField productRatingField = new IntegerField("Product Rating");

        Button createButton = new Button("Create");
        formLayout.add(
                productNameField,
                productDescriptionField,
                productStockCountField,
                productImagesIdField,
                productIsAdultField,
                productCodeField,
                productWeightField,
                productPriceField,
                productRatingField,
                createButton);
        createButton.addClickListener(event -> {
            ProductDto productDto = new ProductDto();
            productDto.setName(productNameField.getValue());
            productDto.setDescription(productDescriptionField.getValue());
            productDto.setStockCount(productStockCountField.getValue());
            productDto.setImagesId(new Long[]{Long.valueOf(productImagesIdField.getValue())});
            productDto.setIsAdult(Boolean.valueOf(productIsAdultField.getValue()));
            productDto.setCode(productCodeField.getValue());
            productDto.setWeight(Long.valueOf(productWeightField.getValue()));
            productDto.setPrice(productPriceField.getValue());
            productDto.setRating(Byte.valueOf(String.valueOf(productRatingField.getValue())));

            ProductDto savedProduct = productClient.create(productDto).getBody();

            dataSource.add(savedProduct);

            productNameField.clear();
            productDescriptionField.clear();
            productStockCountField.clear();
            productImagesIdField.clear();
            productIsAdultField.clear();
            productCodeField.clear();
            productWeightField.clear();
            productPriceField.clear();
            productRatingField.clear();

            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }
}