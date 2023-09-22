package com.gitlab.controller;

import com.gitlab.dto.ProductImageDto;
import com.gitlab.mapper.ProductImageMapper;
import com.gitlab.model.ProductImage;
import com.gitlab.service.ProductImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

class ImageRestControllerIT extends AbstractIntegrationTest {

    private static final String PRODUCT_IMAGE_URN = "/api/images";
    private static final String PRODUCT_IMAGE_URI = URL + PRODUCT_IMAGE_URN;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductImageMapper productImageMapper;

    @Test
    void should_get_all_productImages_ids() throws Exception {
        String expected = objectMapper.writeValueAsString(
                productImageService
                        .findAll()
                        .stream()
                        .map(ProductImage::getId)
                        .mapToLong(Long::valueOf).toArray()
        );

        mockMvc.perform(get(PRODUCT_IMAGE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
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
    void should_return_not_found_when_get_productImage_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(PRODUCT_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_productImage() throws Exception {
        ProductImageDto productImageDto = generateProductImageDto();
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

        ProductImageDto productImageDto = generateProductImageDto();
        productImageDto.setId(id);

        String expected = objectMapper.writeValueAsString(productImageDto);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(PRODUCT_IMAGE_URI + "/{id}", id);
        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mockMvc.perform(builder
                        .file(new MockMultipartFile("file",
                                "file.txt", MediaType.TEXT_PLAIN_VALUE, productImageDto.getData()))
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

        ProductImageDto productImageDto = generateProductImageDto();
        productImageDto.setId(id);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(PRODUCT_IMAGE_URI + "/{id}", id);
        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mockMvc.perform(builder
                        .file(new MockMultipartFile("file",
                                "file.txt", MediaType.TEXT_PLAIN_VALUE, productImageDto.getData()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void should_delete_productImage_by_id() throws Exception {
        ProductImageDto productImageDto = productImageService.saveDto(generateProductImageDto());
        long id = productImageDto.getId();

        mockMvc.perform(delete(PRODUCT_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(PRODUCT_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private ProductImageDto generateProductImageDto() {
        ProductImageDto productImageDto = new ProductImageDto();

        productImageDto.setProductId(1L);
        productImageDto.setName("file.txt");
        productImageDto.setData(new byte[]{1, 2, 3});
         return productImageDto;

    }
}