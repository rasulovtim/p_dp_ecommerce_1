package com.gitlab.controllers.api.rest;

import com.gitlab.dto.ReviewDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = "Review REST")
@Tag(name = "Review REST", description = "Review API description")
public interface ReviewRestApi {

    @GetMapping("/api/review")
    @ApiOperation(value = "Get all Reviews")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reviews found"),
            @ApiResponse(code = 204, message = "Reviews not present")}
    )
    ResponseEntity<List<ReviewDto>> getAll();

    @GetMapping("/api/review/{id}")
    @ApiOperation(value = "Get Review by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review found"),
            @ApiResponse(code = 404, message = "Review not found")}
    )
    ResponseEntity<ReviewDto> get(@ApiParam(name = "id", value = "Review.id") @PathVariable("id") Long id);

    @PostMapping("/api/review")
    @ApiOperation(value = "Create Review")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Review created"),
            @ApiResponse(code = 400, message = "Review not created")}
    )
    ResponseEntity<ReviewDto> create(@ApiParam(name = "Review", value = "ReviewDto") @Valid @RequestBody ReviewDto reviewDto);

    @PatchMapping("/api/review/{id}")
    @ApiOperation(value = "Update Review")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review updated"),
            @ApiResponse(code = 404, message = "Previous Review not found"),
            @ApiResponse(code = 400, message = "Review not updated")}
    )
    ResponseEntity<ReviewDto> update(@ApiParam(name = "id", value = "Review.id") @PathVariable("id") Long id,
                                      @ApiParam(name = "Review", value = "ReviewDto")
                                      @Valid @RequestBody ReviewDto reviewDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Review by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review deleted"),
            @ApiResponse(code = 404, message = "Review not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "Review.id") @PathVariable("id") Long id);

    @GetMapping("/api/review/{id}/images")
    @ApiOperation(value = "Get all ReviewImages IDs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImages found"),
            @ApiResponse(code = 204, message = "ReviewImages not present"),
            @ApiResponse(code = 404, message = "Review's ReviewImages not found")}
    )
    ResponseEntity<long[]> getImagesIDsByReviewId(@ApiParam(name = "id", value = "Review.id")
                                                   @PathVariable("id") Long id);

    @PostMapping("/api/review/{id}/images")
    @ApiOperation(value = "Upload ReviewImages")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "ReviewImages uploaded"),
            @ApiResponse(code = 400, message = "ReviewImages not uploaded"),
            @ApiResponse(code = 404, message = "Review not found, unable to upload images without Review")}
    )
    ResponseEntity<String> uploadImagesByReviewId(@RequestParam("files") MultipartFile[] files,
                                                   @PathVariable("id") Long id) throws IOException;

    @DeleteMapping("/api/review/{id}/images")
    @ApiOperation(value = "Delete ReviewImages by Review.id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReviewImages deleted"),
            @ApiResponse(code = 204, message = "Review with such id has no images"),
            @ApiResponse(code = 404, message = "Review not found")}
    )
    ResponseEntity<String> deleteAllImagesByReviewId(@PathVariable("id") Long id);
}