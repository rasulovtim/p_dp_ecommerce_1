package com.gitlab.controller;

import com.gitlab.dto.ProductImageDto;
import com.gitlab.mapper.ProductImageMapper;
import com.gitlab.model.ProductImage;
import com.gitlab.service.ProductImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

class ProductImageRestControllerIT extends AbstractIntegrationTest {

    private static final String PRODUCT_IMAGE_URN = "/api/images";
    private static final String PRODUCT_IMAGE_URI = URL + PRODUCT_IMAGE_URN;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductImageMapper productImageMapper;

    @Test
    @Transactional(readOnly = true)
    void should_get_all_productImages() throws Exception {

        var response = productImageService.getPage(null, null);
        var expected = objectMapper.writeValueAsString(productImageMapper.toDtoList(response.getContent()));

        mockMvc.perform(get(PRODUCT_IMAGE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @Transactional(readOnly = true)
    void should_get_page() throws Exception {
        int page = 0;
        int size = 2;
        String parameters = "?page=" + page + "&size=" + size;

        var response = productImageService.getPage(page, size);
        assertFalse(response.getContent().isEmpty());

        var expected = objectMapper.writeValueAsString(productImageMapper.toDtoList(response.getContent()));

        mockMvc.perform(get(PRODUCT_IMAGE_URI + parameters))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_page_with_incorrect_parameters() throws Exception {
        int page = 0;
        int size = -2;
        String parameters = "?page=" + page + "&size=" + size;

        mockMvc.perform(get(PRODUCT_IMAGE_URI + parameters))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_page_without_content() throws Exception {
        int page = 10;
        int size = 100;
        String parameters = "?page=" + page + "&size=" + size;

        mockMvc.perform(get(PRODUCT_IMAGE_URI + parameters))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_productImage_by_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                productImageMapper.toDto(
                        productImageService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(PRODUCT_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isPartialContent())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_productImages_by_productId() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                        productImageService
                                .findAllByProductId(id)
                                .stream()
                                .map(productImageMapper::toDto)
                                .collect(Collectors.toList())
        );

        mockMvc.perform(get(PRODUCT_IMAGE_URI + "/product_id/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_productImage_by_non_existent_id() throws Exception {
        long id = 100L;
        mockMvc.perform(get(PRODUCT_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_productImage() throws Exception {
        ProductImageDto productImageDto = generateProductDto();

        String jsonProductImageDto = objectMapper.writeValueAsString(productImageDto);

        mockMvc.perform(post(PRODUCT_IMAGE_URI)
                        .content(jsonProductImageDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_productImage_by_id() throws Exception {
        long id = 1L;
        int numberOfEntitiesExpected = productImageService.findAll().size();

        ProductImageDto productImageDto = generateProductDto();
        productImageDto.setId(id);
        productImageDto.setName("updatedName");
        String jsonProductImageDto = objectMapper.writeValueAsString(productImageDto);
        String expected = objectMapper.writeValueAsString(productImageDto);

        mockMvc.perform(patch(PRODUCT_IMAGE_URI + "/{id}", id)
                        .content(jsonProductImageDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(productImageService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Test
    void should_return_not_found_when_update_productImage_by_non_existent_id() throws Exception {
        long id = 10L;

        ProductImageDto productImageDto = generateProductDto();
        productImageDto.setId(id);
        productImageDto.setName("updatedName");
        String jsonProductImageDto = objectMapper.writeValueAsString(productImageDto);

        mockMvc.perform(patch(PRODUCT_IMAGE_URI + "/{id}", id)
                        .content(jsonProductImageDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_productImage_by_id() throws Exception {
        ProductImage productImage = productImageService.save(productImageMapper.toEntity(generateProductDto()));
        long id = productImageService.findById(productImage.getId()).get().getId();
        mockMvc.perform(delete(PRODUCT_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(PRODUCT_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private ProductImageDto generateProductDto() {
        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setProductId(1L);
        productImageDto.setName("file.txt");
        productImageDto.setData(new byte[]{1, 2, 3});

        return productImageDto;
    }
}