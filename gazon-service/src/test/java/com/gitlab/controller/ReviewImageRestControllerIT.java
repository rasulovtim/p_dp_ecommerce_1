package com.gitlab.controller;

import com.gitlab.dto.ReviewImageDto;
import com.gitlab.mapper.ReviewImageMapper;
import com.gitlab.model.ReviewImage;
import com.gitlab.service.ReviewImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

class ReviewImageRestControllerIT extends AbstractIntegrationTest {

    private static final String REVIEW_IMAGE_URN = "/api/review_images";
    private static final String REVIEW_IMAGE_URI = URL + REVIEW_IMAGE_URN;
    @Autowired
    private ReviewImageService reviewImageService;
    @Autowired
    private ReviewImageMapper reviewImageMapper;

    @Test
    void should_get_all_reviewImages_ids() throws Exception {
        String expected = objectMapper.writeValueAsString(
                reviewImageService
                        .findAll().stream()
                        .map(ReviewImage::getId)
                        .mapToLong(Long::valueOf).toArray()
        );

        mockMvc.perform(get(REVIEW_IMAGE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void should_get_reviewImage_by_id() throws Exception {
        long id = 1L;
        String expected = objectMapper.writeValueAsString(
                reviewImageMapper.toDto(
                        reviewImageService
                                .findById(id)
                                .orElse(null))
        );

        mockMvc.perform(get(REVIEW_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isPartialContent())
                .andExpect(content().json(expected));
    }

    @Test
    void should_return_not_found_when_get_reviewImage_by_non_existent_id() throws Exception {
        long id = 10L;
        mockMvc.perform(get(REVIEW_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_update_reviewImage_by_id() throws Exception {
        long id = 1L;
        int numberOfEntitiesExpected = reviewImageService.findAll().size();

        ReviewImageDto reviewImageDto = generateReviewDto();
        reviewImageDto.setId(id);

        String expected = objectMapper.writeValueAsString(reviewImageDto);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(REVIEW_IMAGE_URI + "/{id}", id);
        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mockMvc.perform(builder
                        .file(new MockMultipartFile("file",
                                reviewImageDto.getName(),
                                MediaType.TEXT_PLAIN_VALUE, reviewImageDto.getData()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andExpect(result -> assertThat(reviewImageService.findAll().size(),
                        equalTo(numberOfEntitiesExpected)));
    }

    @Test
    void should_return_not_found_when_update_reviewImage_by_non_existent_id() throws Exception {
        long id = 10L;

        ReviewImageDto reviewImageDto = generateReviewDto();
        reviewImageDto.setId(id);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(REVIEW_IMAGE_URI + "/{id}", id);
        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mockMvc.perform(builder
                        .file(new MockMultipartFile("file",
                                reviewImageDto.getName(),
                                MediaType.TEXT_PLAIN_VALUE, reviewImageDto.getData()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_reviewImage_by_id() throws Exception {
        long id = 2L;
        mockMvc.perform(delete(REVIEW_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(REVIEW_IMAGE_URI + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private ReviewImageDto generateReviewDto() {
        ReviewImageDto reviewImageDto = new ReviewImageDto();
        reviewImageDto.setReviewId(1L);
        reviewImageDto.setName("file.txt");
        reviewImageDto.setData(new byte[]{1, 2, 3});

        return reviewImageDto;
    }
}