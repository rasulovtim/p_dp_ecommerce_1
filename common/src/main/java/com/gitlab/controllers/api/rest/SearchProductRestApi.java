package com.gitlab.controllers.api.rest;

import com.gitlab.dto.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "ProductSearch REST")
@Tag(name = "ProductSearch REST", description = "ProductSearch API description")
public interface SearchProductRestApi {

    @GetMapping("/api/search/product")
    @ApiOperation(value = "Search products by Product.name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Products found"),
            @ApiResponse(code = 204, message = "Products not present")}
    )
    ResponseEntity<List<ProductDto>> search(@RequestParam("name") String name) throws InterruptedException;
}