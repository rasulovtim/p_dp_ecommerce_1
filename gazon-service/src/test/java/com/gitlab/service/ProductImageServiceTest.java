package com.gitlab.service;

import com.gitlab.model.ProductImage;
import com.gitlab.repository.ProductImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductImageServiceTest {

    @Mock
    private ProductImageRepository productImageRepository;
    @InjectMocks
    private ProductImageService productImageService;

    @Test
    void should_find_all_productImages() {
        List<ProductImage> expectedResult = generateProductImages();
        when(productImageRepository.findAll()).thenReturn(generateProductImages());

        List<ProductImage> actualResult = productImageService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_productImage_by_id() {
        long id = 1L;
        ProductImage expectedResult = generateProductImage();
        when(productImageRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<ProductImage> actualResult = productImageService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_productImage() {
        ProductImage expectedResult = generateProductImage();
        when(productImageRepository.save(expectedResult)).thenReturn(expectedResult);

        ProductImage actualResult = productImageService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_productImage() {
        long id = 4L;
        ProductImage imageToUpdateWith = new ProductImage(22L, null, "name1", new byte[1]);

        ProductImage imageBeforeUpdate = new ProductImage(id, null, "n9", new byte[1]);
        ProductImage imageFromFuture = new ProductImage(id, null, "name1", new byte[1]);

        when(productImageRepository.findById(id)).thenReturn(Optional.of(imageBeforeUpdate));
        when(productImageRepository.save(imageFromFuture)).thenReturn(imageFromFuture);

        Optional<ProductImage> actualResult = productImageService.update(id, imageToUpdateWith);

        assertEquals(imageFromFuture, actualResult.orElse(null));
    }

    @Test
    void should_not_update_productImage_when_entity_not_found() {
        long id = 4L;
        ProductImage imageToUpdateWith = new ProductImage(22L, null, "name1", new byte[1]);

        when(productImageRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ProductImage> actualResult = productImageService.update(id, imageToUpdateWith);

        verify(productImageRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_delete_productImage() {
        long id = 1L;
        when(productImageRepository.findById(id)).thenReturn(Optional.of(generateProductImage()));

        productImageService.delete(id);

        verify(productImageRepository).deleteById(id);
    }

    @Test
    void should_not_delete_productImage_when_entity_not_found() {
        long id = 1L;
        when(productImageRepository.findById(id)).thenReturn(Optional.empty());

        productImageService.delete(id);

        verify(productImageRepository, never()).deleteById(anyLong());
    }

    private List<ProductImage> generateProductImages() {
        return List.of(
                new ProductImage(1L, null, "name1", new byte[1]),
                new ProductImage(1L, null, "name1", new byte[1]),
                new ProductImage(1L, null, "name1", new byte[1]),
                new ProductImage(1L, null, "name1", new byte[1]));
    }

    private ProductImage generateProductImage() {
        return new ProductImage(1L, null, "name1", new byte[1]);
    }

    @Test
    void should_not_updated_productImage_name_field_if_null() {
        long id = 1L;
        ProductImage imageToUpdateWith = new ProductImage(22L, null, "name1", new byte[1]);
        imageToUpdateWith.setName(null);

        ProductImage imageBeforeUpdate = new ProductImage(id, null, "n9", new byte[1]);

        when(productImageRepository.findById(id)).thenReturn(Optional.of(imageBeforeUpdate));
        when(productImageRepository.save(imageBeforeUpdate)).thenReturn(imageBeforeUpdate);

        Optional<ProductImage> actualResult = productImageService.update(id, imageToUpdateWith);

        verify(productImageRepository).save(imageBeforeUpdate);
        assertEquals(imageBeforeUpdate, actualResult.orElse(null));
    }

    @Test
    void should_not_updated_productImage_data_field_if_null() {
        long id = 1L;
        ProductImage imageToUpdateWith = new ProductImage(22L, null, "name1", new byte[1]);
        imageToUpdateWith.setData(null);

        ProductImage imageBeforeUpdate = new ProductImage(id, null, "n9", new byte[1]);

        when(productImageRepository.findById(id)).thenReturn(Optional.of(imageBeforeUpdate));
        when(productImageRepository.save(imageBeforeUpdate)).thenReturn(imageBeforeUpdate);

        Optional<ProductImage> actualResult = productImageService.update(id, imageToUpdateWith);

        verify(productImageRepository).save(imageBeforeUpdate);
        assertEquals(imageBeforeUpdate, actualResult.orElse(null));
    }
}
