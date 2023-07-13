package com.gitlab.controller;

import com.gitlab.dto.ProductDto;
import com.gitlab.mapper.ProductImageMapper;
import com.gitlab.mapper.ProductMapper;
import com.gitlab.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductRestControllerIT extends AbstractIntegrationTest {

    private static final String PRODUCT_URN = "/api/product";
    private static final String PRODUCT_URI = URL + PRODUCT_URN;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;

    @Test
    void should_get_all_products() throws Exception {
        String expected = objectMapper.writeValueAsString(
                productService
                        .findAll()
                        .stream()
                        .map(productMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(PRODUCT_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_product_by_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                productMapper.toDto(
                        productService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(PRODUCT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_product_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(PRODUCT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_product() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("name1");
        productDto.setStockCount(1);
        productDto.setDescription("name");
        productDto.setIsAdult(true);
        productDto.setCode("name");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        String jsonProductDto = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(post(PRODUCT_URI)
                        .content(jsonProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_product_by_id() throws Exception {
        long id = 1L;
        ProductDto productDto = new ProductDto();
        productDto.setName("name1");
        productDto.setStockCount(1);
        productDto.setImagesId(new Long[]{1L});
        productDto.setDescription("name");
        productDto.setIsAdult(true);
        productDto.setCode("name");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        String jsonProductDto = objectMapper.writeValueAsString(productDto);

        productDto.setId(id);
        String expected = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(patch(PRODUCT_URI + "/{id}", id)
                        .content(jsonProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_update_product_by_non_existent_id() throws Exception {
        long id = 10L;
        ProductDto productDto = new ProductDto();
        productDto.setName("name1");
        productDto.setStockCount(1);
        productDto.setImagesId(new Long[]{1L});
        productDto.setDescription("name");
        productDto.setIsAdult(true);
        productDto.setCode("name");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        String jsonProductDto = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(patch(PRODUCT_URI + "/{id}", id)
                        .content(jsonProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void should_delete_product_by_id() throws Exception {
        long id = 2L;
        mockMvc.perform(delete(PRODUCT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(PRODUCT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void should_get_all_productImages_by_product_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                productService
                        .findById(id).get()
                        .getProductImages().stream()
                        .map(productImageMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(PRODUCT_URI + "/{id}" + "/images", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }


    @Test
    void should_create_multiple_productImages_by_product_id() throws Exception {
        long id = 1L;

        mockMvc.perform(multipart(PRODUCT_URI + "/{id}" + "/images", id)
                        .file(new MockMultipartFile("files",
                                "hello.txt", MediaType.TEXT_PLAIN_VALUE, "hello".getBytes()))
                        .file(new MockMultipartFile("files",
                                "bye.txt", MediaType.TEXT_PLAIN_VALUE, "bye".getBytes()))

                        .accept(MediaType.ALL))
                .andExpect(status().isCreated());
    }


    @Test
    void should_delete_all_productImages_by_product_id() throws Exception {
        long id = 1L;
        mockMvc.perform(delete(PRODUCT_URI + "/{id}" + "/images", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(PRODUCT_URI + "/{id}" + "/images", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
