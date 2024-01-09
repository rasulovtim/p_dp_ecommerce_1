package com.gitlab.service;

import com.gitlab.dto.ShoppingCartDto;
import com.gitlab.mapper.ShoppingCartMapper;
import com.gitlab.model.ShoppingCart;
import com.gitlab.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    
    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    public List<ShoppingCartDto> findAllDto() {
        return shoppingCartMapper.toDtoList(findAll());
    }

    public Optional<ShoppingCart> findById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    public Optional<ShoppingCartDto> findByIdDto(Long id) {
        return findById(id).map(shoppingCartMapper::toDto);
    }

    public Page<ShoppingCart> getPage(Integer page, Integer size) {
        if (page == null || size == null) {
            var shoppingCarts = findAll();
            if (shoppingCarts.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(shoppingCarts);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return shoppingCartRepository.findAll(pageRequest);
    }

    public Page<ShoppingCartDto> getPageDto(Integer page, Integer size) {

        if (page == null || size == null) {
            var shoppingCarts = findAllDto();
            if (shoppingCarts.isEmpty()) {
                return Page.empty();
            }
            return new PageImpl<>(shoppingCarts);
        }
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ShoppingCart> shoppingCartPage = shoppingCartRepository.findAll(pageRequest);
        return shoppingCartPage.map(shoppingCartMapper::toDto);
    }

    @Transactional
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public ShoppingCartDto saveDto(ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDto);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(savedShoppingCart);
    }

    @Transactional
    public Optional<ShoppingCart> update(Long id, ShoppingCart shoppingCart) {
        Optional<ShoppingCart> optionalShoppingCart = findById(id);
        if (optionalShoppingCart.isPresent()) {
            shoppingCart.setId(id);
            ShoppingCart updatedShoppingCart = shoppingCartRepository.save(shoppingCart);
            return Optional.of(updatedShoppingCart);
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<ShoppingCartDto> updateDto(Long id, ShoppingCartDto shoppingCartDto) {
        Optional<ShoppingCart> imageOptional = findById(id);
        if (imageOptional.isEmpty()) {
            return Optional.empty();
        }

        ShoppingCart currentShoppingCart = imageOptional.get();

        if (shoppingCartDto.getSelectedProducts() != null) {
            currentShoppingCart.setSelectedProducts(shoppingCartMapper.toEntity(shoppingCartDto).getSelectedProducts());
        }
        
        ShoppingCart updatedShoppingCart = shoppingCartRepository.save(currentShoppingCart);

        return Optional.of(shoppingCartMapper.toDto(updatedShoppingCart));
    }

    @Transactional
    public Optional<ShoppingCart> delete(Long id) {
        Optional<ShoppingCart> imageOptional = findById(id);
        if (imageOptional.isPresent()) {
            shoppingCartRepository.deleteById(id);
        }
        return imageOptional;
    }
}
