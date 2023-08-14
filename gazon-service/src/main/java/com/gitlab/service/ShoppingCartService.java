package com.gitlab.service;

import com.gitlab.model.ShoppingCart;
import com.gitlab.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> findById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    @Transactional
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public Optional<ShoppingCart> update(Long id, ShoppingCart shoppingCart) {
        Optional<ShoppingCart> optionalShoppingCart = findById(id);
        if (optionalShoppingCart.isPresent()) {
            shoppingCart.setId(id);
            return Optional.of(shoppingCartRepository.save(shoppingCart));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<ShoppingCart> optionalShoppingCart = findById(id);

        if (optionalShoppingCart.isPresent()) {
            shoppingCartRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
