package com.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitlab.model.Payment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class PaymentDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Bank card should not be null. Please provide a valid bank card")
    private BankCardDto bankCardDto;

    @NotEmpty(message = "Payment status should not be null. Please provide a valid payment status")
    private Payment.PaymentStatus paymentStatus;

    @NotNull(message = "Local date time of creation should not be null. Please provide a valid local date time")
    private LocalDateTime createDateTime;

    @Range(min = 1, max = 2147483333, message = "Order Id should be between 1 and 2147483333")
    @NotNull(message = "Order Id should not be null. Please provide a valid order Id")
    private Long orderId;

    @DecimalMin(value = "0.1", message = "Sum should be between 0.1 and 2147483333")
    @DecimalMax(value = "2147483333", message = "Payment sum should be between 0.1 and 2147483333")
    @NotNull(message = "Sum should not be null. Please provide a valid sum")
    private BigDecimal sum;

    @Range(min = 1, max = 2147483333, message = "User Id should be between 1 and 2147483333")
    @NotNull(message = "User ID should not be null. Please provide a valid user ID")
    private Long userId;
}