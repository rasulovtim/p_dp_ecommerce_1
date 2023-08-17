package com.gitlab.service;

import com.gitlab.model.ShoppingCart;
import com.gitlab.model.User;
import com.gitlab.repository.ShoppingCartRepository;
import com.gitlab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    public List<ShoppingCart> findAll() {
        log.info("Getting all shopping carts");
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> findById(Long id) {
        log.info("Getting shopping cart by ID: {}", id);
        return shoppingCartRepository.findById(id);
    }

    /*
    @Transactional
public Optional<ShoppingCart> findById(Long id) {
    log.info("Getting shopping cart by ID: {}", id);
    Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(id);

    optionalShoppingCart.ifPresent(shoppingCart -> {
        Hibernate.initialize(shoppingCart.getSelectedProducts());
    });

    return optionalShoppingCart;
}

     */

    @Transactional
    public ShoppingCart save(ShoppingCart shoppingCart) {
        log.info("Saving shopping cart: {}", shoppingCart);


        User user = userRepository.findById(shoppingCart.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public Optional<ShoppingCart> update(Long id, ShoppingCart shoppingCart) {
        log.info("Updating shopping cart with ID {}: {}", id, shoppingCart);
        Optional<ShoppingCart> optionalShoppingCart = findById(id);
        if (optionalShoppingCart.isPresent()) {
            shoppingCart.setId(id);
            ShoppingCart updatedShoppingCart = shoppingCartRepository.save(shoppingCart);
            log.info("Updated shopping cart: {}", updatedShoppingCart);
            return Optional.of(updatedShoppingCart);
        }
        log.warn("Shopping cart with ID {} not found for update", id);
        return Optional.empty();
    }

    @Transactional
    public boolean delete(Long id) {
        log.info("Deleting shopping cart with ID: {}", id);
        Optional<ShoppingCart> optionalShoppingCart = findById(id);

        if (optionalShoppingCart.isPresent()) {
            shoppingCartRepository.deleteById(id);
            log.info("Shopping cart with ID {} deleted", id);
            return true;
        }

        log.warn("Shopping cart with ID {} not found for deletion", id);
        return false;
    }
}
