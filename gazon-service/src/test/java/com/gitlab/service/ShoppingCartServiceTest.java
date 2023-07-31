package com.gitlab.service;

import com.gitlab.model.ShoppingCart;
import com.gitlab.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Test
    void should_update_shoppingCart_when_exists() {
        long id = 1L;
        ShoppingCart originalShoppingCart = generateShoppingCart();
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(originalShoppingCart));

        ShoppingCart updatedShoppingCart = new ShoppingCart();
        updatedShoppingCart.setId(id);
        updatedShoppingCart.setSelectedProducts(Set.of("Product3", "Product4"));
        updatedShoppingCart.setSum(BigDecimal.valueOf(75.0));
        updatedShoppingCart.setTotalWeight(250L);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(updatedShoppingCart);

        Optional<ShoppingCart> actualShoppingCart = shoppingCartService.update(id, updatedShoppingCart);

        assertTrue(actualShoppingCart.isPresent());
        assertEquals(updatedShoppingCart, actualShoppingCart.get());

        verify(shoppingCartRepository, times(1)).findById(id);
        verify(shoppingCartRepository, times(1)).save(updatedShoppingCart);
    }

    @Test
    void should_not_update_shoppingCart_when_not_exists() {
        long id = 1L;
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.empty());

        ShoppingCart updatedShoppingCart = new ShoppingCart();
        updatedShoppingCart.setId(id);
        updatedShoppingCart.setSelectedProducts(Set.of("Product3", "Product4"));
        updatedShoppingCart.setSum(BigDecimal.valueOf(75.0));
        updatedShoppingCart.setTotalWeight(250L);

        Optional<ShoppingCart> actualShoppingCart = shoppingCartService.update(id, updatedShoppingCart);

        assertFalse(actualShoppingCart.isPresent());
        verify(shoppingCartRepository, times(1)).findById(id);
        verify(shoppingCartRepository, never()).save(any(ShoppingCart.class));
    }

    @Test
    void should_find_all_shoppingCarts() {
        List<ShoppingCart> expectedShoppingCarts = generateShoppingCarts();
        when(shoppingCartRepository.findAll()).thenReturn(expectedShoppingCarts);

        List<ShoppingCart> actualShoppingCarts = shoppingCartService.findAll();

        assertEquals(expectedShoppingCarts, actualShoppingCarts);
    }

    @Test
    void should_find_shoppingCart_by_id() {
        long id = 1L;
        ShoppingCart expectedShoppingCart = generateShoppingCart();
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(expectedShoppingCart));

        Optional<ShoppingCart> actualShoppingCart = shoppingCartService.findById(id);

        assertTrue(actualShoppingCart.isPresent());
        assertEquals(expectedShoppingCart, actualShoppingCart.get());
    }

    @Test
    void should_not_find_shoppingCart_when_not_exists() {
        long id = 1L;
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ShoppingCart> actualShoppingCart = shoppingCartService.findById(id);

        assertFalse(actualShoppingCart.isPresent());
    }

    @Test
    void should_delete_shoppingCart() {
        long id = 1L;

        when(shoppingCartRepository.findById(id)).thenReturn(Optional.empty());

        shoppingCartService.deleteById(id);


        verify(shoppingCartRepository, never()).deleteById(id);
    }




    @Test
    void should_not_delete_shoppingCart_when_not_exists() {
        long id = 1L;
        when(shoppingCartRepository.findById(id)).thenReturn(Optional.empty());

        shoppingCartService.deleteById(id);

        verify(shoppingCartRepository, never()).deleteById(anyLong());
    }



    private ShoppingCart generateShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setSelectedProducts(Set.of("Product1", "Product2"));
        shoppingCart.setSum(BigDecimal.valueOf(50.0));
        shoppingCart.setTotalWeight(200L);
        return shoppingCart;
    }

    private List<ShoppingCart> generateShoppingCarts() {
        return List.of(
                generateShoppingCart(),
                new ShoppingCart(2L, null, new HashSet<>(), BigDecimal.valueOf(100.0), 400L),
                new ShoppingCart(3L, null, new HashSet<>(), BigDecimal.valueOf(75.0), 300L)
        );
    }

}
