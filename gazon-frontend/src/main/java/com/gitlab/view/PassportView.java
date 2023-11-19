package com.gitlab.view;

import com.gitlab.clients.PassportClient;
import com.gitlab.dto.PassportDto;
import com.gitlab.enums.Citizenship;
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
import com.vaadin.flow.component.select.Select;
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

@Route(value = "passport", layout = MainLayout.class)
public class PassportView extends VerticalLayout {

    private final Grid<PassportDto> grid = new Grid<>(PassportDto.class, false);
    private final Editor<PassportDto> editor = grid.getEditor();
    private final PassportClient passportClient;
    private final List<PassportDto> dataSource;

    public PassportView(PassportClient passportClient) {
        this.passportClient = passportClient;
        this.dataSource = passportClient.getAll().getBody();

        ValidationMessage idValidationMessage = new ValidationMessage();
        ValidationMessage passportFirstNameValidationMessage = new ValidationMessage();
        ValidationMessage passportLastNameValidationMessage = new ValidationMessage();
        ValidationMessage passportPatronymValidationMessage = new ValidationMessage();
        ValidationMessage passportCitizenshipValidationMessage = new ValidationMessage();
        ValidationMessage passportBirthDateValidationMessage = new ValidationMessage();
        ValidationMessage passportIssueDateValidationMessage = new ValidationMessage();
        ValidationMessage passportPassportNumberValidationMessage = new ValidationMessage();
        ValidationMessage passportIssuerValidationMessage = new ValidationMessage();
        ValidationMessage passportIssuerNumberValidationMessage = new ValidationMessage();

        Grid.Column<PassportDto> idColumn = createIdColumn();
        Grid.Column<PassportDto> passportFirstNameColumn = createPassportFirstNameColumn();
        Grid.Column<PassportDto> passportLastNameColumn = createPassportLastNameColumn();
        Grid.Column<PassportDto> passportPatronymColumn = createPassportPatronymColumn();
        Grid.Column<PassportDto> passportCitizenshipColumn = createPassportCitizenshipColumn();
        Grid.Column<PassportDto> passportBirthDateColumn = createPassportBirthDateColumn();
        Grid.Column<PassportDto> passportIssueDateColumn = createPassportIssueDateColumn();
        Grid.Column<PassportDto> passportPassportNumberColumn = createPassportPassportNumberColumn();
        Grid.Column<PassportDto> passportIssuerColumn = createPassportIssuerColumn();
        Grid.Column<PassportDto> passportIssuerNumberColumn = createPassportIssuerNumberColumn();

        Grid.Column<PassportDto> updateColumn = createEditColumn();
        createDeleteColumn();

        Binder<PassportDto> binder = createBinder();

        createIdField(binder, idValidationMessage, idColumn);
        createFirstNameField(binder, passportFirstNameValidationMessage, passportFirstNameColumn);
        createLastNameField(binder, passportLastNameValidationMessage, passportLastNameColumn);
        createPatronymField(binder, passportPatronymValidationMessage, passportPatronymColumn);
        createCitizenshipField(binder, passportCitizenshipValidationMessage, passportCitizenshipColumn);
        createBirthDateField(binder, passportBirthDateValidationMessage, passportBirthDateColumn);
        createIssuerDateField(binder, passportIssueDateValidationMessage, passportIssueDateColumn);
        createPassportNumberField(binder, passportPassportNumberValidationMessage, passportPassportNumberColumn);
        createIssuerField(binder, passportIssuerValidationMessage, passportIssuerColumn);
        createIssuerNumberField(binder, passportIssuerNumberValidationMessage, passportIssuerNumberColumn);

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
                passportFirstNameValidationMessage,
                passportLastNameValidationMessage,
                passportPatronymValidationMessage,
                passportCitizenshipValidationMessage,
                passportBirthDateValidationMessage,
                passportIssueDateValidationMessage,
                passportPassportNumberValidationMessage,
                passportIssuerValidationMessage,
                passportIssuerNumberValidationMessage

        );
    }

    private void createIdField(Binder<PassportDto> binder,
                               ValidationMessage idValidationMessage,
                               Grid.Column<PassportDto> idColumn) {
        IntegerField idField = new IntegerField();
        idField.setWidthFull();
        binder.forField(idField)
                .asRequired("Id must not be empty")
                .withStatusLabel(idValidationMessage)
                .bind(passportDto -> passportDto.getId().intValue(),
                        (passportDto, integer) -> passportDto.setId(integer.longValue()));
        idColumn.setEditorComponent(idField);
    }

    private void createFirstNameField(Binder<PassportDto> binder,
                                      ValidationMessage passportFirstNameValidationMessage,
                                      Grid.Column<PassportDto> passportFirstNameColumn) {
        TextField passportTextField = new TextField();
        passportTextField.setWidthFull();
        binder.forField(passportTextField).withValidator(new StringLengthValidator(
                        "Passport's first name should have at least two characters and not exceed 15",
                        2, 15))
                .asRequired("Passport's firstname shouldn't be empty")
                .withStatusLabel(passportFirstNameValidationMessage)
                .bind(PassportDto::getFirstName, PassportDto::setFirstName);
        passportFirstNameColumn.setEditorComponent(passportTextField);

    }

    private void createLastNameField(Binder<PassportDto> binder,
                                     ValidationMessage passportLastNameValidationMessage,
                                     Grid.Column<PassportDto> passportLastNameColumn) {
        TextField passportTextField = new TextField();
        passportTextField.setWidthFull();
        binder.forField(passportTextField).withValidator(new StringLengthValidator(
                        "Passport's lastname should have at least two characters and not exceed 25",
                        0, 25))
                .asRequired("Passport's lastname shouldn't be empty")
                .withStatusLabel(passportLastNameValidationMessage)
                .bind(PassportDto::getLastName, PassportDto::setLastName);
        passportLastNameColumn.setEditorComponent(passportTextField);

    }

    private void createPatronymField(Binder<PassportDto> binder,
                                     ValidationMessage passportPatronymValidationMessage,
                                     Grid.Column<PassportDto> passportPatronymColumn) {
        TextField passportTextField = new TextField();
        passportTextField.setWidthFull();
        binder.forField(passportTextField).withValidator(new StringLengthValidator(
                        "Passport patronym should have at least two characters and not exceed 25",
                        2, 25))
                .withStatusLabel(passportPatronymValidationMessage)
                .bind(PassportDto::getPatronym, PassportDto::setPatronym);
        passportPatronymColumn.setEditorComponent(passportTextField);

    }

    private void createBirthDateField(Binder<PassportDto> binder,
                                      ValidationMessage passportBirthDateValidationMessage,
                                      Grid.Column<PassportDto> passportBirthDateColumn) {
        DatePicker passportTextField = new DatePicker();
        passportTextField.setWidthFull();
        binder.forField(passportTextField)
                .asRequired("Passport's lastname shouldn't be empty")
                .withStatusLabel(passportBirthDateValidationMessage)
                .bind(PassportDto::getBirthDate, PassportDto::setBirthDate);
        passportBirthDateColumn.setEditorComponent(passportTextField);
    }

    private void createIssuerDateField(Binder<PassportDto> binder,
                                       ValidationMessage passportIssueDateValidationMessage,
                                       Grid.Column<PassportDto> passportIssueDateColumn) {
        DatePicker passportTextField = new DatePicker();
        passportTextField.setWidthFull();
        binder.forField(passportTextField)
                .asRequired("Passport's lastname shouldn't be empty")
                .withStatusLabel(passportIssueDateValidationMessage)
                .bind(PassportDto::getIssueDate, PassportDto::setIssueDate);
        passportIssueDateColumn.setEditorComponent(passportTextField);
    }

    private void createCitizenshipField(Binder<PassportDto> binder,
                                        ValidationMessage passportCitizenshipValidationMessage,
                                        Grid.Column<PassportDto> passportCitizenshipColumn) {
        Select<Citizenship> passportTextField = new Select<>();
        passportTextField.setItems(Arrays.asList(Citizenship.values()));
        passportTextField.setItemLabelGenerator(Citizenship::getCitizenshipRussianTranslation);
        passportTextField.setWidthFull();
        binder.forField(passportTextField)
                .asRequired("Passport's firstname shouldn't be empty")
                .withStatusLabel(passportCitizenshipValidationMessage)
                .bind(PassportDto::getCitizenship, PassportDto::setCitizenship);
        passportCitizenshipColumn.setEditorComponent(passportTextField);
        //
    }


    private void createPassportNumberField(Binder<PassportDto> binder,
                                           ValidationMessage passportPassportNumberValidationMessage,
                                           Grid.Column<PassportDto> passportPassportNumberColumn) {
        TextField passportTextField = new TextField();
        passportTextField.setWidthFull();
        binder.forField(passportTextField).withValidator(name -> name.length() == 11
                        , "Passport number length 11, must not be empty, in format \"1234 567890\"")
                .asRequired("Passport number must consist of 11 characters")
                .withStatusLabel(passportPassportNumberValidationMessage)
                .bind(PassportDto::getPassportNumber, PassportDto::setPassportNumber);
        passportPassportNumberColumn.setEditorComponent(passportTextField);

    }

    private void createIssuerField(Binder<PassportDto> binder,
                                   ValidationMessage passportIssuerValidationMessage,
                                   Grid.Column<PassportDto> passportIssuerColumn) {
        TextField passportTextField = new TextField();
        passportTextField.setWidthFull();
        binder.forField(passportTextField).withValidator(new StringLengthValidator(
                        "Passport's issuer cannot contain less than ten characters and should not exceed 255",
                        10, 255))
                .asRequired("Passport's issuer shouldn't be empty")
                .withStatusLabel(passportIssuerValidationMessage)
                .bind(PassportDto::getIssuer, PassportDto::setIssuer);
        passportIssuerColumn.setEditorComponent(passportTextField);

    }

    private void createIssuerNumberField(Binder<PassportDto> binder,
                                         ValidationMessage passportIssuerNumberValidationMessage,
                                         Grid.Column<PassportDto> passportIssuerNumberColumn) {
        TextField passportTextField = new TextField();
        passportTextField.setWidthFull();
        binder.forField(passportTextField).withValidator(name -> name.length() == 7
                        , "Passport's issuer number must consist of 7 characters, in format \"123-456\"")
                .withStatusLabel(passportIssuerNumberValidationMessage)
                .bind(PassportDto::getIssuerNumber, PassportDto::setIssuerNumber);
        passportIssuerNumberColumn.setEditorComponent(passportTextField);

    }

    private void addEditorListeners() {
        editor.addSaveListener(e -> {
            passportClient.update(e.getItem().getId(), e.getItem());
            grid.getDataProvider().refreshAll();
        });
    }

    private void addTheme() {
        getThemeList().clear();
        getThemeList().add("spacing-s");
    }

    private Tabs createTabs(Div contentContainer) {
        Tabs tabs = new Tabs();

        Tab tableTab = new Tab("Passport table");
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
        Tab createTab = new Tab("Create passport");
        TextField passportLastNameField = new TextField("Last Name");
        passportLastNameField.setPlaceholder("Last name can contain only letters");
        TextField passportFirstNameField = new TextField("First Name");
        passportFirstNameField.setPlaceholder("First name can contain only letters");
        TextField passportPatronymField = new TextField("Patronym");
        passportPatronymField.setPlaceholder("Patronym can contain only letters");
        Select<Citizenship> passportCitizenshipField = new Select<>();
        passportCitizenshipField.setLabel("Citizenship");
        passportCitizenshipField.setItems(Arrays.asList(Citizenship.values()));
        passportCitizenshipField.setItemLabelGenerator(Citizenship::getCitizenshipRussianTranslation);
        DatePicker passportIssueDateField = new DatePicker("Issue date");
        passportIssueDateField.setPlaceholder("dd.mm.yyyy");
        DatePicker passportBirthDateField = new DatePicker("Birth date");
        passportBirthDateField.setPlaceholder("dd.mm.yyyy");
        TextField passportPassportNumberField = new TextField("Passport Number");
        passportPassportNumberField.setPlaceholder("Number must be in \"1234 567890\" format");
        TextField passportIssuerField = new TextField("Issuer");
        passportIssuerField.setPlaceholder("Issuer must be in \"Department police\" format, 10 - 255 characters");
        TextField passportIssuerNumberField = new TextField("Issuer Number");
        passportIssuerNumberField.setPlaceholder("Number must be in \"123-456\" format");
        Button createButton = new Button("Create");
        formLayout.add(passportFirstNameField, passportLastNameField, passportPatronymField, passportCitizenshipField,
                passportIssueDateField, passportBirthDateField, passportPassportNumberField, passportIssuerField,
                passportIssuerNumberField, createButton);
        createButton.addClickListener(passport -> {
            PassportDto passportDto = new PassportDto();
            passportDto.setLastName(passportLastNameField.getValue());
            passportDto.setFirstName(passportFirstNameField.getValue());
            passportDto.setPatronym(passportPatronymField.getValue());
            passportDto.setIssueDate(passportIssueDateField.getValue());
            passportDto.setBirthDate(passportBirthDateField.getValue());
            passportDto.setPassportNumber(passportPassportNumberField.getValue());
            passportDto.setIssuer(passportIssuerField.getValue());
            passportDto.setIssuerNumber(passportIssuerNumberField.getValue());
            passportDto.setCitizenship(passportCitizenshipField.getValue());

            PassportDto savedPassportDto = passportClient.create(passportDto).getBody();
            dataSource.add(savedPassportDto);
            passportFirstNameField.clear();
            passportLastNameField.clear();
            passportPatronymField.clear();
            passportIssueDateField.clear();
            passportBirthDateField.clear();
            passportPassportNumberField.clear();
            passportIssuerField.clear();
            passportIssuerNumberField.clear();
            passportCitizenshipField.clear();
            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }

    private Binder<PassportDto> createBinder() {
        Binder<PassportDto> binder = new Binder<>(PassportDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);
        return binder;
    }

    private Grid.Column<PassportDto> createIdColumn() {
        return grid.addColumn(passportDto -> passportDto.getId().intValue())
                .setHeader("Id").setWidth("50px").setFlexGrow(0);
    }

    private Grid.Column<PassportDto> createPassportLastNameColumn() {
        return grid.addColumn(PassportDto::getLastName).setHeader("Last name").setWidth("110px");
    }

    private Grid.Column<PassportDto> createPassportFirstNameColumn() {
        return grid.addColumn(PassportDto::getFirstName).setHeader("First name").setWidth("110px");
    }

    private Grid.Column<PassportDto> createPassportPatronymColumn() {
        return grid.addColumn(PassportDto::getPatronym).setHeader("Patronym").setWidth("110px");
    }

    private Grid.Column<PassportDto> createPassportCitizenshipColumn() {
        return grid.addColumn(PassportDto::getCitizenship).setHeader("Citizenship").setWidth("110px");
    }

    private Grid.Column<PassportDto> createPassportBirthDateColumn() {
        return grid.addColumn(PassportDto::getBirthDate).setHeader("BirthDate").setWidth("110px");
    }

    private Grid.Column<PassportDto> createPassportIssueDateColumn() {
        return grid.addColumn(PassportDto::getIssueDate).setHeader("IssueDate").setWidth("110px");
    }

    private Grid.Column<PassportDto> createPassportPassportNumberColumn() {
        return grid.addColumn(PassportDto::getPassportNumber).setHeader("PassportNumber").setWidth("110px");
    }

    private Grid.Column<PassportDto> createPassportIssuerColumn() {
        return grid.addColumn(PassportDto::getIssuer).setHeader("Issuer").setWidth("110px");
    }

    private Grid.Column<PassportDto> createPassportIssuerNumberColumn() {
        return grid.addColumn(PassportDto::getIssuerNumber).setHeader("IssuerNumber").setWidth("110px");
    }

    private Grid.Column<PassportDto> createEditColumn() {
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

    private Grid.Column<PassportDto> createDeleteColumn() {
        return grid.addComponentColumn(passportDto -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<PassportDto> dataProvider = (ListDataProvider<PassportDto>) grid.getDataProvider();
                    passportClient.delete(passportDto.getId());
                    dataProvider.getItems().remove(passportDto);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

}
