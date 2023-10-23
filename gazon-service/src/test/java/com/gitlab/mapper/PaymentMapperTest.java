//package com.gitlab.mapper;
//
//import com.gitlab.dto.ExampleDto;
//import com.gitlab.model.Example;
//import com.gitlab.model.Payment;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//class PaymentMapperTest {
//
//    private final PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);
//
//    @Test
//    void should_map_payment_to_Dto() {
//        Payment payment = new Payment();
//        payment.setId(1L);
//        payment.setExampleText("text");
//
//        ExampleDto actualResult = mapper.toDto(example);
//
//        assertNotNull(actualResult);
//        assertEquals(example.getId(), actualResult.getId());
//        assertEquals(example.getExampleText(), actualResult.getExampleText());
//    }
//
//    @Test
//    void should_map_exampleDto_to_Entity() {
//        ExampleDto exampleDto = new ExampleDto();
//        exampleDto.setId(1L);
//        exampleDto.setExampleText("text");
//
//        Example actualResult = mapper.toEntity(exampleDto);
//
//        assertNotNull(actualResult);
//        assertEquals(exampleDto.getId(), actualResult.getId());
//        assertEquals(exampleDto.getExampleText(), actualResult.getExampleText());
//    }
//}