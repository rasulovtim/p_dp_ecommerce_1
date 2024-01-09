package com.gitlab.view;

import com.gitlab.clients.RoleClient;
import com.gitlab.dto.RoleDto;
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

import java.util.List;

@Route(value = "role", layout = MainLayout.class)
public class RoleView extends VerticalLayout {

    private final Grid<RoleDto> grid = new Grid<>(RoleDto.class, false);
    private final Editor<RoleDto> editor = grid.getEditor();
    private final RoleClient roleClient;
    private final List<RoleDto> dataSource;

    public RoleView(RoleClient roleClient) {
        this.roleClient = roleClient;
        this.dataSource = roleClient.getPage(null, null).getBody();
        ValidationMessage idValidationMessage = new ValidationMessage();
        ValidationMessage roleTextValidationMessage = new ValidationMessage();

        Grid.Column<RoleDto> idColumn = createIdColumn();
        Grid.Column<RoleDto> nameColumn = createNameColumn();
        Grid.Column<RoleDto> updateColumn = createEditColumn();
        createDeleteColumn();

        Binder<RoleDto> binder = createBinder();

        createIdField(binder, idValidationMessage, idColumn);
        createNameField(binder, roleTextValidationMessage, nameColumn);

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

        add(tabs, contentContainer, idValidationMessage, roleTextValidationMessage);
    }

    private Grid.Column<RoleDto> createIdColumn() {
        return grid.addColumn(roleDto -> roleDto.getId().intValue())
                .setHeader("Id").setWidth("120px").setFlexGrow(0);
    }

    private Grid.Column<RoleDto> createNameColumn() {
        return grid.addColumn(RoleDto::getRoleName).setHeader("Role").setWidth("650px");
    }

    private Grid.Column<RoleDto> createEditColumn() {
        return grid.addComponentColumn(role -> {
            Button updateButton = new Button("Update");
            updateButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(role);
            });
            return updateButton;
        });
    }

    private Grid.Column<RoleDto> createDeleteColumn() {
        return grid.addComponentColumn(roleDto -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<RoleDto> dataProvider = (ListDataProvider<RoleDto>) grid.getDataProvider();
                    roleClient.delete(roleDto.getId());
                    dataProvider.getItems().remove(roleDto);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

    private Binder<RoleDto> createBinder() {
        Binder<RoleDto> binder = new Binder<>(RoleDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);
        return binder;
    }

    private void createIdField(Binder<RoleDto> binder, ValidationMessage idValidationMessage, Grid.Column<RoleDto> idColumn) {
        IntegerField idField = new IntegerField();
        idField.setWidthFull();
        binder.forField(idField)
                .asRequired("Id must not be empty")
                .withStatusLabel(idValidationMessage)
                .bind(roleDto -> roleDto.getId().intValue(),
                        (roleDto, integer) -> roleDto.setId(integer.longValue()));
        idColumn.setEditorComponent(idField);
    }

    private void createNameField(Binder<RoleDto> binder,
                                 ValidationMessage roleTextValidationMessage,
                                 Grid.Column<RoleDto> nameColumn) {
        TextField nameField = new TextField();
        nameField.setWidthFull();
        binder.forField(nameField)
                .asRequired("Role name must not be empty")
                .withStatusLabel(roleTextValidationMessage)
                .bind(RoleDto::getRoleName, RoleDto::setRoleName);
        nameColumn.setEditorComponent(nameField);
    }

    private void addEditorListeners() {
        editor.addSaveListener(e -> {

            roleClient.update(e.getItem().getId(), e.getItem());
            grid.getDataProvider().refreshAll();
        });
    }

    private void addTheme() {
        getThemeList().clear();
        getThemeList().add("spacing-s");
    }

    private Tabs createTabs(Div contentContainer) {
        Tabs tabs = new Tabs();

        Tab tableTab = new Tab("Roles table");
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
            }
        });
        return tabs;
    }

    private Tab createCreateTab(FormLayout formLayout) {
        Tab createTab = new Tab("Create role");
        TextField roleNameField = new TextField("Role Name");
        Button createButton = new Button("Create");
        formLayout.add(roleNameField, createButton);
        createButton.addClickListener(event -> {
            RoleDto roleDto = new RoleDto();
            roleDto.setRoleName(roleNameField.getValue());
            RoleDto savedRoleDto = roleClient.create(roleDto).getBody();
            dataSource.add(savedRoleDto);
            roleNameField.clear();
            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }
}
