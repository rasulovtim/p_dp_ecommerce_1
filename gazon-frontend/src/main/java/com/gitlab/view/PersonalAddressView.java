package com.gitlab.view;

import com.gitlab.clients.PersonalAddressClient;
import com.gitlab.dto.PersonalAddressDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Route(value = "personal_address", layout = MainLayout.class)
public class PersonalAddressView extends VerticalLayout {
    private final Grid<PersonalAddressDto> grid = new Grid<>(PersonalAddressDto.class, false);
    private final Editor<PersonalAddressDto> editor = grid.getEditor();
    private final PersonalAddressClient personalAddressClient;
    private final List<PersonalAddressDto> dataSource;

    public PersonalAddressView(PersonalAddressClient personalAddressClient) {
        this.personalAddressClient = personalAddressClient;
        this.dataSource = new ArrayList<>(Objects.requireNonNull(personalAddressClient.getAll().getBody()));

        ValidationMessage idValidationMessage = new ValidationMessage();
        ValidationMessage personalAddressTextValidationMessage = new ValidationMessage();

        Binder<PersonalAddressDto> binder = createBinder();
        createIdField(binder, idValidationMessage, createIdColumn());
        createPersonalAddressField(binder, personalAddressTextValidationMessage, createAddressColumn());
        createPersonalAddressDirectionsField(binder, personalAddressTextValidationMessage, createDirectionsColumn());
        createPersonalAddressApartmentField(binder, personalAddressTextValidationMessage, createApartmentColumn());
        createPersonalAddressFloorField(binder, personalAddressTextValidationMessage, createFloorColumn());
        createPersonalAddressEntranceField(binder, personalAddressTextValidationMessage, createEntranceColumn());
        createPersonalAddressDoorCodeField(binder, personalAddressTextValidationMessage, createDoorCodeColumn());
        createPersonalAddressPostCodeField(binder, personalAddressTextValidationMessage, createPostCodeColumn());

        Grid.Column<PersonalAddressDto> updateColumn = createEditColumn();

        Button updateButton = new Button("Update", e -> editor.save());
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel());

        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(updateButton, cancelButton);
        actions.setPadding(false);
        updateColumn.setEditorComponent(actions);

        createDeleteColumn();
        addEditorListeners();
        grid.setItems(dataSource);

        Div contentContainer = new Div();
        contentContainer.setSizeFull();
        Tabs tabs = createTabs(contentContainer);

        add(tabs, contentContainer, idValidationMessage, personalAddressTextValidationMessage);
    }

    private Grid.Column<PersonalAddressDto> createIdColumn() {
        return grid.addColumn(PersonalAddressDto -> PersonalAddressDto.getId().intValue()).setHeader("Id").setWidth("50px").setFlexGrow(0);
    }

    private Grid.Column<PersonalAddressDto> createAddressColumn() {
        return grid.addColumn(PersonalAddressDto::getAddress).setHeader("Address").setWidth("350px").setFlexGrow(0);
    }

    private Grid.Column<PersonalAddressDto> createDirectionsColumn() {
        return grid.addColumn(PersonalAddressDto::getDirections).setHeader("Directions").setWidth("350px").setFlexGrow(0);
    }

    private Grid.Column<PersonalAddressDto> createApartmentColumn() {
        return grid.addColumn(PersonalAddressDto::getApartment).setHeader("Apartment").setWidth("110px").setFlexGrow(0);
    }

    private Grid.Column<PersonalAddressDto> createFloorColumn() {
        return grid.addColumn(PersonalAddressDto::getFloor).setHeader("Floor").setWidth("100px").setFlexGrow(0);
    }

    private Grid.Column<PersonalAddressDto> createEntranceColumn() {
        return grid.addColumn(PersonalAddressDto::getEntrance).setHeader("Entrance").setWidth("100px").setFlexGrow(0);
    }

    private Grid.Column<PersonalAddressDto> createDoorCodeColumn() {
        return grid.addColumn(PersonalAddressDto::getDoorCode).setHeader("Door code").setWidth("100px").setFlexGrow(0);
    }

    private Grid.Column<PersonalAddressDto> createPostCodeColumn() {
        return grid.addColumn(PersonalAddressDto::getPostCode).setHeader("Post code").setWidth("100px").setFlexGrow(0);
    }

    private Tabs createTabs(Div contentContainer) {
        Tabs tabs = new Tabs();

        Tab tableTab = new Tab("Personal Address table");
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
        Tab createTab = new Tab("Create personal address");
        TextField personalAddressField = new TextField("Address Text");
        TextField personalAddressDirectionsField = new TextField("Directions Text");
        TextField personalAddressApartmentField = new TextField("Apartment Text");
        TextField personalAddressFloorField = new TextField("Floor Text");
        TextField personalAddressEntranceField = new TextField("Entrance Text");
        TextField personalAddressDoorCodeField = new TextField("Door code Text");
        TextField personalAddressPostCodeField = new TextField("Post code Text");

        Button createButton = new Button("Create");
        formLayout.add(personalAddressField, createButton);
        formLayout.add(personalAddressDirectionsField, createButton);
        formLayout.add(personalAddressApartmentField, createButton);
        formLayout.add(personalAddressFloorField, createButton);
        formLayout.add(personalAddressEntranceField, createButton);
        formLayout.add(personalAddressDoorCodeField, createButton);
        formLayout.add(personalAddressPostCodeField, createButton);

        createButton.addClickListener(event -> {
            PersonalAddressDto personalAddressDto = new PersonalAddressDto();
            personalAddressDto.setAddress(personalAddressField.getValue());
            personalAddressDto.setDirections(personalAddressDirectionsField.getValue());
            personalAddressDto.setApartment(personalAddressApartmentField.getValue());
            personalAddressDto.setFloor(personalAddressFloorField.getValue());
            personalAddressDto.setEntrance(personalAddressEntranceField.getValue());
            personalAddressDto.setDoorCode(personalAddressDoorCodeField.getValue());
            personalAddressDto.setPostCode(personalAddressPostCodeField.getValue());

            PersonalAddressDto savePersonalAddress = personalAddressClient.create(personalAddressDto).getBody();
            dataSource.add(savePersonalAddress);

            personalAddressField.clear();
            personalAddressDirectionsField.clear();
            personalAddressApartmentField.clear();
            personalAddressFloorField.clear();
            personalAddressEntranceField.clear();
            personalAddressDoorCodeField.clear();
            personalAddressPostCodeField.clear();

            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }

    private void addEditorListeners() {
        editor.addSaveListener(e -> {
            personalAddressClient.update(e.getItem().getId(), e.getItem());
            grid.getDataProvider().refreshAll();
        });
    }

    private Grid.Column<PersonalAddressDto> createEditColumn() {
        return grid.addComponentColumn(personalAddress -> {
            Button updateButton = new Button("Update");
            updateButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(personalAddress);
            });
            return updateButton;
        }).setWidth("120px").setFlexGrow(0);
    }

    private void createDeleteColumn() {
        grid.addComponentColumn(personalAddress -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {

                    ListDataProvider<PersonalAddressDto> dataProvider = (ListDataProvider<PersonalAddressDto>) grid.getDataProvider();
                    personalAddressClient.delete(personalAddress.getId());
                    dataProvider.getItems().remove(personalAddress);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("120px").setFlexGrow(0);
    }


    private Binder<PersonalAddressDto> createBinder() {
        Binder<PersonalAddressDto> binder = new Binder<>(PersonalAddressDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);
        return binder;
    }

    private void createIdField(Binder<PersonalAddressDto> binder, ValidationMessage idValidationMessage, Grid.Column<PersonalAddressDto> idColumn) {
        IntegerField idField = new IntegerField();
        idField.setWidthFull();
        binder.forField(idField)
                .asRequired("Id must not be empty")
                .withStatusLabel(idValidationMessage)
                .bind(personalAddressDto -> personalAddressDto.getId().intValue(),
                        (personalAddressDto, integer) -> personalAddressDto.setId(integer.longValue()));
        idColumn.setEditorComponent(idField);
    }

    private void createPersonalAddressField(Binder<PersonalAddressDto> binder, ValidationMessage personalAddressTextValidationMessage, Grid.Column<PersonalAddressDto> addressTextColumn) {
        TextField personalAddressTextField = new TextField();
        personalAddressTextField.setWidthFull();
        binder.forField(personalAddressTextField).asRequired("Address text must not be empty")
                .withStatusLabel(personalAddressTextValidationMessage)
                .bind(PersonalAddressDto::getAddress, PersonalAddressDto::setAddress);
        addressTextColumn.setEditorComponent(personalAddressTextField);
    }

    private void createPersonalAddressDirectionsField(Binder<PersonalAddressDto> binder, ValidationMessage directionsTextValidationMessage, Grid.Column<PersonalAddressDto> directionsTextColumn) {
        TextField personalAddresTextField = new TextField();
        personalAddresTextField.setWidthFull();
        binder.forField(personalAddresTextField).asRequired("Directions text must not be empty")
                .withStatusLabel(directionsTextValidationMessage)
                .bind(PersonalAddressDto::getDirections, PersonalAddressDto::setDirections);
        directionsTextColumn.setEditorComponent(personalAddresTextField);
    }

    private void createPersonalAddressApartmentField(Binder<PersonalAddressDto> binder, ValidationMessage apartmentTextValidationMessage, Grid.Column<PersonalAddressDto> apartmentTextColumn) {
        TextField apartmentTextField = new TextField();
        apartmentTextField.setWidthFull();
        binder.forField(apartmentTextField).asRequired("Apartment text must not be empty")
                .withStatusLabel(apartmentTextValidationMessage)
                .bind(PersonalAddressDto::getDirections, PersonalAddressDto::setApartment);
        apartmentTextColumn.setEditorComponent(apartmentTextField);
    }

    private void createPersonalAddressFloorField(Binder<PersonalAddressDto> binder, ValidationMessage floorTextValidationMessage, Grid.Column<PersonalAddressDto> floorTextColumn) {
        TextField floorTextField = new TextField();
        floorTextField.setWidthFull();
        binder.forField(floorTextField).asRequired("Floor text must not be empty")
                .withStatusLabel(floorTextValidationMessage)
                .bind(PersonalAddressDto::getFloor, PersonalAddressDto::setFloor);
        floorTextColumn.setEditorComponent(floorTextField);
    }

    private void createPersonalAddressEntranceField(Binder<PersonalAddressDto> binder, ValidationMessage entranceTextValidationMessage, Grid.Column<PersonalAddressDto> entranceTextColumn) {
        TextField entranceTextField = new TextField();
        entranceTextField.setWidthFull();
        binder.forField(entranceTextField).asRequired("Floor text must not be empty")
                .withStatusLabel(entranceTextValidationMessage)
                .bind(PersonalAddressDto::getEntrance, PersonalAddressDto::setEntrance);
        entranceTextColumn.setEditorComponent(entranceTextField);
    }

    private void createPersonalAddressDoorCodeField(Binder<PersonalAddressDto> binder, ValidationMessage doorCodeTextValidationMessage, Grid.Column<PersonalAddressDto> doorCodeTextColumn) {
        TextField doorCodeTextField = new TextField();
        doorCodeTextField.setWidthFull();
        binder.forField(doorCodeTextField).asRequired("Door code text must not be empty")
                .withStatusLabel(doorCodeTextValidationMessage)
                .bind(PersonalAddressDto::getDoorCode, PersonalAddressDto::setDoorCode);
        doorCodeTextColumn.setEditorComponent(doorCodeTextField);
    }

    private void createPersonalAddressPostCodeField(Binder<PersonalAddressDto> binder, ValidationMessage postCodeTextValidationMessage, Grid.Column<PersonalAddressDto> postCodeTextColumn) {
        TextField postCodeTextField = new TextField();
        postCodeTextField.setWidthFull();
        binder.forField(postCodeTextField).asRequired("Post code text must not be empty")
                .withStatusLabel(postCodeTextValidationMessage)
                .bind(PersonalAddressDto::getPostCode, PersonalAddressDto::setPostCode);
        postCodeTextColumn.setEditorComponent(postCodeTextField);
    }
}
