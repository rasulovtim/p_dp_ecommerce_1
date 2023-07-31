package com.gitlab.service;

import com.gitlab.repository.UserRepository;
import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.model.ShoppingCart;
import com.gitlab.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;

    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> findById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    public void deleteById(Long id) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(id);
        if (optionalShoppingCart.isPresent()) {
            shoppingCartRepository.deleteById(id);
        } else {
            System.out.println("Shopping cart with ID " + id + " does not exist.");
        }
    }




    public Optional<ShoppingCart> update(Long id, ShoppingCart shoppingCart) {
        Optional<ShoppingCart> optionalSavedShoppingCart = findById(id);
        if (optionalSavedShoppingCart.isEmpty()) {
            return Optional.empty();
        }

        ShoppingCart savedShoppingCart = optionalSavedShoppingCart.get();

        // Обновление полей объекта savedShoppingCart на основе полей объекта shoppingCart
        savedShoppingCart.setUser(shoppingCart.getUser());
        savedShoppingCart.setSelectedProducts(shoppingCart.getSelectedProducts());
        savedShoppingCart.setSum(shoppingCart.getSum());
        savedShoppingCart.setTotalWeight(shoppingCart.getTotalWeight());

        savedShoppingCart = shoppingCartRepository.save(savedShoppingCart);
        return Optional.of(savedShoppingCart);
    }

    public List<ShoppingCartDto> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
        return shoppingCarts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    public Optional<ShoppingCartDto> getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id).map(this::mapToDto);
    }

    public ShoppingCartDto createShoppingCart(ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = mapToEntity(shoppingCartDto);
        shoppingCart.setUser(userService.findUserById(shoppingCartDto.getUserId()));
        shoppingCart = shoppingCartRepository.save(shoppingCart);
        return mapToDto(shoppingCart);
    }

    public Optional<ShoppingCartDto> updateShoppingCart(Long id, ShoppingCartDto shoppingCartDto) {
        Optional<ShoppingCart> existingShoppingCart = shoppingCartRepository.findById(id);
        if (existingShoppingCart.isEmpty()) {
            return Optional.empty();
        }

        ShoppingCart updatedShoppingCart = mapToEntity(shoppingCartDto);
        updatedShoppingCart.setId(id);


        if (userService.findUserById(shoppingCartDto.getUserId()) == null) {
            return Optional.empty();
        }

        updatedShoppingCart = shoppingCartRepository.save(updatedShoppingCart);
        return Optional.of(mapToDto(updatedShoppingCart));
    }

    private ShoppingCart mapToEntity(ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartDto.getId());
        shoppingCart.setSum(shoppingCartDto.getSum());
        shoppingCart.setTotalWeight(shoppingCartDto.getTotalWeight());
        shoppingCart.setSelectedProducts(shoppingCartDto.getSelectedProducts());

        if (shoppingCartDto.getUserId() != null) {
            shoppingCart.setUser(userRepository.findById(shoppingCartDto.getUserId()).orElse(null));
        }

        return shoppingCart;
    }

    private ShoppingCartDto mapToDto(ShoppingCart shoppingCart) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(shoppingCart.getId());
        shoppingCartDto.setSum(shoppingCart.getSum());
        shoppingCartDto.setTotalWeight(shoppingCart.getTotalWeight());
        shoppingCartDto.setSelectedProducts(shoppingCart.getSelectedProducts());

        if (shoppingCart.getUser() != null) {
            shoppingCartDto.setUserId(shoppingCart.getUser().getId());
        }

        return shoppingCartDto;
    }

}
