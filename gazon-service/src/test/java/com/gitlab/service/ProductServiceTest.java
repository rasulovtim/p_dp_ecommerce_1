package com.gitlab.service;

import com.gitlab.model.Product;
import com.gitlab.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void should_find_all_products() {
        List<Product> expectedResult = generateProducts();
        when(productRepository.findAll()).thenReturn(generateProducts());

        List<Product> actualResult = productService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_product_by_id() {
        long id = 1L;
        Product expectedResult = generateProduct();
        when(productRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<Product> actualResult = productService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_product() {
        Product expectedResult = generateProduct();
        when(productRepository.save(expectedResult)).thenReturn(expectedResult);

        Product actualResult = productService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_product() {
        long id = 4L;
        Product productToUpdateWith = new Product();
        productToUpdateWith.setName("name1");
        productToUpdateWith.setStockCount(1);
        productToUpdateWith.setDescription("name");
        productToUpdateWith.setIsAdult(true);
        productToUpdateWith.setCode("name");
        productToUpdateWith.setWeight(1L);
        productToUpdateWith.setPrice(BigDecimal.ONE);

        Product productBeforeUpdate = new Product(id, "n1", 1, null, "name", true, "name", 1L, BigDecimal.ONE);
        Product productFromFuture = new Product(id, "name1", 1, null, "name", true, "name", 1L, BigDecimal.ONE);

        when(productRepository.findById(id)).thenReturn(Optional.of(productBeforeUpdate));
        when(productRepository.save(productFromFuture)).thenReturn(productFromFuture);

        Optional<Product> actualResult = productService.update(id, productToUpdateWith);

        assertEquals(productFromFuture, actualResult.orElse(null));
    }

    @Test
    void should_not_update_product_when_entity_not_found() {
        long id = 4L;
        Product productToUpdateWith = new Product();
        productToUpdateWith.setName("name1");
        productToUpdateWith.setStockCount(1);
        productToUpdateWith.setDescription("name");
        productToUpdateWith.setIsAdult(true);
        productToUpdateWith.setCode("name");
        productToUpdateWith.setWeight(1L);
        productToUpdateWith.setPrice(BigDecimal.ONE);

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> actualResult = productService.update(id, productToUpdateWith);

        verify(productRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_delete_product() {
        long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.of(generateProduct()));

        productService.delete(id);

        verify(productRepository).deleteById(id);
    }

    @Test
    void should_not_delete_product_when_entity_not_found() {
        long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        productService.delete(id);

        verify(productRepository, never()).deleteById(anyLong());
    }

    private List<Product> generateProducts() {
        return List.of(
                new Product(1L, "name1", 1, null, "name", true, "name", 1L, BigDecimal.ONE),
                new Product(2L, "name2", 1, null, "name", true, "name", 1L, BigDecimal.ONE),
                new Product(3L, "name3", 1, null, "name", true, "name", 1L, BigDecimal.ONE),
                new Product(4L, "name4", 1, null, "name", true, "name", 1L, BigDecimal.ONE),
                new Product(5L, "name5", 1, null, "name", true, "name", 1L, BigDecimal.ONE));
    }

    private Product generateProduct() {
        return new Product(1L, "name1", 1, null, "name", true, "name", 1L, BigDecimal.ONE);
    }

    @Test
    void should_not_updated_product_name_field_if_null() {
        long id = 1L;
        Product productToUpdateWith = new Product(23L, "name1", 1, null, "name", true, "name", 1L, BigDecimal.ONE);
        productToUpdateWith.setName(null);

        Product productBeforeUpdate = new Product(id, "n1", 1, null, "name", true, "name", 1L, BigDecimal.ONE);

        when(productRepository.findById(id)).thenReturn(Optional.of(productBeforeUpdate));
        when(productRepository.save(productBeforeUpdate)).thenReturn(productBeforeUpdate);

        Optional<Product> actualResult = productService.update(id, productToUpdateWith);

        verify(productRepository).save(productBeforeUpdate);
        assertEquals(productBeforeUpdate, actualResult.orElse(null));
    }

    @Test
    void should_not_updated_product_stockCount_field_if_null() {
        long id = 1L;
        Product productToUpdateWith = new Product(23L, "name1", 1, null, "name", true, "name", 1L, BigDecimal.ONE);
        productToUpdateWith.setStockCount(null);

        Product productBeforeUpdate = new Product(id, "n1", 1, null, "name", true, "name", 1L, BigDecimal.ONE);

        when(productRepository.findById(id)).thenReturn(Optional.of(productBeforeUpdate));
        when(productRepository.save(productBeforeUpdate)).thenReturn(productBeforeUpdate);

        Optional<Product> actualResult = productService.update(id, productToUpdateWith);

        verify(productRepository).save(productBeforeUpdate);
        assertEquals(productBeforeUpdate, actualResult.orElse(null));
    }

    @Test
    void should_not_updated_product_description_field_if_null() {
        long id = 1L;
        Product productToUpdateWith = new Product(23L, "name1", 1, null, "name", true, "name", 1L, BigDecimal.ONE);
        productToUpdateWith.setDescription(null);

        Product productBeforeUpdate = new Product(id, "n1", 1, null, "name", true, "name", 1L, BigDecimal.ONE);

        when(productRepository.findById(id)).thenReturn(Optional.of(productBeforeUpdate));
        when(productRepository.save(productBeforeUpdate)).thenReturn(productBeforeUpdate);

        Optional<Product> actualResult = productService.update(id, productToUpdateWith);

        verify(productRepository).save(productBeforeUpdate);
        assertEquals(productBeforeUpdate, actualResult.orElse(null));
    }

}
