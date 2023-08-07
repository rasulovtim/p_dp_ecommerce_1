package com.gitlab.controller;

import com.gitlab.controller.api.ShoppingCartRestApi;
import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.ShoppingCartMapper;
import com.gitlab.model.ShoppingCart;
import com.gitlab.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Validated
@RestController
@RequiredArgsConstructor
public class ShoppingCartRestController implements ShoppingCartRestApi {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ResponseEntity<ShoppingCartDto> getById(Long id) {
        return shoppingCartService.getShoppingCartById(id)
                .map(shoppingCart -> ResponseEntity.ok(shoppingCartMapper.toDto(shoppingCart)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ShoppingCartDto> create(ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDto);
        ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCart);
        return ResponseEntity.ok(shoppingCartMapper.toDto(createdShoppingCart));
    }

    @Override
    public ResponseEntity<ShoppingCartDto> update(Long id, ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDto);
        Optional<ShoppingCart> updatedShoppingCart = shoppingCartService.updateShoppingCart(id, shoppingCart);
        return updatedShoppingCart.map(cart -> ResponseEntity.ok(shoppingCartMapper.toDto(cart)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        boolean deleted = shoppingCartService.deleteShoppingCart(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
