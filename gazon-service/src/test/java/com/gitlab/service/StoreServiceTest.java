package com.gitlab.service;

import com.gitlab.dto.StoreDto;
import com.gitlab.enums.EntityStatus;
import com.gitlab.mapper.StoreMapper;
import com.gitlab.mapper.UserMapper;
import com.gitlab.model.Store;
import com.gitlab.model.User;
import com.gitlab.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreMapper storeMapper;

    @InjectMocks
    private StoreService storeService;

    @Test
    void should_find_all_examples() {
        List<Store> expectedResult = generateStores();
        when(storeRepository.findAll()).thenReturn(generateStores());

        List<Store> actualResult = storeService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_store_by_id() {
        long id = 1L;
        StoreDto expectedResult = generateStoreDto();
        Store store = generateStore(id);

        when(storeRepository.findById(id)).thenReturn(Optional.of(store));
        when(storeMapper.toDto(store)).thenReturn(generateStoreDto());

        Optional<StoreDto> actualResult = storeService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_store() {
        Store expectedResult = generateStore();
        when(storeRepository.save(expectedResult)).thenReturn(expectedResult);

        Store actualResult = storeService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_store() {
        long id = 1L;
        Store storeToUpdate = generateStore();

        Store storeBeforeUpdate = generateStoreBefore();

        Store updatedStore = generateStore();

        when(storeRepository.findById(id)).thenReturn(Optional.of(storeBeforeUpdate));
        when(storeRepository.save(updatedStore)).thenReturn(updatedStore);

        Optional<Store> actualResult = storeService.update(id, storeToUpdate);

        assertEquals(updatedStore, actualResult.orElse(null));
    }

    @Test
    void should_not_update_store_when_entity_not_found() {
        long id = 4L;
        Store storeToUpdateWith = generateStore();


        when(storeRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Store> actualResult = storeService.update(id, storeToUpdateWith);

        verify(storeRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_delete_store() {
        long id = 1L;
        Store deletedStore = generateStore(id);
        deletedStore.setEntityStatus(EntityStatus.DELETED);

        when(storeRepository.findById(id)).thenReturn(Optional.of(generateStore()));

        storeService.delete(id);

        verify(storeRepository).save(deletedStore);
    }

    @Test
    void should_not_delete_store_when_entity_not_found() {
        long id = 1L;
        when(storeRepository.findById(id)).thenReturn(Optional.empty());

        storeService.delete(id);

        verify(storeRepository, never()).deleteById(anyLong());
    }

    private List<Store> generateStores() {
        return List.of(
                generateStore(1L),
                generateStore(2L),
                generateStore(3L),
                generateStore(4L),
                generateStore(5L));
    }

    private Store generateStore(Long id) {
        Store store = generateStore();
        store.setId(id);
        store.setEntityStatus(EntityStatus.ACTIVE);
        return store;
    }

    private Store generateStore() {
        Store store = new Store();
        store.setId(1L);
        store.setEntityStatus(EntityStatus.ACTIVE);
        return store;
    }
    private Store generateStoreBefore() {
        Store store = new Store();
        store.setId(1L);
        store.setEntityStatus(EntityStatus.ACTIVE);
        return store;
    }
    private StoreDto generateStoreDto() {
        StoreDto storeDto = new StoreDto();
        storeDto.setId(55L);
        storeDto.setOwnerId(1L);
        storeDto.setManagersId(new HashSet<>());

        return storeDto;
    }
}