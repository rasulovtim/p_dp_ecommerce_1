package com.gitlab.mapper;

import com.gitlab.dto.StoreDto;
import com.gitlab.model.Store;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StoreMapperTest {

    @Autowired
    private StoreMapper mapper = Mappers.getMapper(StoreMapper.class);

    @Test
    void should_map_store_to_Dto() {
        Store store = new Store();
        store.setId(1L);

        StoreDto actualResult = mapper.toDto(store);

        assertNotNull(actualResult);
        assertEquals(store.getId(), actualResult.getId());
    }

    @Test
    void should_map_StoreDto_to_Entity() {
        StoreDto storeDto = new StoreDto();
        storeDto.setId(1L);

        Store entityTwin = mapper.toEntity(storeDto);

        assertNotNull(entityTwin);
        assertEquals(storeDto.getId(), entityTwin.getId());
    }
}
