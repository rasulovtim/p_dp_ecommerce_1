package com.gitlab.mapper;

import com.gitlab.dto.StoreDto;
import com.gitlab.model.Store;
import com.gitlab.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class StoreMapper {

    @Mapping(target = "ownerId", source = "owner")
    @Mapping(target = "managersId", source = "managers")
    public abstract StoreDto toDto(Store store);

    @Mapping(target = "owner", source = "ownerId")
    @Mapping(target = "managers", source = "managersId")
    public abstract Store toEntity(StoreDto storeDto);

    public Long mapUserToOwnerId(User user) {
        return user != null ? user.getId() : null;
    }

    public User mapOwnerIdToUser(Long ownerId) {
        if (ownerId == null) {
            return null;
        }

        User user = new User();
        user.setId(ownerId);
        return user;
    }

    public abstract Set<User> map(Set<Long> managersId);

    public List<StoreDto> toDtoList(List<Store> storeList) {
        return storeList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Store> toEntityList(List<StoreDto> storeDtoList) {
        return storeDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
