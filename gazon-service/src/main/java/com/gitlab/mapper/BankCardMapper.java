package com.gitlab.mapper;

import com.gitlab.dto.BankCardDto;
import com.gitlab.model.BankCard;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankCardMapper {

    BankCardDto toDto(BankCard bankCard);

    BankCard toEntity(BankCardDto bankCardDto);

}