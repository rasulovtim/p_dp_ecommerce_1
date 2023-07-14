package com.gitlab.mapper;

import com.gitlab.dto.PostomatDto;
import com.gitlab.model.Postomat;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostomatMapperTest {

    private final PostomatMapper mapper = Mappers.getMapper(PostomatMapper.class);

    @Test
    void should_should_map_Postomat_to_Dto() {
        Postomat postomat = new Postomat();
        postomat.setId(1L);
        postomat.setAddress("Moscow,Russia");
        postomat.setDirections("Some directions");
        postomat.setShelfLifeDays((byte) 5);

        PostomatDto actualResult = mapper.toDto(postomat);

        assertNotNull(actualResult);
        assertEquals(postomat.getId(), actualResult.getId());
        assertEquals(postomat.getAddress(), actualResult.getAddress());
        assertEquals(postomat.getDirections(), actualResult.getDirections());
        assertEquals(postomat.getShelfLifeDays(), actualResult.getShelfLifeDays());
    }

    @Test
    void should_should_map_PostomatDto_to_Entity() {
        PostomatDto postomatDto = new PostomatDto();
        postomatDto.setId(1L);
        postomatDto.setAddress("Moscow,Russia");
        postomatDto.setDirections("Some directions");
        postomatDto.setShelfLifeDays((byte) 5);

        Postomat actualResult = mapper.toEntity(postomatDto);

        assertNotNull(actualResult);
        assertEquals(postomatDto.getId(), actualResult.getId());
        assertEquals(postomatDto.getAddress(), actualResult.getAddress());
        assertEquals(postomatDto.getDirections(), actualResult.getDirections());
        assertEquals(postomatDto.getShelfLifeDays(), actualResult.getShelfLifeDays());
    }
}
