package com.gitlab.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.QueryParameters;

import java.util.HashMap;
import java.util.Map;

@Route
public class SearchBar extends VerticalLayout {
    private TextField searchField = new TextField();
    private Button searchButton = new Button(VaadinIcon.SEARCH.create());

    public SearchBar() {
        addClassName("search-bar-desktop");

        HorizontalLayout searchWrapper = new HorizontalLayout();
        searchWrapper.addClassName("search-bar-wrapper");

        searchField.setPlaceholder("Искать на Ozon");
        searchField.addClassName("search-input");
        searchField.setWidthFull();
        searchField.setWidth("600px");

        Button searchButton = new Button(VaadinIcon.SEARCH.create());
        searchButton.addClickListener(event -> performSearch());

        HorizontalLayout searchFieldContainer = new HorizontalLayout(searchField, searchButton);
        searchFieldContainer.addClassName("search-field-container");

        searchWrapper.add(searchFieldContainer);
        add(searchWrapper);
    }

    public TextField getSearchField() {
        return searchField;
    }

    private void performSearch() {
        String query = searchField.getValue();
        if (!query.isEmpty()) {
            // request parameters
            Map<String, String> parametersMap = new HashMap<>();
            parametersMap.put("text", query);
            QueryParameters queryParameters = QueryParameters.simple(parametersMap);

            UI.getCurrent().navigate("search", queryParameters);
        }
    }
}