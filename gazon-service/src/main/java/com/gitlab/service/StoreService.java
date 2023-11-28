package com.gitlab.service;

import com.gitlab.dto.StoreDto;
import com.gitlab.enums.EntityStatus;
import com.gitlab.mapper.StoreMapper;
import com.gitlab.model.Store;
import com.gitlab.repository.StoreRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public @NonNull List<StoreDto> findAll() {
        List<Store> storeList = storeRepository.findAll();
        return storeList
                .stream()
                .filter(user -> user.getEntityStatus().equals(EntityStatus.ACTIVE))
                .map(storeMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<StoreDto> findById(Long id) {
        Optional<Store> optionalStore = storeRepository.findById(id);
        if (optionalStore.isPresent() && optionalStore.get().getEntityStatus().equals(EntityStatus.DELETED)) {
            return Optional.empty();
        }
        return optionalStore.map(storeMapper::toDto);
    }

    @Transactional
    public StoreDto save(StoreDto storeDto) {
        Store store = storeMapper.toEntity(storeDto);
        store.setEntityStatus(EntityStatus.ACTIVE);
        Store savedStore = storeRepository.save(store);
        return storeMapper.toDto(savedStore);
    }

    @Transactional
    public StoreDto update(Long id, StoreDto storeDto) {
        Optional<Store> optionalSavedStore = storeRepository.findById(id);
        if (optionalSavedStore.isEmpty() || optionalSavedStore.get().getEntityStatus().equals(EntityStatus.DELETED)) {
            return null;
        }
        Store savedStore = optionalSavedStore.get();

        updateStoreFields(savedStore, storeDto);
        Store updatedStore = storeRepository.save(savedStore);
        return storeMapper.toDto(updatedStore);
    }

    @Transactional
    public StoreDto delete(Long id) {
        Optional<Store> optionalDeletedStore = storeRepository.findById(id);
        if (optionalDeletedStore.isEmpty() || optionalDeletedStore.get().getEntityStatus().equals(EntityStatus.DELETED)) {
            return null;
        }
        Store deletedStore = optionalDeletedStore.get();
        deletedStore.setEntityStatus(EntityStatus.DELETED);
        storeRepository.save(deletedStore);

        return storeMapper.toDto(optionalDeletedStore.get());
    }

    private Store updateStoreFields(Store savedStore, StoreDto storeDto) {
        savedStore.setOwner(storeMapper.mapOwnerIdToUser(storeDto.getOwnerId()));
        savedStore.setManagers(storeMapper.map(storeDto.getManagersId()));

        return savedStore;
    }
}