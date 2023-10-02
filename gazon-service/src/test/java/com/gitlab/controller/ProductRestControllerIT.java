package com.gitlab.controller;

import com.gitlab.dto.ProductDto;
import com.gitlab.model.Product;
import com.gitlab.model.ProductImage;
import com.gitlab.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import java.math.BigDecimal;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

class ProductRestControllerIT extends AbstractIntegrationTest {

    private static final String PRODUCT_URN = "/api/product";
    private static final String PRODUCT_URI = URL + PRODUCT_URN;
    @Autowired
    private ProductService productService;

    @Test
    void should_get_all_products() throws Exception {
        String expected = objectMapper.writeValueAsString(
                productService
                        .findAllDto()
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
                productService.findByIdDto(id).orElse(null)
        );

        mockMvc.perform(get(PRODUCT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_product_by_non_existent_id() throws Exception {
        long id = -10L;
        mockMvc.perform(get(PRODUCT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_product() throws Exception {
        ProductDto productDto = generateProductDTO();
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
        int numberOfEntitiesExpected = productService.findAll().size();
        ProductDto productDto = generateProductDTO();
        productDto.setRating(productService.findByIdDto(id).get().getRating());
        String jsonProductDto = objectMapper.writeValueAsString(productDto);
        productDto.setId(id);
        String expected = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(patch(PRODUCT_URI + "/{id}", id)
                        .content(jsonProductDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(productService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));

    }

    @Test
    void should_return_not_found_when_update_product_by_non_existent_id() throws Exception {
        long id = -10L;
        ProductDto productDto = generateProductDTO();
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
        ProductDto productDto = productService.saveDto(generateProductDTO());
        long id = productDto.getId();
        mockMvc.perform(delete(PRODUCT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(PRODUCT_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_images_ids_by_product_id() throws Exception {
        long id = 3L;
        Optional<Product> product = productService.findById(id);
        assert product.orElse(null) != null;

        String expected = objectMapper.writeValueAsString(
                product.orElse(null).getProductImages().stream()
                        .map(ProductImage::getId).mapToLong(Long::valueOf).toArray()
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

    private ProductDto generateProductDTO() {
        ProductDto productDto = new ProductDto();
        productDto.setName("name1");
        productDto.setStockCount(1);
        productDto.setImagesId(new Long[]{1L, 2L});
        productDto.setDescription("name");
        productDto.setIsAdult(true);
        productDto.setCode("name");
        productDto.setWeight(1L);
        productDto.setPrice(BigDecimal.ONE);
        return productDto;
    }
}