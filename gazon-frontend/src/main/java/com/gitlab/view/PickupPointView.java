package com.gitlab.view;

import com.gitlab.clients.PickupPointClient;
import com.gitlab.dto.PickupPointDto;
import com.gitlab.model.PickupPoint;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route(value = "pickup_point", layout = MainLayout.class)
public class PickupPointView extends VerticalLayout {
    private final Grid<PickupPointDto> grid = new Grid<>(PickupPointDto.class, false);
    private final Editor<PickupPointDto> editor = grid.getEditor();
    private final PickupPointClient pickupPointClient;
    private final List<PickupPointDto> dataSource;

    public PickupPointView(PickupPointClient pickupPointClient) {
        this.pickupPointClient = pickupPointClient;
        this.dataSource = pickupPointClient.getAll().getBody();

        ValidationMessage idValidationMessage = new ValidationMessage();
        ValidationMessage pickupPointAddressValidationMessage = new ValidationMessage();
        ValidationMessage pickupPointDirectionsValidationMessage = new ValidationMessage();
        ValidationMessage pickupPointShelfLifeDaysValidationMessage = new ValidationMessage();
        ValidationMessage pickupPointFeaturesValidationMessage = new ValidationMessage();

        Grid.Column<PickupPointDto> idColumn = createIdColumn();
        Grid.Column<PickupPointDto> addressColumn = createPickupPointAddressColumn();
        Grid.Column<PickupPointDto> directionsColumn = createPickupPointDirectionsColumn();
        Grid.Column<PickupPointDto> shelfLifeDaysColumn = createPickupPointShelfLifeDaysColumn();
        Grid.Column<PickupPointDto> pickupPointFeaturesColumn = createPickupPointFeaturesColumn();
        Grid.Column<PickupPointDto> updateColumn = createEditColumn();
        createDeleteColumn();

        Binder<PickupPointDto> binder = createBinder();

        createIdField(binder, idValidationMessage, idColumn);
        createAddressField(binder, pickupPointAddressValidationMessage, addressColumn);
        createDirectionsField(binder, pickupPointDirectionsValidationMessage, directionsColumn);
        createShelfLifeDaysField(binder, pickupPointShelfLifeDaysValidationMessage, shelfLifeDaysColumn);
        createPickupPointFeaturesField(binder, pickupPointFeaturesValidationMessage, pickupPointFeaturesColumn);

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
                pickupPointAddressValidationMessage,
                pickupPointDirectionsValidationMessage,
                pickupPointShelfLifeDaysValidationMessage,
                pickupPointFeaturesValidationMessage
        );
    }

    private Grid.Column<PickupPointDto> createIdColumn() {
        return grid.addColumn(pickupPointDto -> pickupPointDto.getId().intValue())
                .setHeader("Id").setWidth("50px").setFlexGrow(0);
    }

    private Grid.Column<PickupPointDto> createPickupPointAddressColumn() {
        return grid.addColumn(PickupPointDto::getAddress).setHeader("Address").setWidth("110px");
    }

    private Grid.Column<PickupPointDto> createPickupPointDirectionsColumn() {
        return grid.addColumn(PickupPointDto::getDirections).setHeader("Directions").setWidth("110px");
    }

    private Grid.Column<PickupPointDto> createPickupPointShelfLifeDaysColumn() {
        return grid.addColumn(PickupPointDto::getShelfLifeDays).setHeader("Shelf Life Days").setWidth("110px");
    }

    private Grid.Column<PickupPointDto> createPickupPointFeaturesColumn() {
        return grid.addColumn(dto -> {
            Set<PickupPoint.PickupPointFeatures> features = dto.getPickupPointFeatures();
            return features.stream()
                    .map(PickupPoint.PickupPointFeatures::getPickupPointFeatureInRussian)
                    .collect(Collectors.joining(", "));
        }).setHeader("Pickup Point Features").setWidth("110px");
    }

    private Grid.Column<PickupPointDto> createEditColumn() {
        return grid.addComponentColumn(passport -> {
            Button updateButton = new Button("Update");
            updateButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(passport);
            });
            return updateButton;
        });
    }

    private Grid.Column<PickupPointDto> createDeleteColumn() {
        return grid.addComponentColumn(pickupPointDto -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<PickupPointDto> dataProvider = (ListDataProvider<PickupPointDto>) grid.getDataProvider();
                    pickupPointClient.delete(pickupPointDto.getId());
                    dataProvider.getItems().remove(pickupPointDto);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

    private Binder<PickupPointDto> createBinder() {
        Binder<PickupPointDto> binder = new Binder<>(PickupPointDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);
        return binder;
    }
    private void createIdField(Binder<PickupPointDto> binder,
                               ValidationMessage idValidationMessage,
                               Grid.Column<PickupPointDto> idColumn) {
        IntegerField idField = new IntegerField();
        idField.setWidthFull();
        binder.forField(idField)
                .asRequired("Id must not be empty")
                .withStatusLabel(idValidationMessage)
                .bind(pickupPointDto -> pickupPointDto.getId().intValue(),
                        (pickupPointDto, integer) -> pickupPointDto.setId(integer.longValue()));
        idColumn.setEditorComponent(idField);
    }

    private void createAddressField(Binder<PickupPointDto> binder,
                                      ValidationMessage pickupPointAddressValidationMessage,
                                      Grid.Column<PickupPointDto> pickupPointAddressColumn) {
        TextField pickupPointTextField = new TextField();
        pickupPointTextField.setWidthFull();
        binder.forField(pickupPointTextField).withValidator(new StringLengthValidator(
                        "Pickup Point's address should have the name of the city, street and house number",
                        10, 150))
                .asRequired("Pickup Point's address shouldn't be empty")
                .withStatusLabel(pickupPointAddressValidationMessage)
                .bind(PickupPointDto::getAddress, PickupPointDto::setAddress);
        pickupPointAddressColumn.setEditorComponent(pickupPointTextField);

    }

    private void createDirectionsField(Binder<PickupPointDto> binder,
                                     ValidationMessage pickupPointDirectionsValidationMessage,
                                     Grid.Column<PickupPointDto> pickupPointDirectionsColumn) {
        TextField pickupPointTextField = new TextField();
        pickupPointTextField.setWidthFull();
        binder.forField(pickupPointTextField)
                .asRequired("Pickup Point's directions shouldn't be empty")
                .withStatusLabel(pickupPointDirectionsValidationMessage)
                .bind(PickupPointDto::getDirections, PickupPointDto::setDirections);
        pickupPointDirectionsColumn.setEditorComponent(pickupPointTextField);
    }

    private void createShelfLifeDaysField(Binder<PickupPointDto> binder,
                                     ValidationMessage pickupPointShelfLifeDaysValidationMessage,
                                     Grid.Column<PickupPointDto> shelfLifeDaysColumn) {
        IntegerField pickupPointIntegerField = new IntegerField();
        pickupPointIntegerField.setWidthFull();
        binder.forField(pickupPointIntegerField)
                .withConverter(Integer::byteValue, Byte::intValue)
                .withValidator(field -> (field >= 1 && field <= 30),
                        "Pickup Point's shelf life days should be between 1 to 30 days")
                .withStatusLabel(pickupPointShelfLifeDaysValidationMessage)
                .bind(PickupPointDto::getShelfLifeDays, PickupPointDto::setShelfLifeDays);
        shelfLifeDaysColumn.setEditorComponent(pickupPointIntegerField);
    }

    private void createPickupPointFeaturesField(Binder<PickupPointDto> binder,
                                        ValidationMessage pickupPointFeaturesValidationMessage,
                                        Grid.Column<PickupPointDto> passportCitizenshipColumn) {
        MultiSelectComboBox<PickupPoint.PickupPointFeatures> pickupPointFeaturesSelect = new MultiSelectComboBox<>();
        pickupPointFeaturesSelect.setItems(Arrays.asList(PickupPoint.PickupPointFeatures.values()));
        pickupPointFeaturesSelect.setItemLabelGenerator(PickupPoint.PickupPointFeatures::getPickupPointFeatureInRussian);
        pickupPointFeaturesSelect.setWidthFull();
        binder.forField(pickupPointFeaturesSelect)
                .asRequired("Pickup Point's features shouldn't be empty")
                .withStatusLabel(pickupPointFeaturesValidationMessage)
                .bind(PickupPointDto::getPickupPointFeatures, PickupPointDto::setPickupPointFeatures);
        passportCitizenshipColumn.setEditorComponent(pickupPointFeaturesSelect);

        passportCitizenshipColumn.setEditorComponent(pickupPointFeaturesSelect);
    }
    private void addEditorListeners() {
        editor.addSaveListener(e -> {
            pickupPointClient.update(e.getItem().getId(), e.getItem());
            grid.getDataProvider().refreshAll();
        });
    }

    private void addTheme() {
        getThemeList().clear();
        getThemeList().add("spacing-s");
    }

    private Tabs createTabs(Div contentContainer) {
        Tabs tabs = new Tabs();

        Tab tableTab = new Tab("Pickup Point table");
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
        Tab createTab = new Tab("Create pickup point");
        TextField pickupPointAddressField = new TextField("Address");
        pickupPointAddressField.setPlaceholder("Address can contain only letters");
        TextField pickupPointDescriptionsField = new TextField("Descriptions");
        pickupPointDescriptionsField.setPlaceholder("Description can contain only letters");
        IntegerField pickupPointShelfLifeDaysField = new IntegerField("Shelf Life Days");
        pickupPointShelfLifeDaysField.setPlaceholder("Shelf Life Days can contain only integers");

        MultiSelectComboBox<PickupPoint.PickupPointFeatures> pickupPointFeaturesSelect = new MultiSelectComboBox<>("Pickup Point Features");
        pickupPointFeaturesSelect.setItems(Arrays.asList(PickupPoint.PickupPointFeatures.values()));
        pickupPointFeaturesSelect.setItemLabelGenerator(PickupPoint.PickupPointFeatures::getPickupPointFeatureInRussian);
        Button createButton = new Button("Create");
        formLayout.add(pickupPointAddressField, pickupPointDescriptionsField,
                pickupPointShelfLifeDaysField, pickupPointFeaturesSelect, createButton);
        createButton.addClickListener(event -> {
            PickupPointDto pickupPointDto = new PickupPointDto();
            pickupPointDto.setAddress(pickupPointAddressField.getValue());
            pickupPointDto.setDirections(pickupPointDescriptionsField.getValue());
            pickupPointDto.setShelfLifeDays(pickupPointShelfLifeDaysField.getValue().byteValue());
            pickupPointDto.setPickupPointFeatures(pickupPointFeaturesSelect.getValue());

            PickupPointDto savedpickupPointDto = pickupPointClient.create(pickupPointDto).getBody();
            dataSource.add(savedpickupPointDto);
            pickupPointDescriptionsField.clear();
            pickupPointAddressField.clear();
            pickupPointShelfLifeDaysField.clear();
            pickupPointFeaturesSelect.clear();
            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }
}