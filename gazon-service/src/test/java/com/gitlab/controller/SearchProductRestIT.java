package com.gitlab.controller;

import com.gitlab.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchProductRestIT extends AbstractIntegrationTest {

    private static final String PRODUCT_URN = "/api/search/product";
    private static final String PRODUCT_URI = URL + PRODUCT_URN;
    @Autowired
    private ProductService productService;

    @Test
    void should_get_product_by_id() throws Exception {
        String parameterValue = "prod";

        String expected = objectMapper.writeValueAsString(
                productService.findByNameIgnoreCaseContaining(parameterValue));

        mockMvc.perform(get(PRODUCT_URI + "?name=" + parameterValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}