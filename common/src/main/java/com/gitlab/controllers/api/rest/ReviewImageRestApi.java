package com.gitlab.controllers.api.rest;

import com.gitlab.dto.ReviewImageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Review Image REST")
@Tag(name = "Review Image REST", description = "Review Image API description")
public interface ReviewImageRestApi {

    @GetMapping("/api/review-images")
    @ApiOperation(value = "Get Review image page")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review image Page found"),
            @ApiResponse(code = 204, message = "Review image Page not present")}
    )
    ResponseEntity<List<ReviewImageDto>> getPage(@ApiParam(name = "page") @RequestParam(required = false, value = "page") Integer page,
                                                 @ApiParam(name = "size") @RequestParam(required = false, value = "size") Integer size);


    @GetMapping("/api/review-images/{id}")
    @ApiOperation(value = "Get ReviewImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review Image found"),
            @ApiResponse(code = 206, message = "Review Image found, but as json not jpg"),
            @ApiResponse(code = 404, message = "Review Image not found")}
    )
    ResponseEntity<?> get(@ApiParam(name = "id", value = "ReviewImage.id") @PathVariable("id") Long id);

    @PostMapping("/api/review-images")
    @ApiOperation(value = "Create Review Image")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Review image created"),
            @ApiResponse(code = 400, message = "Review image not created")}
    )
    ResponseEntity<ReviewImageDto> create(@ApiParam(name = "review_image", value = "ReviewImageDto") @Valid @RequestBody ReviewImageDto reviewImageDto);

    @PatchMapping(value = "/api/review-images/{id}")
    @ApiOperation(value = "Update Review Image")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review Image updated"),
            @ApiResponse(code = 404, message = "Review Image not found")}
    )
    ResponseEntity<ReviewImageDto> update(@ApiParam(name = "id", value = "ReviewImage.id") @PathVariable(value = "id") Long id,
                                          @ApiParam(name = "review_image", value = "ReviewImageDto") @Valid @RequestBody ReviewImageDto reviewImageDto);

    @DeleteMapping("/api/review-images/{id}")
    @ApiOperation(value = "Delete ReviewImage by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review Image deleted"),
            @ApiResponse(code = 404, message = "Review Image not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "ReviewImage.id") @PathVariable(value = "id") Long id);
}