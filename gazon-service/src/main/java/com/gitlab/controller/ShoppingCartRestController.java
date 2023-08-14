package com.gitlab.controller;


import com.gitlab.controller.api.ShoppingCartRestApi;
import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.ShoppingCartMapper;
import com.gitlab.model.ShoppingCart;
import com.gitlab.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ShoppingCartRestController implements ShoppingCartRestApi {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ResponseEntity<List<ShoppingCartDto>> getAll() {
        var shoppingCarts = shoppingCartService.findAll();

        return shoppingCarts.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(shoppingCarts.stream().map(shoppingCartMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<ShoppingCartDto> get(Long id) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartService.findById(id);

        if (shoppingCartOptional.isEmpty()) return ResponseEntity.notFound().build();

        ShoppingCart shoppingCart = shoppingCartOptional.orElse(null);
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);

        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartDto);
    }

    @Override
    public ResponseEntity<ShoppingCartDto> create(ShoppingCartDto shoppingCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(shoppingCartMapper
                        .toDto(shoppingCartService
                                .save(shoppingCartMapper
                                        .toEntity(shoppingCartDto))));
    }

    @Override
    public ResponseEntity<ShoppingCartDto> update(Long id, ShoppingCartDto shoppingCartDto) {
        return shoppingCartService.update(id, shoppingCartMapper.toEntity(shoppingCartDto))
                .map(cart -> ResponseEntity.ok(shoppingCartMapper.toDto(cart)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        shoppingCartService.delete(id);
        return ResponseEntity.ok().build();
    }
}
