package com.gitlab.view;

import com.gitlab.clients.WorkingScheduleClient;
import com.gitlab.dto.WorkingScheduleDto;
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
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;


@Route(value = "working-schedule", layout = MainLayout.class)
public class WorkingScheduleView extends VerticalLayout {

    private final Grid<WorkingScheduleDto> grid = new Grid<>(WorkingScheduleDto.class, false);
    private final Editor<WorkingScheduleDto> editor = grid.getEditor();
    private final WorkingScheduleClient workingScheduleClient;
    private final List<WorkingScheduleDto> dataSource;

    public WorkingScheduleView(WorkingScheduleClient workingScheduleClient) {
        this.workingScheduleClient = workingScheduleClient;
        int page = 0;
        int size = 100;
        this.dataSource = workingScheduleClient.getPage(page, size).getBody().stream().collect(Collectors.toList());
        var idValidationMessage = new ValidationMessage();
        var dayOfWeekValidationMessage = new ValidationMessage();
        var fromValidationMessage = new ValidationMessage();
        var toValidationMessage = new ValidationMessage();

        Grid.Column<WorkingScheduleDto> idColumn = createIdColumn();
        Grid.Column<WorkingScheduleDto> dayOfWeekColumn = createDayOfWeekColumn();
        Grid.Column<WorkingScheduleDto> fromColumn = createFromColumn();
        Grid.Column<WorkingScheduleDto> toColumn = createToColumn();
        Grid.Column<WorkingScheduleDto> updateColumn = createEditColumn();
        createDeleteColumn();

        Binder<WorkingScheduleDto> binder = createBinder();

        createIdField(binder, idValidationMessage, idColumn);
        createDayOfWeekField(binder, dayOfWeekValidationMessage, dayOfWeekColumn);
        createFromField(binder, fromValidationMessage, fromColumn);
        createToField(binder, toValidationMessage, toColumn);


        var updateButton = new Button("Update", e -> editor.save());
        var cancelButton = new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        var actions = new HorizontalLayout(updateButton, cancelButton);
        actions.setPadding(false);
        updateColumn.setEditorComponent(actions);

        addEditorListeners();
        grid.setItems(dataSource);
        addTheme();

        var contentContainer = new Div();
        contentContainer.setSizeFull();
        Tabs tabs = createTabs(contentContainer);

        add(tabs, contentContainer, idValidationMessage, dayOfWeekValidationMessage, fromValidationMessage, toValidationMessage);
    }

    private Grid.Column<WorkingScheduleDto> createIdColumn() {
        return grid.addColumn(workingScheduleDto -> workingScheduleDto.getId().intValue())
                .setHeader("Id").setWidth("120px").setFlexGrow(0);

  }

    private Grid.Column<WorkingScheduleDto> createDayOfWeekColumn() {
        return grid.addColumn(WorkingScheduleDto::getDayOfWeek).setHeader("Day of week").setWidth("500px");
    }

    private Grid.Column<WorkingScheduleDto> createFromColumn() {
        return grid.addColumn(WorkingScheduleDto::getFrom).setHeader("From").setWidth("150px");
    }

    private Grid.Column<WorkingScheduleDto> createToColumn() {
        return grid.addColumn(WorkingScheduleDto::getTo).setHeader("To").setWidth("150px");
    }

    private Grid.Column<WorkingScheduleDto> createEditColumn() {
        return grid.addComponentColumn(workingSchedule -> {
            var updateButton = new Button("Update");
            updateButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(workingSchedule);
            });
            return updateButton;
        });
    }

    private Grid.Column<WorkingScheduleDto> createDeleteColumn() {
        return grid.addComponentColumn(workingSchedule -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<WorkingScheduleDto> dataProvider = (ListDataProvider<WorkingScheduleDto>) grid.getDataProvider();
                    workingScheduleClient.delete(workingSchedule.getId());
                    dataProvider.getItems().remove(workingSchedule);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

    private Binder<WorkingScheduleDto> createBinder() {
        Binder<WorkingScheduleDto> binder = new Binder<>(WorkingScheduleDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);
        return binder;
    }

    private void createIdField(Binder<WorkingScheduleDto> binder,
                               ValidationMessage idValidationMessage,
                               Grid.Column<WorkingScheduleDto> idColumn) {
        var idField = new IntegerField();
        idField.setWidthFull();
        binder.forField(idField)
                .asRequired("Id must not be empty")
                .withStatusLabel(idValidationMessage)
                .bind(workingScheduleDto  -> workingScheduleDto.getId().intValue(),
                        (workingScheduleDto, integer) -> workingScheduleDto.setId(integer.longValue()));
        idColumn.setEditorComponent(idField);
    }
    private void createDayOfWeekField(Binder<WorkingScheduleDto> binder,
                               ValidationMessage dayOfWeekValidationMessage,
                               Grid.Column<WorkingScheduleDto> dayOfWeekColumn) {
        TextField dayOfWeekField = new TextField();
        dayOfWeekField.setWidthFull();
        binder.forField(dayOfWeekField)
                .asRequired("Day of week must not be empty")
                .withStatusLabel(dayOfWeekValidationMessage)
                .bind(workingScheduleDto -> workingScheduleDto.getDayOfWeek().toString(),
                        (workingScheduleDto, s) -> workingScheduleDto.setDayOfWeek(DayOfWeek.valueOf(s)));
        dayOfWeekColumn.setEditorComponent(dayOfWeekField);
    }
    private void createFromField(Binder<WorkingScheduleDto> binder,
                                      ValidationMessage fromValidationMessage,
                                      Grid.Column<WorkingScheduleDto> fromColumn) {
        var fromField = new TimePicker();
        fromField.setWidthFull();
        binder.forField(fromField)
                .asRequired("From must not be empty")
                .withStatusLabel(fromValidationMessage)
                .bind(WorkingScheduleDto::getFrom, WorkingScheduleDto::setFrom);
        fromColumn.setEditorComponent(fromField);
    }
    private void createToField(Binder<WorkingScheduleDto> binder,
                                 ValidationMessage toValidationMessage,
                                 Grid.Column<WorkingScheduleDto> toColumn) {
        var toField = new TimePicker();
        toField.setWidthFull();
        binder.forField(toField)
                .asRequired("To must not be empty")
                .withStatusLabel(toValidationMessage)
                .bind(WorkingScheduleDto::getTo, WorkingScheduleDto::setTo);
        toColumn.setEditorComponent(toField);
    }

    private void addEditorListeners() {
        editor.addSaveListener(e -> {
            workingScheduleClient.update(e.getItem().getId(), e.getItem());
            grid.getDataProvider().refreshAll();
        });
    }

    private void addTheme() {
        getThemeList().clear();
        getThemeList().add("spacing-s");
    }

    private Tabs createTabs(Div contentContainer) {
        var tabs = new Tabs();

        var tableTab = new Tab("Working Schedule table");
        var formLayout = new FormLayout();
        var createTab = createCreateTab(formLayout);

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
        var createTab = new Tab("Create working schedule");
        var dayOfWeekField = new TextField("Day of week");
        var fromField = new TimePicker("From");
        var toField = new TimePicker("To");
        var createButton = new Button("Create");
        formLayout.add(dayOfWeekField, createButton);
        formLayout.add(fromField, createButton);
        formLayout.add(toField, createButton);
                createButton.addClickListener(event -> {
            var workingScheduleDto = new WorkingScheduleDto();
            workingScheduleDto.setDayOfWeek(DayOfWeek.valueOf(dayOfWeekField.getValue()));
            workingScheduleDto.setFrom(fromField.getValue());
            workingScheduleDto.setTo(toField.getValue());
            WorkingScheduleDto savedWorkingSchedule = workingScheduleClient.create(workingScheduleDto).getBody();
            dataSource.add(savedWorkingSchedule);
            dayOfWeekField.clear();
            fromField.clear();
            toField.clear();
            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }

}