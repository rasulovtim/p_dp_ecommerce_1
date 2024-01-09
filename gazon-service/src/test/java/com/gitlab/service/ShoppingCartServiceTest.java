package com.gitlab.service;

import com.gitlab.model.Product;
import com.gitlab.model.SelectedProduct;
import com.gitlab.model.ShoppingCart;
import com.gitlab.model.User;
import com.gitlab.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    void should_find_all_shoppingCarts() {
        List<ShoppingCart> expectedResult = generateShoppingCarts();
        when(shoppingCartRepository.findAll()).thenReturn(generateShoppingCarts());

        List<ShoppingCart> actualResult = shoppingCartService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_shoppingCart_by_id() {
        long id = 1L;
        ShoppingCart expectedResult = generateShoppingCart();
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<ShoppingCart> actualResult = shoppingCartService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_shoppingCart() {
        ShoppingCart expectedResult = generateShoppingCart();
        when(shoppingCartRepository.save(expectedResult)).thenReturn(expectedResult);

        ShoppingCart actualResult = shoppingCartService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_shoppingCart() {
        long id = 2L;
        ShoppingCart shoppingCartToUpdate = generateShoppingCart();

        ShoppingCart shoppingCartBeforeUpdate = new ShoppingCart();
        shoppingCartBeforeUpdate.setId(id);
        shoppingCartBeforeUpdate.setUser(new User());
        shoppingCartBeforeUpdate.setSelectedProducts(new HashSet<>());

        ShoppingCart updatedShoppingCart = generateShoppingCart();
        updatedShoppingCart.setId(id);

        when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(shoppingCartBeforeUpdate));
        when(shoppingCartRepository.save(updatedShoppingCart)).thenReturn(updatedShoppingCart);

        Optional<ShoppingCart> actualResult = shoppingCartService.update(id, shoppingCartToUpdate);

        assertEquals(updatedShoppingCart, actualResult.orElse(null));
    }

    @Test
    void should_not_update_shoppingCart_when_entity_not_found() {
        long id = 1L;
        ShoppingCart shoppingCartToUpdate = generateShoppingCart();

        when(shoppingCartRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ShoppingCart> actualResult = shoppingCartService.update(id, shoppingCartToUpdate);

        verify(shoppingCartRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_delete_shoppingCart() {
        long id = 1L;
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(generateShoppingCart()));

        shoppingCartService.delete(id);

        verify(shoppingCartRepository).deleteById(id);
    }

    @Test
    void should_not_delete_shoppingCart_when_entity_not_found() {
        long id = 1L;
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.empty());

        shoppingCartService.delete(id);

        verify(shoppingCartRepository, never()).deleteById(anyLong());
    }

    private List<ShoppingCart> generateShoppingCarts() {
        return List.of(
                generateShoppingCart(1L),
                generateShoppingCart(2L),
                generateShoppingCart(3L),
                generateShoppingCart(4L),
                generateShoppingCart(5L)
        );
    }

    private ShoppingCart generateShoppingCart(Long id) {
        ShoppingCart shoppingCart = generateShoppingCart();
        shoppingCart.setId(id);
        return shoppingCart;
    }

    private ShoppingCart generateShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(new User());
        shoppingCart.setSelectedProducts(generateSelectedProducts());

        return shoppingCart;
    }

    private Set<SelectedProduct> generateSelectedProducts() {
        return Set.of(
                generateSelectedProduct(1L),
                generateSelectedProduct(2L)
        );
    }

    private SelectedProduct generateSelectedProduct(Long id) {
        SelectedProduct selectedProduct = new SelectedProduct();
        selectedProduct.setId(id);
        selectedProduct.setProduct(new Product());
        selectedProduct.setCount(1);
        return selectedProduct;
    }
}
