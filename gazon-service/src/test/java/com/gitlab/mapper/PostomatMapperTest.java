package com.gitlab.mapper;

import com.gitlab.dto.PostomatDto;
import com.gitlab.model.Postomat;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostomatMapperTest {

    private final PostomatMapper mapper = Mappers.getMapper(PostomatMapper.class);

    @Test
    void should_should_map_Postomat_to_Dto() {
        Postomat postomat = getPostomat(1L);

        PostomatDto actualResult = mapper.toDto(postomat);

        assertNotNull(actualResult);
        assertEquals(postomat.getId(), actualResult.getId());
        assertEquals(postomat.getAddress(), actualResult.getAddress());
        assertEquals(postomat.getDirections(), actualResult.getDirections());
        assertEquals(postomat.getShelfLifeDays(), actualResult.getShelfLifeDays());
    }

    @Test
    void should_should_map_PostomatDto_to_Entity() {
        PostomatDto postomatDto = getPostomatDto(1L);

        Postomat actualResult = mapper.toEntity(postomatDto);

        assertNotNull(actualResult);
        assertEquals(postomatDto.getId(), actualResult.getId());
        assertEquals(postomatDto.getAddress(), actualResult.getAddress());
        assertEquals(postomatDto.getDirections(), actualResult.getDirections());
        assertEquals(postomatDto.getShelfLifeDays(), actualResult.getShelfLifeDays());
    }

    @Test
    void should_map_postomatList_to_DtoList() {
        List<Postomat> postomatList = List.of(getPostomat(1L), getPostomat(2L), getPostomat(3L));

        List<PostomatDto> postomatDtoList = mapper.toDtoList(postomatList);

        assertNotNull(postomatDtoList);
        assertEquals(postomatList.size(), postomatList.size());
        for (int i = 0; i < postomatDtoList.size(); i++) {
            PostomatDto dto = postomatDtoList.get(i);
            Postomat entity = postomatList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getAddress(), entity.getAddress());
            assertEquals(dto.getDirections(), entity.getDirections());
            assertEquals(dto.getShelfLifeDays(), entity.getShelfLifeDays());
        }
    }

    @Test
    void should_map_postomatDtoList_to_EntityList() {
        List<PostomatDto> postomatDtoList = List.of(getPostomatDto(1L), getPostomatDto(2L), getPostomatDto(3L));

        List<Postomat> postomatList = mapper.toEntityList(postomatDtoList);

        assertNotNull(postomatList);
        assertEquals(postomatList.size(), postomatList.size());
        for (int i = 0; i < postomatList.size(); i++) {
            PostomatDto dto = postomatDtoList.get(i);
            Postomat entity = postomatList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getAddress(), entity.getAddress());
            assertEquals(dto.getDirections(), entity.getDirections());
            assertEquals(dto.getShelfLifeDays(), entity.getShelfLifeDays());
        }
    }

    @NotNull
    private Postomat getPostomat(Long id) {
        Postomat postomat = new Postomat();
        postomat.setId(id);
        postomat.setAddress("Moscow,Russia" + id);
        postomat.setDirections("Some directions" + id);
        postomat.setShelfLifeDays((byte) (5 / id));
        return postomat;
    }

    @NotNull
    private PostomatDto getPostomatDto(Long id) {
        PostomatDto postomatDto = new PostomatDto();
        postomatDto.setId(id);
        postomatDto.setAddress("Moscow,Russia" + id);
        postomatDto.setDirections("Some directions" + id);
        postomatDto.setShelfLifeDays((byte) (5 / id));
        return postomatDto;
    }
}
