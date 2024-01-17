package com.gitlab.view;

import com.gitlab.clients.BankCardClient;
import com.gitlab.dto.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
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

@Route(value = "bankcard", layout = MainLayout.class)
public class BankCardView extends VerticalLayout {

    private final Grid<BankCardDto> grid = new Grid<>(BankCardDto.class, false);
    private final Editor<BankCardDto> editor = grid.getEditor();
    private final BankCardClient bankCardClient;
    private final List<BankCardDto> dataSource;

    public BankCardView(BankCardClient bankCardClient) {
        this.bankCardClient = bankCardClient;
        this.dataSource = bankCardClient.getPage(null, null).getBody();

        ValidationMessage idValidationMessage = new ValidationMessage();
        ValidationMessage cardNumberValidationMessage = new ValidationMessage();
        ValidationMessage dueDateValidationMessage = new ValidationMessage();
        ValidationMessage securityCodeValidationMessage = new ValidationMessage();

        Grid.Column<BankCardDto> idColumn = createIdColumn();
        Grid.Column<BankCardDto> cardNumberColumn = createCardNumberColumn();
        Grid.Column<BankCardDto> dueDateColumn = createDueDateColumn();
        Grid.Column<BankCardDto> securityCodeColumn = createSecurityCodeColumn();

        Grid.Column<BankCardDto> updateColumn = createEditColumn();
        createDeleteColumn();

        Binder<BankCardDto> binder = createBinder();

        createIdField(binder, idValidationMessage, idColumn);
        createCardNumberField(binder, cardNumberValidationMessage, cardNumberColumn);
        createDueDateField(binder, dueDateValidationMessage, dueDateColumn);
        createSecurityCodeField(binder, securityCodeValidationMessage, securityCodeColumn);

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
                cardNumberValidationMessage,
                dueDateValidationMessage,
                securityCodeValidationMessage
        );
    }

    private Grid.Column<BankCardDto> createIdColumn() {
        return grid.addColumn(bankCardDto -> bankCardDto.getId().intValue())
                .setHeader("Id").setWidth("120px").setFlexGrow(0);
    }

    private Grid.Column<BankCardDto> createCardNumberColumn() {
        return grid.addColumn(BankCardDto::getCardNumber).setHeader("Card Number").setWidth("150px");
    }

    private Grid.Column<BankCardDto> createDueDateColumn() {
        return grid.addColumn(BankCardDto::getDueDate).setHeader("Due Date").setWidth("150px");
    }

    private Grid.Column<BankCardDto> createSecurityCodeColumn() {
        return grid.addColumn(BankCardDto::getSecurityCode).setHeader("Security Code").setWidth("150px");
    }

    private void createIdField(Binder<BankCardDto> binder,
                               ValidationMessage idValidationMessage,
                               Grid.Column<BankCardDto> idColumn) {
        IntegerField idField = new IntegerField();
        idField.setWidthFull();
        binder.forField(idField)
                .asRequired("Id must not be empty")
                .withStatusLabel(idValidationMessage)
                .bind(bankCardDto -> bankCardDto.getId().intValue(),
                        (bankCardDto, integer) -> bankCardDto.setId(integer.longValue()));
        idColumn.setEditorComponent(idField);
    }

    private void createCardNumberField(Binder<BankCardDto> binder,
                                       ValidationMessage userEmailValidationMessage,
                                       Grid.Column<BankCardDto> cardNumberColumn) {
        TextField cardNumberField = new TextField();
        cardNumberField.setWidthFull();
        binder.forField(cardNumberField)
                .asRequired("BankCard's cardNumber should have at least eight characters")
                .withStatusLabel(userEmailValidationMessage)
                .bind(BankCardDto::getCardNumber, BankCardDto::setCardNumber);
        cardNumberColumn.setEditorComponent(cardNumberField);
    }

    private void createDueDateField(Binder<BankCardDto> binder,
                                         ValidationMessage userPasswordValidationMessage,
                                         Grid.Column<BankCardDto> dueDateColumn) {
        DatePicker dueDateField = new DatePicker();
        dueDateField.setWidthFull();
        binder.forField(dueDateField)
                .asRequired("BankCard's dueDate should not be empty")
                .withStatusLabel(userPasswordValidationMessage)
                .bind(BankCardDto::getDueDate, BankCardDto::setDueDate);
        dueDateColumn.setEditorComponent(dueDateField);
    }

    private void createSecurityCodeField(Binder<BankCardDto> binder,
                                          ValidationMessage userFirstNameValidationMessage,
                                          Grid.Column<BankCardDto> securityCodeColumn) {
        IntegerField securityCodeField = new IntegerField();
        securityCodeField.setWidthFull();
        binder.forField(securityCodeField)
                .asRequired("BankCard's securityCode should not be empty")
                .withStatusLabel(userFirstNameValidationMessage)
                .bind(BankCardDto::getSecurityCode, BankCardDto::setSecurityCode);
        securityCodeColumn.setEditorComponent(securityCodeField);
    }

    private Binder<BankCardDto> createBinder() {
        Binder<BankCardDto> binder = new Binder<>(BankCardDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);
        return binder;
    }

    private void addEditorListeners() {
        editor.addSaveListener(e -> {
            bankCardClient.update(e.getItem().getId(), e.getItem());
            grid.getDataProvider().refreshAll();
        });
    }

    private void addTheme() {
        getThemeList().clear();
        getThemeList().add("spacing-s");
    }

    private Tabs createTabs(Div contentContainer) {
        Tabs tabs = new Tabs();

        Tab tableTab = new Tab("Bank Card table");
        FormLayout createCardLayout = new FormLayout();
        Tab createTab = createCreateTab(createCardLayout);

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
                contentContainer.add(createCardLayout);
                grid.getDataProvider().refreshAll();
            }
        });
        return tabs;
    }

    private Tab createCreateTab(FormLayout formLayout) {
        Tab createTab = new Tab("Create bank card");

        TextField bankCardNumberField = new TextField("Card number");
        bankCardNumberField.setPlaceholder("Length of BankCard's cardNumber should be between 8 and 19 positive digits");
        DatePicker bankCardDueDateField = new DatePicker("Due date");
        bankCardDueDateField.setPlaceholder("dd.mm.yyyy");
        IntegerField bankCardSecurityCodeField = new IntegerField("Security code");
        bankCardSecurityCodeField.setPlaceholder("Length of BankCard's securityCode should be between 3 and 4 positive digits");

        Button createButton = new Button("Create");
        formLayout.add(bankCardNumberField, bankCardDueDateField, bankCardSecurityCodeField, createButton);
        createButton.addClickListener(event -> {
            BankCardDto bankCardDto = new BankCardDto();
            bankCardDto.setCardNumber(bankCardNumberField.getValue());
            bankCardDto.setDueDate(bankCardDueDateField.getValue());
            bankCardDto.setSecurityCode(bankCardSecurityCodeField.getValue());
            BankCardDto savedBankCard = bankCardClient.create(bankCardDto).getBody();
            dataSource.add(savedBankCard);
            bankCardNumberField.clear();
            bankCardDueDateField.clear();
            bankCardSecurityCodeField.clear();
            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }

    private Grid.Column<BankCardDto> createEditColumn() {
        return grid.addComponentColumn(bankCard -> {
            Button updateButton = new Button("Update");
            updateButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(bankCard);
            });
            return updateButton;
        });
    }

    private Grid.Column<BankCardDto> createDeleteColumn() {
        return grid.addComponentColumn(bankCardDto -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<BankCardDto> dataProvider = (ListDataProvider<BankCardDto>) grid.getDataProvider();
                    bankCardClient.delete(bankCardDto.getId());
                    dataProvider.getItems().remove(bankCardDto);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

}