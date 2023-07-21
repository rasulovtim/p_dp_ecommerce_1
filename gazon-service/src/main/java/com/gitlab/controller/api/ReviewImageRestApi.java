package com.gitlab.controller.api;

import com.gitlab.dto.ReviewImageDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "ReviewImage REST")
@Tag(name = "ReviewImage REST", description = "ReviewImage API description")
@RequestMapping("/api/review_images")
public interface ReviewImageRestApi {

    @GetMapping
    @ApiOperation(value = "Get all ReviewImages IDs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImages found"),
            @ApiResponse(code = 204, message = "ReviewImages not present")}
    )
    ResponseEntity<long[]> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get ReviewImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImage found"),
            @ApiResponse(code = 206, message = "ReviewImage found, but as json not jpg"),
            @ApiResponse(code = 404, message = "ReviewImage not found")}
    )
    ResponseEntity<?> get(@PathVariable("id") Long id);

    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "Update ReviewImage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImage updated"),
            @ApiResponse(code = 404, message = "ReviewImage not found")}
    )
    ResponseEntity<ReviewImageDto> update(@RequestParam("file") MultipartFile files,
                                          @PathVariable Long id) throws IOException;

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete ReviewImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImage deleted"),
            @ApiResponse(code = 404, message = "ReviewImage not found")}
    )
    ResponseEntity<Void> delete(@PathVariable Long id);
}