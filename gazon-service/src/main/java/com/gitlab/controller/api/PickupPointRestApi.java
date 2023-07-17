package com.gitlab.controller.api;

import com.gitlab.dto.PickupPointDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "PickupPoint REST")
@Tag(name = "PickupPoint REST", description = "API for pickup point")
@RequestMapping("/api/pickup_point")
public interface PickupPointRestApi {

    @GetMapping
    @ApiOperation(value = "Get all Pickup Points")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pickup Points found"),
            @ApiResponse(code = 204, message = "Pickup Points not present")}
    )
    ResponseEntity<List<PickupPointDto>> getAll();

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Pickup Point by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pickup Point found"),
            @ApiResponse(code = 404, message = "Pickup Point not found")}
    )
    ResponseEntity<PickupPointDto> get(@ApiParam(name = "id", value = "PickupPoint.id")
                                       @PathVariable Long id);

    @PostMapping
    @ApiOperation(value = "Create Pickup Point")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pickup Point created"),
            @ApiResponse(code = 400, message = "Pickup Point not created")}
    )
    ResponseEntity<PickupPointDto> create(@ApiParam(name = "pickupPoint", value = "PickupPointDto")
                                          @Valid @RequestBody PickupPointDto pickupPointDto);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Pickup Point")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pickup Point updated"),
            @ApiResponse(code = 400, message = "Pickup Point not updated")}
    )
    ResponseEntity<PickupPointDto> update(@ApiParam(name = "id", value = "PickupPoint.id")
                                          @PathVariable Long id,
                                          @ApiParam(name = "pickupPoint", value = "PickupPointDto")
                                          @Valid @RequestBody PickupPointDto pickupPointDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Pickup Point by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pickup Point deleted"),
            @ApiResponse(code = 404, message = "Pickup Point not found")}
    )
    ResponseEntity<Void> delete(@ApiParam(name = "id", value = "PickupPoint.id")
                                @PathVariable Long id);
}
