package com.gitlab.controller;

import com.gitlab.controller.api.ShoppingCartRestApi;
import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.ShoppingCartMapper;
import com.gitlab.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class ShoppingCartRestController implements ShoppingCartRestApi {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ResponseEntity<List<ShoppingCartDto>> getAll() {
        List<ShoppingCartDto> shoppingCarts = shoppingCartService.getAllShoppingCarts();
        return ResponseEntity.ok(shoppingCarts);
    }

    @Override
    public ResponseEntity<ShoppingCartDto> get(Long id) {
        Optional<ShoppingCartDto> shoppingCartDto = shoppingCartService.getShoppingCartById(id);
        return shoppingCartDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ShoppingCartDto> create(ShoppingCartDto shoppingCartDto) {
        ShoppingCartDto createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCartDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShoppingCart);
    }

    @Override
    public ResponseEntity<ShoppingCartDto> update(Long id, ShoppingCartDto shoppingCartDto) {
        Optional<ShoppingCartDto> updatedShoppingCart = shoppingCartService.updateShoppingCart(id, shoppingCartDto);
        return updatedShoppingCart.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        shoppingCartService.deleteShoppingCart(id);
        return ResponseEntity.noContent().build();
    }
}
