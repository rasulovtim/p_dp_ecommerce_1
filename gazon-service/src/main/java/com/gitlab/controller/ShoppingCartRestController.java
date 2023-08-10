package com.gitlab.controller;

import com.gitlab.controller.api.ShoppingCartRestApi;
import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.ShoppingCartMapper;
import com.gitlab.model.ShoppingCart;
import com.gitlab.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
public class ShoppingCartRestController implements ShoppingCartRestApi {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ResponseEntity<ShoppingCartDto> get(Long id) {
        return shoppingCartService.getShoppingCartById(id)
                .map(shoppingCart -> ResponseEntity.ok(shoppingCartMapper.toDto(shoppingCart)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ShoppingCartDto> create(ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(shoppingCartMapper.toDto(shoppingCartService.createShoppingCartForUser(shoppingCart)));
    }


    @Override
    public ResponseEntity<ShoppingCartDto> update(Long id, ShoppingCartDto shoppingCartDto) {
        Optional<ShoppingCart> updatedShoppingCart = shoppingCartService.updateShoppingCart(id, shoppingCartMapper.toEntity(shoppingCartDto));
        return updatedShoppingCart
                .map(shoppingCart -> ResponseEntity.ok(shoppingCartMapper.toDto(shoppingCart)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        if (shoppingCartService.deleteShoppingCart(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public List<ShoppingCartDto> getAll() {
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllShoppingCarts();
        return shoppingCarts.stream()
                .map(shoppingCartMapper::toDto)
                .collect(Collectors.toList());
    }

}
