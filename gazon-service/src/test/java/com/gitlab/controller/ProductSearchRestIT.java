package com.gitlab.controller;

import com.gitlab.dto.ProductDto;
import com.gitlab.mapper.ProductMapper;
import com.gitlab.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductSearchRestIT extends AbstractIntegrationTest {

    private static final String PRODUCT_URN = "/api/search";
    private static final String PRODUCT_URI = URL + PRODUCT_URN;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Test
    void should_get_product_by_id() throws Exception {

            ProductDto productDto = new ProductDto();
            productDto.setName("name1");
            productDto.setStockCount(1);
            productDto.setImagesId(productMapper.toDto(productService.findById(1L).get()).getImagesId());
            productDto.setDescription("name");
            productDto.setIsAdult(true);
            productDto.setCode("name");
            productDto.setWeight(1L);
            productDto.setPrice(BigDecimal.ONE);
            productService.saveDto(productDto);

        String expected = objectMapper.writeValueAsString(
                productService.findByNameIgnoreCaseContaining(productDto.getName()));


        mockMvc.perform(get(PRODUCT_URI + "?name=" + productDto.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}