package com.gitlab.view;

import com.gitlab.clients.ReviewClient;
import com.gitlab.clients.ReviewImageClient;
import com.gitlab.dto.ReviewDto;
import com.gitlab.dto.ReviewImageDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Route(value = "review_image", layout = MainLayout.class)
public class ReviewImageView extends VerticalLayout {

    private final Grid<ReviewImageDto> grid = new Grid<>(ReviewImageDto.class, false);
    private final Editor<ReviewImageDto> editor = grid.getEditor();
    private final ReviewImageClient reviewImageClient;
    private final ReviewClient reviewClient;
    private final List<ReviewImageDto> dataSource;

    private final List<Byte> listBytesImage = new ArrayList<>();
    private final int imageHeight = 800;
    private final int imageWidth = 600;


    public ReviewImageView(ReviewImageClient reviewImageClient, ReviewClient reviewClient) {
        this.reviewImageClient = reviewImageClient;
        this.reviewClient = reviewClient;
        int page = 0;
        int size = 100;
        this.dataSource = Objects.requireNonNull(reviewImageClient.getPage(page, size).getBody()).stream().collect(Collectors.toList());
        ValidationMessage idValidationMessage = new ValidationMessage();
        ValidationMessage nameValidationMessage = new ValidationMessage();
        ValidationMessage dataValidationMessage = new ValidationMessage();

        createIdColumn();
        Grid.Column<ReviewImageDto> nameColumn = createNameColumn();
        Grid.Column<ReviewImageDto> dataColumn = createDataColumn();
        Grid.Column<ReviewImageDto> updateColumn = createEditColumn();
        createDeleteColumn();

        Binder<ReviewImageDto> binder = createBinder();

        createNameField(binder, nameValidationMessage, nameColumn);
        createDataField(dataColumn);

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

        add(tabs, contentContainer, idValidationMessage, nameValidationMessage, dataValidationMessage);
    }

    private void createIdColumn() {
        grid.addColumn(reviewImageDto -> reviewImageDto.getId().intValue()).setHeader("Id").setWidth("120px").setFlexGrow(0);
    }

    private Grid.Column<ReviewImageDto> createNameColumn() {
        return grid.addColumn(ReviewImageDto::getName).setHeader("Name text").setWidth("550px");
    }

    private Grid.Column<ReviewImageDto> createDataColumn() {
        return grid.addComponentColumn(this::getImage).setHeader("Image").setWidth("250px");
    }

    private Image getImage(ReviewImageDto imageDto) {
        StreamResource sr = new StreamResource("user", () -> new ByteArrayInputStream(imageDto.getData()));
        sr.setContentType("image/png");
        Image img = new Image(sr, "profile-picture");
        img.setWidth("250px");
        img.setHeight("300px");
        return img;
    }

    private Grid.Column<ReviewImageDto> createEditColumn() {
        return grid.addComponentColumn(reviewImage -> {
            Button updateButton = new Button("Update");
            updateButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(reviewImage);
            });
            return updateButton;
        });
    }

    private void createDeleteColumn() {
        grid.addComponentColumn(reviewImage -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                if (grid.getDataProvider().isInMemory() && grid.getDataProvider().getClass() == ListDataProvider.class) {
                    ListDataProvider<ReviewImageDto> dataProvider = (ListDataProvider<ReviewImageDto>) grid.getDataProvider();
                    reviewImageClient.delete(reviewImage.getId());
                    dataProvider.getItems().remove(reviewImage);
                }
                grid.getDataProvider().refreshAll();
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);
    }

    private Binder<ReviewImageDto> createBinder() {
        Binder<ReviewImageDto> binder = new Binder<>(ReviewImageDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);
        return binder;
    }


    private void createNameField(Binder<ReviewImageDto> binder,
                                 ValidationMessage nameValidationMessage,
                                 Grid.Column<ReviewImageDto> nameColumn) {
        TextField nameField = new TextField();
        nameField.setWidthFull();
        binder.forField(nameField).asRequired("Review image name must not be empty")
                .withStatusLabel(nameValidationMessage)
                .bind(ReviewImageDto::getName, ReviewImageDto::setName);
        nameColumn.setEditorComponent(nameField);

    }

    private void createDataField(Grid.Column<ReviewImageDto> dataColumn) {
        Upload dataField = createUpload();
        dataField.setWidthFull();
        dataColumn.setEditorComponent(dataField);
    }

    private void addEditorListeners() {
        editor.addSaveListener(e -> {
            if (!listBytesImage.isEmpty()) {
                ReviewImageDto reviewImageDto = e.getItem();
                reviewImageDto.setData(list2Array());
                ReviewImageDto updatedReviewImageDto = reviewImageClient.update(e.getItem().getId(), reviewImageDto).getBody();
                dataSource.stream().filter(data -> Objects.equals(data.getId(), updatedReviewImageDto.getId())).findFirst().get().setData(list2Array());
                listBytesImage.clear();
                grid.setItems(dataSource);
            }
            else {
                reviewImageClient.update(e.getItem().getId(), e.getItem());
            }
            grid.getDataProvider().refreshAll();
        });
    }

    private void addTheme() {
        getThemeList().clear();
        getThemeList().add("spacing-s");
    }

    private Tabs createTabs(Div contentContainer) {
        Tabs tabs = new Tabs();

        Tab tableTab = new Tab("Review images table");
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
        Tab createTab = new Tab("Create Review image");
        TextField nameField = new TextField("Review image name");
        Select<Long> reviewField = new Select<>();
        reviewField.setLabel("Review id");
        reviewField.setItems(Objects.requireNonNull(reviewClient.getAll().getBody())
                .stream()
                .map(ReviewDto::getId)
                .collect(Collectors.toList()));

        Upload dataField = createUpload();

        Button createButton = new Button("Create");
        formLayout.add(nameField, reviewField, dataField, createButton);
        createButton.addClickListener(event -> {
            ReviewImageDto reviewImageDto = new ReviewImageDto();
            reviewImageDto.setName(nameField.getValue());
            reviewImageDto.setData(list2Array());
            reviewImageDto.setReviewId(reviewField.getValue());
            ReviewImageDto savedReviewImage = reviewImageClient.create(reviewImageDto).getBody();
            dataSource.add(savedReviewImage);
            listBytesImage.clear();
            nameField.clear();
            reviewField.clear();
            dataField.clearFileList();
            grid.getDataProvider().refreshAll();
        });
        return createTab;
    }

    @NotNull
    private Upload createUpload() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload dataField = new Upload(buffer);
        dataField.setAcceptedFileTypes("image/jpeg", "image/png");
        dataField.addSucceededListener(event -> {
            try {
                BufferedImage inputImage = ImageIO.read(buffer.getInputStream());
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();

                    java.awt.Image scaledImage = inputImage.getScaledInstance(imageWidth, imageHeight, java.awt.Image.SCALE_DEFAULT);
                    BufferedImage bufferedScaledImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = bufferedScaledImage.createGraphics();
                    g2d.drawImage(scaledImage, 0, 0, null);
                    g2d.dispose();

                ImageIO.write(inputImage, "png", pngContent);
                array2List(pngContent.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return dataField;
    }

    private byte[] list2Array() {
        byte[] arr = new byte[listBytesImage.size()];
        IntStream.range(0, listBytesImage.size()).forEach(i -> arr[i] = listBytesImage.get(i));
        return arr;
    }

    private void array2List(byte[] arr) {
        for (byte byt : arr) {
            listBytesImage.add(byt);
        }
    }
}
