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
import org.hibernate.Hibernate;
import java.util.stream.Collectors;


import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartRestController implements ShoppingCartRestApi {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final EntityManager entityManager;

    @Override
    public ResponseEntity<List<ShoppingCartDto>> getAll() {
        log.info("Received request to get all shopping carts");

        List<ShoppingCart> shoppingCarts = shoppingCartService.findAll();

        List<ShoppingCartDto> shoppingCartDtos = shoppingCarts.stream()
                .map(shoppingCart -> shoppingCartMapper.toDto(shoppingCart))
                .collect(Collectors.toList());

        return shoppingCartDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(shoppingCartDtos);
    }


    @Override
    public ResponseEntity<ShoppingCartDto> get(Long id) {
        log.info("Received request to get shopping cart with ID: {}", id);

        Optional<ShoppingCart> shoppingCartOptional = shoppingCartService.findById(id);

        if (shoppingCartOptional.isEmpty()) {
            log.warn("Shopping cart with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }

        ShoppingCart shoppingCart = shoppingCartOptional.get();
        Hibernate.initialize(shoppingCart.getSelectedProducts());

        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);

        log.info("Found shopping cart with ID {}: {}", id, shoppingCartDto);
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartDto);
    }

    @Override
    public ResponseEntity<ShoppingCartDto> create(ShoppingCartDto shoppingCartDto) {
        log.info("Received request to create shopping cart: {}", shoppingCartDto);

        ShoppingCart shoppingCartEntity = shoppingCartMapper.toEntity(shoppingCartDto);
        ShoppingCart savedShoppingCart = shoppingCartService.save(shoppingCartEntity);

        Hibernate.initialize(savedShoppingCart.getSelectedProducts());

        ShoppingCartDto savedShoppingCartDto = shoppingCartMapper.toDto(savedShoppingCart);

        log.info("Created shopping cart: {}", savedShoppingCartDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShoppingCartDto);
    }

    @Override
    public ResponseEntity<ShoppingCartDto> update(Long id, ShoppingCartDto shoppingCartDto) {
        log.info("Received request to update shopping cart with ID {}: {}", id, shoppingCartDto);

        return shoppingCartService.update(id, shoppingCartMapper.toEntity(shoppingCartDto))
                .map(cart -> {
                    Hibernate.initialize(cart.getSelectedProducts());

                    ShoppingCartDto updatedCartDto = shoppingCartMapper.toDto(cart);
                    log.info("Updated shopping cart with ID {}: {}", id, updatedCartDto);
                    return ResponseEntity.ok(updatedCartDto);
                })
                .orElseGet(() -> {
                    log.warn("Shopping cart with ID {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        log.info("Received request to delete shopping cart with ID: {}", id);
        shoppingCartService.delete(id);
        log.info("Deleted shopping cart with ID: {}", id);
        return ResponseEntity.ok().build();
    }
}

