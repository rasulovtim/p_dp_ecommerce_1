package com.gitlab.view;

import com.gitlab.clients.ProductClient;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("search")
public class SearchResultsView extends Div {
    private final ProductClient productClient;

    public SearchResultsView(ProductClient productClient) {
        this.productClient = productClient;
        SearchBar searchBar = new SearchBar();
        // adding a search component
        add(searchBar);

        // contents of the search results page
    }
}