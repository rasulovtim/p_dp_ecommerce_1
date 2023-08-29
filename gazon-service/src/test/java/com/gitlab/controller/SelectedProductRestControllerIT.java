package com.gitlab.controller;

import com.gitlab.dto.SelectedProductDto;
import com.gitlab.mapper.SelectedProductMapper;
import com.gitlab.service.SelectedProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SelectedProductRestControllerIT extends AbstractIntegrationTest {

    private static final String SELECTED_PRODUCT_URN = "/api/selected_product";
    private static final String SELECTED_PRODUCT__URI = URL + SELECTED_PRODUCT_URN;
    @Autowired
    private SelectedProductService selectedProductService;
    @Autowired
    private SelectedProductMapper selectedProductMapper;

    @Test
    void should_get_all_selectedProducts() throws Exception {
        String expected = objectMapper.writeValueAsString(
                selectedProductService
                        .findAll()
                        .stream()
                        .map(selectedProductMapper::toDto)
                        .collect(Collectors.toList())
        );

        mockMvc.perform(get(SELECTED_PRODUCT__URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_selectedProduct_by_id() throws Exception {
        long id = 1L;
        var selectedProduct = selectedProductService.findById(id).orElse(null);
        var selectedProductDto = selectedProductMapper.toDto(selectedProduct);
        selectedProductMapper.calculatedUnmappedFields(selectedProductDto, selectedProduct);
        String expected = objectMapper.writeValueAsString(selectedProductDto);

        mockMvc.perform(get(SELECTED_PRODUCT__URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_selectedProduct_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(SELECTED_PRODUCT__URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_selectedProduct() throws Exception {
        SelectedProductDto selectedProductDto = generateSelectedProductDto();
        String jsonSelectedProductDto = objectMapper.writeValueAsString(selectedProductDto);

        mockMvc.perform(post(SELECTED_PRODUCT__URI)
                        .content(jsonSelectedProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_selectedProduct_by_id() throws Exception {
        long id = 1L;
        long numberOfEntitiesExpected = selectedProductService.findAll().size();

        SelectedProductDto selectedProductDto = generateSelectedProductDto();

        String jsonSelectedProductDto = objectMapper.writeValueAsString(selectedProductDto);

        selectedProductDto.setId(id);
        String expected = objectMapper.writeValueAsString(selectedProductDto);

        mockMvc.perform(patch(SELECTED_PRODUCT__URI + "/{id}", id)
                        .content(jsonSelectedProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

        mockMvc.perform(get(SELECTED_PRODUCT__URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(numberOfEntitiesExpected));
    }

    @Test
    void should_return_not_found_when_update_selectedProduct_by_non_existent_id() throws Exception {
        long id = 10L;
        SelectedProductDto selectedProductDto = generateSelectedProductDto();

        String jsonSelectedProductDto = objectMapper.writeValueAsString(selectedProductDto);

        mockMvc.perform(patch(SELECTED_PRODUCT__URI + "/{id}", id)
                        .content(jsonSelectedProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void should_delete_selectedProduct_by_id() throws Exception {
        long id = 3L;
        mockMvc.perform(delete(SELECTED_PRODUCT__URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(SELECTED_PRODUCT__URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    private SelectedProductDto generateSelectedProductDto() {
        SelectedProductDto selectedProduct = new SelectedProductDto();
        selectedProduct.setProductId(1L);
        selectedProduct.setCount(3);

        return selectedProduct;
    }
}

