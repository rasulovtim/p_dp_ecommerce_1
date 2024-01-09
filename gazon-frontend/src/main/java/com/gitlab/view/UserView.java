package com.gitlab.view;

import com.gitlab.clients.UserClient;
import com.gitlab.dto.*;
import com.gitlab.enums.Citizenship;
import com.gitlab.enums.Gender;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
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
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import java.util.*;

@Route(value = "users", layout = MainLayout.class)
public class UserView extends VerticalLayout {

    private final Grid<UserDto> grid = new Grid<>(UserDto.class, false);
    private final Editor<UserDto> editor = grid.getEditor();
    private final UserClient userClient;
    private final List<UserDto> dataSource;


    public UserView(UserClient userClient) {
        this.userClient = userClient;
        this.dataSource = userClient.getPage(null, null).getBody();

        ValidationMessage idValidationMessage = new ValidationMessage();
        ValidationMessage userEmailValidationMessage = new ValidationMessage();
        ValidationMessage userPasswordValidationMessage = new ValidationMessage();
        ValidationMessage userFirstNameValidationMessage = new ValidationMessage();
        ValidationMessage userLastNameValidationMessage = new ValidationMessage();

        Grid.Column<UserDto> idColumn = createIdColumn();
        Grid.Column<UserDto> userEmailColumn = createUserEmailColumn();
        Grid.Column<UserDto> userPasswordColumn = createUserPasswordColumn();
        Grid.Column<UserDto> userFirstNameColumn = createUserFirstNameColumn();
        Grid.Column<UserDto> userLastNameColumn = createUserLastNameColumn();
//
        Grid.Column<UserDto> updateColumn = createEditColumn();
        createDeleteColumn();

        Binder<UserDto> binder = createBinder();

        createIdField(binder, idValidationMessage, idColumn);
        createUserEmailField(binder, userEmailValidationMessage, userEmailColumn);
        createUserPasswordField(binder, userPasswordValidationMessage, userPasswordColumn);
        createUserFirstNameField(binder, userFirstNameValidationMessage, userFirstNameColumn);
        createUserLastNameField(binder, userLastNameValidationMessage, userLastNameColumn);

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
                userEmailValidationMessage,
                userPasswordValidationMessage,
                userFirstNameValidationMessage,
                userLastNameValidationMessage
        );
    }

    private Grid.Column<UserDto> createIdColumn() {
        return grid.addColumn(userDto -> userDto.getId().intValue())
                .setHeader("Id").setWidth("120px").setFlexGrow(0);
    }

    private Grid.Column<UserDto> createUserEmailColumn() {
        return grid.addColumn(UserDto::getEmail).setHeader("Email").setWidth("150px");
    }

    private Grid.Column<UserDto> createUserPasswordColumn() {
        return grid.addColumn(UserDto::getPassword).setHeader("Password").setWidth("150px");
    }

    private Grid.Column<UserDto> createUserFirstNameColumn() {
        return grid.addColumn(UserDto::getFirstName).setHeader("First name").setWidth("150px");
    }

    private Grid.Column<UserDto> createUserLastNameColumn() {
        return grid.addColumn(UserDto::getLastName).setHeader("Last name").setWidth("150px");
    }

    private void createIdField(Binder<UserDto> binder,
                               ValidationMessage idValidationMessage,
                               Grid.Column<UserDto> idColumn) {
        IntegerField idField = new IntegerField();
        idField.setWidthFull();
        binder.forField(idField)
                .asRequired("Id must not be empty")
                .withStatusLabel(idValidationMessage)
                .bind(userDto -> userDto.getId().intValue(),
                        (userDto, integer) -> userDto.setId(integer.longValue()));
        idColumn.setEditorComponent(idField);
    }

    private void createUserEmailField(Binder<UserDto> binder,
                                      ValidationMessage userEmailValidationMessage,
                                      Grid.Column<UserDto> userEmailColumn) {
        TextField userEmailField = new TextField();
        userEmailField.setWidthFull();
        binder.forField(userEmailField)
                .asRequired("User email must not be empty")
                .withStatusLabel(userEmailValidationMessage)
                .bind(UserDto::getEmail, UserDto::setEmail);
        userEmailColumn.setEditorComponent(userEmailField);
    }

    private void createUserPasswordField(Binder<UserDto> binder,
                                         ValidationMessage userPasswordValidationMessage,
                                         Grid.Column<UserDto> userPasswordColumn) {
        PasswordField userPasswordField = new PasswordField();
        userPasswordField.setWidthFull();
        binder.forField(userPasswordField)
                .asRequired("User password must not be empty")
                .withStatusLabel(userPasswordValidationMessage)
                .bind(UserDto::getPassword, UserDto::setPassword);
        userPasswordColumn.setEditorComponent(userPasswordField);
    }

    private void createUserFirstNameField(Binder<UserDto> binder,
                                          ValidationMessage userFirstNameValidationMessage,
                                          Grid.Column<UserDto> userFirstNameColumn) {
        TextField userFirstNAmeField = new TextField();
        userFirstNAmeField.setWidthFull();
        binder.forField(userFirstNAmeField)
                .asRequired("User first name must not be empty")
                .withStatusLabel(userFirstNameValidationMessage)
                .bind(UserDto::getFirstName, UserDto::setFirstName);
        userFirstNameColumn.setEditorComponent(userFirstNAmeField);
    }

    private void createUserLastNameField(Binder<UserDto> binder,
                                         ValidationMessage userLastNameValidationMessage,
                                         Grid.Column<UserDto> userLastNameColumn) {
        TextField userLastNameField = new TextField();
        userLastNameField.setWidthFull();
        binder.forField(userLastNameField)
                .asRequired("User last name must not be empty")
                .withStatusLabel(userLastNameValidationMessage)
                .bind(UserDto::getLastName, UserDto::setLastName);
        userLastNameColumn.setEditorComponent(userLastNameField);
    }

    private Binder<UserDto> createBinder() {
        Binder<UserDto> binder = new Binder<>(UserDto.class);
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
        verticalLayoutMain.addClassName(Gap.XSMALL);
        HorizontalLayout horizontalLayoutMain = new HorizontalLayout();
        horizontalLayoutMain.addClassName(Gap.XSMALL);

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
        userGenders.setItemLabelGenerator(Gender::getSexRussianTranslation);
        TextField userPhoneNumberField = new TextField("Phone number");
        TextField userRoleField = new TextField("User role");


        // Passport DTO layer
        Select<Citizenship> passportCitizenshipField = new Select<>();
        passportCitizenshipField.setLabel("Citizenship");
        passportCitizenshipField.setItems(Arrays.asList(Citizenship.values()));
        passportCitizenshipField.setItemLabelGenerator(Citizenship::getCitizenshipRussianTranslation);
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
        verticalLayoutColumnI.addClassName(Gap.XSMALL);
        VerticalLayout verticalLayoutColumnII = new VerticalLayout();
        verticalLayoutColumnII.addClassName(Gap.XSMALL);

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

        layoutRow1.addClassName(Gap.XSMALL);
        layoutRow1.setWidthFull();
        layoutRow1.setFlexGrow(1.0, userEmailField, userPasswordField, userSecurityQuestionField);
        layoutRow2.addClassName(Gap.XSMALL);
        layoutRow2.setWidthFull();
        layoutRow2.setFlexGrow(1.0, userFirstNameField, userLastNameField, userBirthDateField);
        layoutRow3.addClassName(Gap.XSMALL);
        layoutRow3.setWidthFull();
        layoutRow3.setFlexGrow(1.0, passportCitizenshipField, passportPatronymField, passportIssueDateField);
        layoutRow4.addClassName(Gap.XSMALL);
        layoutRow4.setWidthFull();
        layoutRow4.setFlexGrow(1.0, passportNumberField, passportIssuerField, passportIssuerNumberField);
        layoutRow5.addClassName(Gap.XSMALL);
        layoutRow5.setWidthFull();
        layoutRow5.setFlexGrow(1.0, shippingAddressField, shippingAddressApartmentField, shippingAddressFloorField);
        layoutRow6.addClassName(Gap.XSMALL);
        layoutRow6.setWidthFull();
        layoutRow6.setFlexGrow(1.0, bankCardSecurityCodeField);
        layoutRow7.addClassName(Gap.XSMALL);
        layoutRow7.setWidthFull();
        layoutRow7.setFlexGrow(1.0, userAnswerQuestionField, userPhoneNumberField);
        layoutRow8.addClassName(Gap.XSMALL);
        layoutRow8.setWidthFull();
        layoutRow8.setFlexGrow(1.0, userGenders, userRoleField);
        layoutRow9.addClassName(Gap.XSMALL);
        layoutRow9.setWidthFull();
        layoutRow9.setFlexGrow(1.0, bankCardNumberField, bankCardDueDateField);
        layoutRow10.addClassName(Gap.XSMALL);
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

            UserDto userDto = new UserDto();
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
            userDto.setEmail(userEmailField.getValue());
            userDto.setPassword(userPasswordField.getValue());
            userDto.setSecurityQuestion(userSecurityQuestionField.getValue());
            userDto.setAnswerQuestion(userAnswerQuestionField.getValue());
            userDto.setFirstName(userFirstNameField.getValue());
            userDto.setLastName(userLastNameField.getValue());
            userDto.setBirthDate(userBirthDateField.getValue());
            userDto.setGender(userGenders.getValue());
            userDto.setPhoneNumber(userPhoneNumberField.getValue());
            userDto.setPassportDto(passportDto);
            Set<ShippingAddressDto> shippingAddress = new HashSet<>();
            shippingAddress.add(personalAddressDto);
            userDto.setShippingAddressDtos(shippingAddress);
            Set<BankCardDto> card = new HashSet<>();
            card.add(bankCardDto);
            userDto.setBankCardDtos(card);
            Set<String> roles = new HashSet<>();
            roles.add(userRoleField.getValue());
            userDto.setRoles(roles);

            UserDto savedUserDto = userClient.create(userDto).getBody();
            dataSource.add(savedUserDto);

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

    private Grid.Column<UserDto> createEditColumn() {
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

    private Grid.Column<UserDto> createDeleteColumn() {
        return grid.addComponentColumn(userDto -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<UserDto> dataProvider = (ListDataProvider<UserDto>) grid.getDataProvider();
                    userClient.delete(userDto.getId());
                    dataProvider.getItems().remove(userDto);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

}
