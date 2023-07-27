package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitlab.model.Review;
import com.gitlab.service.ReviewService;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Component
public class ProductDto {
    @Autowired
    protected ReviewService reviewService;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotEmpty(message = "Product's name should not be empty")
    @Size(min = 3, max = 256, message = "Length of Product's name should be between 3 and 256 characters")
    private String name;

    @Range(min = 1, max = 2147483333, message = "Product's stockCount should be between 1 and 2147483333")
    @NotNull(message = "Product's stockCount should not be empty")
    private Integer stockCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long[] imagesId;

    @NotEmpty(message = "Product's description should not be empty")
    @Size(min = 3, max = 1000, message = "Length of Product's description should be between 3 and 1000 characters")
    private String description;

    @NotNull(message = "Product's isAdult field should not be empty")
    private Boolean isAdult;

    @NotEmpty(message = "Product's code should not be empty")
    @Size(min = 2, max = 256, message = "Length of Product's code should be between 2 and 256 characters")
    private String code;

    @Range(min = 1, max = 2147483333, message = "Product's weight should be between 1 and 2147483333")
    @NotNull(message = "Product's weight should not be empty")
    private Long weight;

    @DecimalMin(value = "0.1", message = "Product's price should be between 0.1 and 2147483333")
    @DecimalMax(value = "2147483333", message = "Product's price should be between 0.1 and 2147483333")
    @NotNull(message = "Product's price should not be empty")
    private BigDecimal price;

    private Byte rating ;

//    public Byte getDynamicAverage() {
//        int sum = 0;
//        int count = 0;
//
//     //   assert reviewService != null;
//        for (Review review : reviewService.findAll()) {
//            if (review.getRating() != null) {
//                sum += review.getRating();
//                count++;
//            }
//        }
//
//        if (count > 0) {
//            double average = (double) sum / count;
//            return (byte) Math.round(average);
//        } else {
//            return null;
//        }
    }


