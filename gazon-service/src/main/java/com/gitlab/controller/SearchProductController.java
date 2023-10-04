package com.gitlab.controller;

import com.gitlab.controllers.api.rest.SearchProductRestApi;
import com.gitlab.dto.ProductDto;
import com.gitlab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchProductController implements SearchProductRestApi {


    private final ProductService productService;

    @Override
    public ResponseEntity<List<ProductDto>> search(String name) throws InterruptedException {
        var products = productService.findByNameIgnoreCaseContaining(name);

        return products.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(products);
    }
}
