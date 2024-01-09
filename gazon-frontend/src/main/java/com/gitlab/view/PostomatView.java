package com.gitlab.view;

import com.gitlab.clients.PostomatClient;
import com.gitlab.dto.PostomatDto;
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


import java.util.*;

@Route(value = "postomat", layout = MainLayout.class)
public class PostomatView extends VerticalLayout {

    private final Grid<PostomatDto> grid = new Grid<>(PostomatDto.class, false);
    private final Editor<PostomatDto> editor = grid.getEditor();
    private final PostomatClient postomatClient;
    private final List<PostomatDto> dataSource;

    public PostomatView(PostomatClient postomatClient) {
        this.postomatClient = postomatClient;
        this.dataSource = new ArrayList<>(Objects.requireNonNull(postomatClient.getPage(null, null).getBody()));

        ValidationMessage idValidationMessage = new ValidationMessage();
        ValidationMessage postomatTextValidationMessage = new ValidationMessage();


        Binder<PostomatDto> binder = createBinder();
        createIdField(binder, idValidationMessage, createIdColumn());
        createPostomatAddressField(binder, postomatTextValidationMessage, createAddressColumn());
        createPostomatDirectionsField(binder, postomatTextValidationMessage, createDirectionsColumn());
        createPostomatShelfLifeDaysField(binder, postomatTextValidationMessage, createShelfLifeDaysColumn());


        Grid.Column<PostomatDto> updateColumn = createEditColumn();

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

        add(tabs, contentContainer, idValidationMessage, postomatTextValidationMessage);

    }


    private Grid.Column<PostomatDto> createIdColumn() {
        return grid.addColumn(postomatDto -> postomatDto.getId().intValue()).setHeader("Id").setWidth("120px").setFlexGrow(0);
    }


    private Grid.Column<PostomatDto> createAddressColumn() {
        return grid.addColumn(PostomatDto::getAddress).setHeader("Address").setWidth("500px").setFlexGrow(0);
    }

    private Grid.Column<PostomatDto> createDirectionsColumn() {
        return grid.addColumn(PostomatDto::getDirections).setHeader("Directions").setWidth("500px").setFlexGrow(0);
    }

    private Grid.Column<PostomatDto> createShelfLifeDaysColumn() {
        return grid.addColumn(postomatDto -> postomatDto.getShelfLifeDays().intValue()).setHeader("Shelf Life Days").setWidth("200px").setFlexGrow(0);
    }


    private Tabs createTabs(Div contentContainer) {
        Tabs tabs = new Tabs();

        Tab tableTab = new Tab("Postomat table");
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
        Tab createTab = new Tab("Create postomat");
        TextField postomatAddressField = new TextField("Address Text");
        TextField postomatDirectionsField = new TextField("Directions Text");
        TextField postomatShelfLifeDaysField = new TextField("Shelf Life Days Text");
        Button createButton = new Button("Create");
        formLayout.add(postomatAddressField, createButton);
        formLayout.add(postomatDirectionsField, createButton);
        formLayout.add(postomatShelfLifeDaysField, createButton);
        createButton.addClickListener(event -> {
            PostomatDto postomatDto = new PostomatDto();
            postomatDto.setAddress(postomatAddressField.getValue());
            postomatDto.setDirections(postomatDirectionsField.getValue());
            postomatDto.setShelfLifeDays(Byte.parseByte(postomatShelfLifeDaysField.getValue()));
            PostomatDto savedPostomat = postomatClient.create(postomatDto).getBody();
            dataSource.add(savedPostomat);
            postomatAddressField.clear();
            postomatDirectionsField.clear();
            postomatShelfLifeDaysField.clear();
            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }

    private void addEditorListeners() {
        editor.addSaveListener(e -> {
            postomatClient.update(e.getItem().getId(), e.getItem());
            grid.getDataProvider().refreshAll();
        });
    }

    private Grid.Column<PostomatDto> createEditColumn() {
        return grid.addComponentColumn(postomat -> {
            Button updateButton = new Button("Update");
            updateButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(postomat);
            });
            return updateButton;
        });
    }

    @SuppressWarnings("unchecked")
    private void createDeleteColumn() {
        grid.addComponentColumn(postomat -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<PostomatDto> dataProvider = (ListDataProvider<PostomatDto>) grid.getDataProvider();
                    postomatClient.delete(postomat.getId());
                    dataProvider.getItems().remove(postomat);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

    private void createIdField(Binder<PostomatDto> binder, ValidationMessage idValidationMessage, Grid.Column<PostomatDto> idColumn) {
        IntegerField idField = new IntegerField();
        idField.setWidthFull();
        binder.forField(idField)
                .asRequired("Id must not be empty")
                .withStatusLabel(idValidationMessage)
                .bind(postomatDto -> postomatDto.getId().intValue(),
                        (postomatDto, integer) -> postomatDto.setId(integer.longValue()));
        idColumn.setEditorComponent(idField);
    }

    private Binder<PostomatDto> createBinder() {
        Binder<PostomatDto> binder = new Binder<>(PostomatDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);
        return binder;
    }

    private void createPostomatAddressField(Binder<PostomatDto> binder, ValidationMessage postomatTextValidationMessage, Grid.Column<PostomatDto> addressTextColumn) {
        TextField postomatTextField = new TextField();
        postomatTextField.setWidthFull();
        binder.forField(postomatTextField).asRequired("Address text must not be empty")
                .withStatusLabel(postomatTextValidationMessage)
                .bind(PostomatDto::getAddress, PostomatDto::setAddress);
        addressTextColumn.setEditorComponent(postomatTextField);

    }

    private void createPostomatDirectionsField(Binder<PostomatDto> binder, ValidationMessage postomatTextValidationMessage, Grid.Column<PostomatDto> directionsTextColumn) {
        TextField postomatTextField = new TextField();
        postomatTextField.setWidthFull();
        binder.forField(postomatTextField).asRequired("Directions text must not be empty")
                .withStatusLabel(postomatTextValidationMessage)
                .bind(PostomatDto::getDirections, PostomatDto::setDirections);
        directionsTextColumn.setEditorComponent(postomatTextField);

    }

    private void createPostomatShelfLifeDaysField(Binder<PostomatDto> binder, ValidationMessage exampleTextValidationMessage, Grid.Column<PostomatDto> shelfLifeDaysTextColumn) {
        TextField exampleTextField = new TextField();
        exampleTextField.setWidthFull();
        binder.forField(exampleTextField).asRequired("Shelf Life Days text must not be empty")
                .withStatusLabel(exampleTextValidationMessage)
                .bind(postomatDto1 -> String.valueOf(postomatDto1.getShelfLifeDays()), (postomatDto, shelfLifeDays) -> postomatDto.setShelfLifeDays(Byte.valueOf(shelfLifeDays)));
        shelfLifeDaysTextColumn.setEditorComponent(exampleTextField);

    }

}