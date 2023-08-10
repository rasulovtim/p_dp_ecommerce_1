package com.gitlab.service;

import com.gitlab.model.ShoppingCart;
import com.gitlab.model.User;
import com.gitlab.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;

    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    public ShoppingCart createShoppingCartForUser(ShoppingCart shoppingCart) {
        Long userId = shoppingCart.getUserId();
        Optional<User> optionalUser = userService.findById(userId);

        if (optionalUser.isEmpty()) {
            User newUser = new User();
            newUser.setId(userId);
            User savedUser = userService.save(newUser);
            shoppingCart.setUser(savedUser);
        } else {
            shoppingCart.setUser(optionalUser.get());
        }

        return shoppingCartRepository.save(shoppingCart);
    }



    public Optional<ShoppingCart> updateShoppingCart(Long id, ShoppingCart shoppingCart) {
        Optional<ShoppingCart> existingShoppingCart = shoppingCartRepository.findById(id);
        if (existingShoppingCart.isEmpty()) {
            return Optional.empty();
        }
        shoppingCart.setId(id);
        shoppingCart.setSelectedProducts(existingShoppingCart.get().getSelectedProducts());
        shoppingCart.setUser(existingShoppingCart.get().getUser());
        return Optional.of(shoppingCartRepository.save(shoppingCart));
    }

    public boolean deleteShoppingCart(Long id) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(id);
        if (shoppingCart.isPresent()) {
            shoppingCartRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
