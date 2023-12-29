package com.gitlab.mapper;

import com.gitlab.dto.BankCardDto;
import com.gitlab.model.BankCard;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankCardMapper {

    BankCardDto toDto(BankCard bankCard);

    BankCard toEntity(BankCardDto bankCardDto);

    default List<BankCardDto> toDtoList(List<BankCard> bankCardList) {
        return bankCardList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<BankCard> toEntityList(List<BankCardDto> bankCardDtoList) {
        return bankCardDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}