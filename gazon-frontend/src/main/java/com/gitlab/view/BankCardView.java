package com.gitlab.view;

import com.gitlab.clients.BankCardClient;
import com.gitlab.dto.*;
import com.gitlab.enums.Gender;
import com.gitlab.model.Passport;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route(value = "bankcard", layout = MainLayout.class)
public class BankCardView extends VerticalLayout {

    private final Grid<BankCardDto> grid = new Grid<>(BankCardDto.class, false);
    private final Editor<BankCardDto> editor = grid.getEditor();
    private final BankCardClient bankCardClient;
    private final List<BankCardDto> dataSource;

    public BankCardView(BankCardClient bankCardClient) {
        this.bankCardClient = bankCardClient;
        this.dataSource = bankCardClient.getAll().getBody();

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
                .asRequired("Card number must not be empty")
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
                .asRequired("Due date must not be empty")
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
                .asRequired("User first name must not be empty")
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
            userClient.update(e.getItem().getId(), e.getItem());
            grid.getDataProvider().refreshAll();
        });
    }

    private void addTheme() {
        getThemeList().clear();
        getThemeList().add("spacing-s");
    }

    private Tabs createTabs(Div contentContainer) {
        Tabs tabs = new Tabs();

        Tab tableTab = new Tab("Users table");
        FormLayout createUserLayout = new FormLayout();
        Tab createTab = createCreateTab(createUserLayout);

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
                contentContainer.add(createUserLayout);
            }
        });
        return tabs;
    }


    private Tab createCreateTab(FormLayout formLayout) {

        Tab createTab = new Tab("Create user");
        VerticalLayout verticalLayoutMain = new VerticalLayout();
        verticalLayoutMain.addClassName(LumoUtility.Gap.XSMALL);
        HorizontalLayout horizontalLayoutMain = new HorizontalLayout();
        horizontalLayoutMain.addClassName(LumoUtility.Gap.XSMALL);

        // Common fields
        TextField userFirstNameField = new TextField("First name");
        TextField userLastNameField = new TextField("Last name");
        DatePicker userBirthDateField = new DatePicker("Birthdate");

        // Main user`s layout
        TextField userEmailField = new TextField("Email");
        PasswordField userPasswordField = new PasswordField("Password");
        TextField userSecurityQuestionField = new TextField("Security question");
        TextField userAnswerQuestionField = new TextField("Answer question");
        Select<Gender> userGenders = new Select<>();
        userGenders.setLabel("Gender");
        userGenders.setItems(Arrays.asList(Gender.values()));
        userGenders.setItemLabelGenerator(Gender::getSex);
        TextField userPhoneNumberField = new TextField("Phone number");
        TextField userRoleField = new TextField("User role");


        // Passport DTO layer
        Select<Passport.Citizenship> passportCitizenshipField = new Select<>();
        passportCitizenshipField.setLabel("Citizenship");
        passportCitizenshipField.setItems(Arrays.asList(Passport.Citizenship.values()));
        passportCitizenshipField.setItemLabelGenerator(Passport.Citizenship::getCitizenshipInRussia);
        TextField passportPatronymField = new TextField("Your patronym");
        DatePicker passportIssueDateField = new DatePicker("Issue date");
        TextField passportNumberField = new TextField("Passport number");
        TextField passportIssuerField = new TextField("Passport issuer");
        TextField passportIssuerNumberField = new TextField("Passport issuer number");

        // Shipping Address DTO layer
        TextField shippingAddressField = new TextField("Shipping address");
        TextField shippingAddressApartmentField = new TextField("Apartment");
        TextField shippingAddressFloorField = new TextField("Floor");
        TextField shippingAddressEntranceField = new TextField("Entrance");

        // BankCard DTO layer
        TextField bankCardNumberField = new TextField("Card number");
        DatePicker bankCardDueDateField = new DatePicker("Due date");
        IntegerField bankCardSecurityCodeField = new IntegerField("Security code");

        // Button
        Button createButton = new Button("Create");

        H5 hUser = new H5("Fill user`s info:");
        H5 hPassport = new H5("Passport");
        H5 hCard = new H5("Bank card");
        H5 hApartment = new H5("Apartment info");
        H5 hAddress = new H5("Address");

        VerticalLayout verticalLayoutColumnI = new VerticalLayout();
        verticalLayoutColumnI.addClassName(LumoUtility.Gap.XSMALL);
        VerticalLayout verticalLayoutColumnII = new VerticalLayout();
        verticalLayoutColumnII.addClassName(LumoUtility.Gap.XSMALL);

        HorizontalLayout layoutRow1 = new HorizontalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        HorizontalLayout layoutRow5 = new HorizontalLayout();
        HorizontalLayout layoutRow6 = new HorizontalLayout();
        HorizontalLayout layoutRow7 = new HorizontalLayout();
        HorizontalLayout layoutRow8 = new HorizontalLayout();
        HorizontalLayout layoutRow9 = new HorizontalLayout();
        HorizontalLayout layoutRow10 = new HorizontalLayout();

        layoutRow1.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow1.setWidthFull();
        layoutRow1.setFlexGrow(1.0, userEmailField, userPasswordField, userSecurityQuestionField);
        layoutRow2.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow2.setWidthFull();
        layoutRow2.setFlexGrow(1.0, userFirstNameField, userLastNameField, userBirthDateField);
        layoutRow3.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow3.setWidthFull();
        layoutRow3.setFlexGrow(1.0, passportCitizenshipField, passportPatronymField, passportIssueDateField);
        layoutRow4.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow4.setWidthFull();
        layoutRow4.setFlexGrow(1.0, passportNumberField, passportIssuerField, passportIssuerNumberField);
        layoutRow5.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow5.setWidthFull();
        layoutRow5.setFlexGrow(1.0, shippingAddressField, shippingAddressApartmentField, shippingAddressFloorField);
        layoutRow6.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow6.setWidthFull();
        layoutRow6.setFlexGrow(1.0, bankCardSecurityCodeField);
        layoutRow7.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow7.setWidthFull();
        layoutRow7.setFlexGrow(1.0, userAnswerQuestionField, userPhoneNumberField);
        layoutRow8.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow8.setWidthFull();
        layoutRow8.setFlexGrow(1.0, userGenders, userRoleField);
        layoutRow9.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow9.setWidthFull();
        layoutRow9.setFlexGrow(1.0, bankCardNumberField, bankCardDueDateField);
        layoutRow10.addClassName(LumoUtility.Gap.XSMALL);
        layoutRow10.setWidthFull();
        layoutRow10.setFlexGrow(1.0, bankCardSecurityCodeField);

        layoutRow1.add(userEmailField, userPasswordField, userSecurityQuestionField);
        layoutRow2.add(userFirstNameField, userLastNameField, userBirthDateField);
        layoutRow3.add(passportCitizenshipField, passportPatronymField, passportIssueDateField);
        layoutRow4.add(passportNumberField, passportIssuerField, passportIssuerNumberField);
        layoutRow5.add(shippingAddressApartmentField, shippingAddressFloorField, shippingAddressEntranceField);
        verticalLayoutColumnI.add(layoutRow1, layoutRow2, hPassport, layoutRow3, layoutRow4, hApartment, layoutRow5);

        layoutRow7.add(userAnswerQuestionField, userPhoneNumberField);
        layoutRow8.add(userGenders, userRoleField);
        layoutRow9.add(bankCardNumberField, bankCardDueDateField);
        layoutRow10.add(bankCardSecurityCodeField);
        layoutRow6.add(shippingAddressField);
        verticalLayoutColumnII.add(layoutRow7, layoutRow8, hCard, layoutRow9, layoutRow10, hAddress, layoutRow6);

        horizontalLayoutMain.add(verticalLayoutColumnI, verticalLayoutColumnII);
        verticalLayoutMain.add(hUser, horizontalLayoutMain, createButton);

        formLayout.add(verticalLayoutMain);

        createButton.addClickListener(event -> {

            BankCardDto bankCardDto = new BankCardDto();
            PassportDto passportDto = new PassportDto();
            PersonalAddressDto personalAddressDto = new PersonalAddressDto();
            BankCardDto bankCardDto = new BankCardDto();

            // Fill passport DTO
            passportDto.setCitizenship(passportCitizenshipField.getValue());
            passportDto.setFirstName(userFirstNameField.getValue());
            passportDto.setLastName(userLastNameField.getValue());
            passportDto.setPatronym(passportPatronymField.getValue());
            passportDto.setBirthDate(userBirthDateField.getValue());
            passportDto.setIssueDate(passportIssueDateField.getValue());
            passportDto.setPassportNumber(passportNumberField.getValue());
            passportDto.setIssuer(passportIssuerField.getValue());
            passportDto.setIssuerNumber(passportIssuerNumberField.getValue());

            // Fill shipping address DTO
            personalAddressDto.setAddress(shippingAddressField.getValue());
            personalAddressDto.setApartment(shippingAddressApartmentField.getValue());
            personalAddressDto.setFloor(shippingAddressFloorField.getValue());
            personalAddressDto.setEntrance(shippingAddressEntranceField.getValue());

            // Fill bank card DTO
            bankCardDto.setCardNumber(bankCardNumberField.getValue());
            bankCardDto.setDueDate(bankCardDueDateField.getValue());
            bankCardDto.setSecurityCode(bankCardSecurityCodeField.getValue());

            // Fill USER DTO
            bankCardDto.setEmail(userEmailField.getValue());
            bankCardDto.setPassword(userPasswordField.getValue());
            bankCardDto.setSecurityQuestion(userSecurityQuestionField.getValue());
            bankCardDto.setAnswerQuestion(userAnswerQuestionField.getValue());
            bankCardDto.setFirstName(userFirstNameField.getValue());
            bankCardDto.setLastName(userLastNameField.getValue());
            bankCardDto.setBirthDate(userBirthDateField.getValue());
            bankCardDto.setGender(userGenders.getValue());
            bankCardDto.setPhoneNumber(userPhoneNumberField.getValue());
            bankCardDto.setPassportDto(passportDto);
            Set<ShippingAddressDto> shippingAddress = new HashSet<>();
            shippingAddress.add(personalAddressDto);
            bankCardDto.setShippingAddressDtos(shippingAddress);
            Set<BankCardDto> card = new HashSet<>();
            card.add(bankCardDto);
            bankCardDto.setBankCardDtos(card);
            Set<String> roles = new HashSet<>();
            roles.add(userRoleField.getValue());
            bankCardDto.setRoles(roles);

            BankCardDto savedBankCardDto = userClient.create(bankCardDto).getBody();
            dataSource.add(savedBankCardDto);

            userEmailField.clear();
            userPasswordField.clear();
            userSecurityQuestionField.clear();
            userAnswerQuestionField.clear();
            userFirstNameField.clear();
            userLastNameField.clear();
            userBirthDateField.clear();
            userGenders.clear();
            userPhoneNumberField.clear();
            passportCitizenshipField.clear();
            passportPatronymField.clear();
            passportIssueDateField.clear();
            passportNumberField.clear();
            passportIssuerField.clear();
            passportIssuerNumberField.clear();
            shippingAddressField.clear();
            shippingAddressApartmentField.clear();
            shippingAddressFloorField.clear();
            shippingAddressEntranceField.clear();
            bankCardNumberField.clear();
            bankCardDueDateField.clear();
            bankCardSecurityCodeField.clear();
            userRoleField.clear();

            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }

    private Grid.Column<BankCardDto> createEditColumn() {
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

    private Grid.Column<BankCardDto> createDeleteColumn() {
        return grid.addComponentColumn(bankCardDto -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<BankCardDto> dataProvider = (ListDataProvider<BankCardDto>) grid.getDataProvider();
                    userClient.delete(bankCardDto.getId());
                    dataProvider.getItems().remove(bankCardDto);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

}