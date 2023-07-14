package com.gitlab.service;

import com.gitlab.model.Postomat;
import com.gitlab.repository.PostomatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostomatServiceTest {

    @Mock
    private PostomatRepository postomatRepository;
    @InjectMocks
    private PostomatService postomatService;

    @Test
    void should_find_all_postomats() {
        List<Postomat> expectedResult = generatePostomats();
        when(postomatRepository.findAll()).thenReturn(generatePostomats());

        List<Postomat> actualResult = postomatService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_postomat_by_id() {
        long id = 1L;
        Postomat expectedResult = generatePostomat();
        when(postomatRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<Postomat> actualResult = postomatService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_postomat() {
        Postomat expectedResult = generatePostomat();
        when(postomatRepository.save(expectedResult)).thenReturn(expectedResult);

        Postomat actualResult = postomatService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_postomat() {
        long id = 1L;
        Postomat postomatToUpdate = new Postomat();
        postomatToUpdate.setId(id);
        postomatToUpdate.setAddress("modifiedText");
        postomatToUpdate.setDirections("modifiedText");
        postomatToUpdate.setShelfLifeDays((byte) 2);

        Postomat postomatBeforeUpdate = new Postomat();
        postomatBeforeUpdate.setId(id);
        postomatBeforeUpdate.setAddress("unmodifiedText");
        postomatBeforeUpdate.setDirections("unmodifiedText");
        postomatBeforeUpdate.setShelfLifeDays((byte) 1);

        Postomat updatedPostomat = new Postomat();
        updatedPostomat.setId(id);
        updatedPostomat.setAddress("modifiedText");
        updatedPostomat.setDirections("modifiedText");
        updatedPostomat.setShelfLifeDays((byte) 2);

        when(postomatRepository.findById(id)).thenReturn(Optional.of(postomatBeforeUpdate));
        when(postomatRepository.save(updatedPostomat)).thenReturn(updatedPostomat);

        Optional<Postomat> actualResult = postomatService.update(id, postomatToUpdate);

        assertEquals(updatedPostomat, actualResult.orElse(null));
    }

    @Test
    void should_not_update_postomat_when_entity_not_found() {
        long id = 1L;
        Postomat postomatToUpdate = generatePostomat();

        when(postomatRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Postomat> actualResult = postomatService.update(id, postomatToUpdate);

        verify(postomatRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_update_address_field_if_null() {
        long id = 1L;
        Postomat postomatToUpdate = generatePostomat();
        postomatToUpdate.setAddress(null);

        Postomat postomatBeforeUpdate = generatePostomat();

        when(postomatRepository.findById(id)).thenReturn(Optional.of(postomatBeforeUpdate));
        when(postomatRepository.save(postomatBeforeUpdate)).thenReturn(postomatBeforeUpdate);

        Optional<Postomat> actualResult = postomatService.update(id, postomatToUpdate);

        verify(postomatRepository).save(postomatBeforeUpdate);
        assertNotNull(actualResult.orElse(postomatBeforeUpdate).getAddress());
    }

    @Test
    void should_not_update_shelfLifeDays_field_if_null() {
        long id = 1L;
        Postomat postomatToUpdate = generatePostomat();
        postomatToUpdate.setShelfLifeDays(null);

        Postomat postomatBeforeUpdate = generatePostomat();

        when(postomatRepository.findById(id)).thenReturn(Optional.of(postomatBeforeUpdate));
        when(postomatRepository.save(postomatBeforeUpdate)).thenReturn(postomatBeforeUpdate);

        Optional<Postomat> actualResult = postomatService.update(id, postomatToUpdate);

        verify(postomatRepository).save(postomatBeforeUpdate);
        assertNotNull(actualResult.orElse(postomatBeforeUpdate).getShelfLifeDays());
    }

    @Test
    void should_not_update_directions_field_if_null() {
        long id = 1L;
        Postomat postomatToUpdate = generatePostomat();
        postomatToUpdate.setDirections(null);

        Postomat postomatBeforeUpdate = generatePostomat();

        when(postomatRepository.findById(id)).thenReturn(Optional.of(postomatBeforeUpdate));
        when(postomatRepository.save(postomatBeforeUpdate)).thenReturn(postomatBeforeUpdate);

        Optional<Postomat> actualResult = postomatService.update(id, postomatToUpdate);

        verify(postomatRepository).save(postomatBeforeUpdate);
        assertNotNull(actualResult.orElse(postomatBeforeUpdate).getDirections());
    }

    @Test
    void should_delete_postomat() {
        long id = 1L;
        when(postomatRepository.findById(id)).thenReturn(Optional.of(generatePostomat()));

        postomatService.delete(id);

        verify(postomatRepository).deleteById(id);
    }

    @Test
    void should_not_delete_postomat_when_entity_not_found() {
        long id = 1L;
        when(postomatRepository.findById(id)).thenReturn(Optional.empty());

        postomatService.delete(id);

        verify(postomatRepository, never()).deleteById(anyLong());
    }

    private List<Postomat> generatePostomats() {
        return List.of(
                generatePostomat(1L),
                generatePostomat(2L),
                generatePostomat(3L),
                generatePostomat(4L),
                generatePostomat(5L)
        );
    }

    private Postomat generatePostomat(Long id) {
        Postomat postomat = generatePostomat();
        postomat.setId(id);
        return postomat;
    }

    private Postomat generatePostomat() {
        Postomat postomat = new Postomat();
        postomat.setId(1L);
        postomat.setAddress("Test Address");
        postomat.setDirections("Test Directions");
        postomat.setShelfLifeDays((byte) 5);
        return postomat;
    }
}
