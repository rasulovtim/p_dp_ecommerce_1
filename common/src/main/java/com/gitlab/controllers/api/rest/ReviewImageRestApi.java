package com.gitlab.controllers.api.rest;

import com.gitlab.dto.ReviewImageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "ReviewImage REST")
@Tag(name = "ReviewImage REST", description = "ReviewImage API description")
public interface ReviewImageRestApi {

    @GetMapping("/api/review_images")
    @ApiOperation(value = "Get all ReviewImages IDs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImages found"),
            @ApiResponse(code = 204, message = "ReviewImages not present")}
    )
    ResponseEntity<long[]> getAll();

    @GetMapping("/api/review_images/{id}")
    @ApiOperation(value = "Get ReviewImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImage found"),
            @ApiResponse(code = 206, message = "ReviewImage found, but as json not jpg"),
            @ApiResponse(code = 404, message = "ReviewImage not found")}
    )
    ResponseEntity<?> get(@PathVariable(value = "id") Long id);

    @PatchMapping(value = "/api/review_images/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "Update ReviewImage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImage updated"),
            @ApiResponse(code = 404, message = "ReviewImage not found")}
    )
    ResponseEntity<ReviewImageDto> update(@RequestParam("file") MultipartFile files,
                                          @PathVariable(value = "id") Long id) throws IOException;

    @DeleteMapping("/api/review_images/{id}")
    @ApiOperation(value = "Delete ReviewImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImage deleted"),
            @ApiResponse(code = 404, message = "ReviewImage not found")}
    )
    ResponseEntity<Void> delete(@PathVariable(value = "id") Long id);
}