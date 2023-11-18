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

    public @NonNull List<Store> findAll() {
        return storeRepository.findAll()
                .stream()
                .filter(store -> store.getEntityStatus().equals(EntityStatus.ACTIVE))
                .collect(Collectors.toList());
    }

    public List<StoreDto> findAllDto() {
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
    public Store save(Store store) {
        store.setEntityStatus(EntityStatus.ACTIVE);
        return storeRepository.save(store);
    }

    @Transactional
    public StoreDto saveDto(StoreDto storeDto) {
        Store store = storeMapper.toEntity(storeDto);
        Store savedStore = storeRepository.save(store);
        return storeMapper.toDto(savedStore);
    }

    @Transactional
    public Optional<Store> update(Long id, Store store) {
        Optional<Store> currentOptionalStore = storeRepository.findById(id);
        Store savedStore;
        if (currentOptionalStore.isEmpty() || currentOptionalStore.get().getEntityStatus().equals(EntityStatus.DELETED)) {
            return Optional.empty();
        } else {
            savedStore = currentOptionalStore.get();
        }
        if (store.getManagers() != null) {
            savedStore.setManagers(store.getManagers());
        }
        if (store.getOwner() != null) {
            savedStore.setOwner(store.getOwner());
        }
        if (store.getProducts() != null) {
            savedStore.setProducts(store.getProducts());
        }

        savedStore.setEntityStatus(EntityStatus.ACTIVE);

        return Optional.of(storeRepository.save(savedStore));
    }

    @Transactional
    public StoreDto updateDto(Long id, StoreDto storeDto) {
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
    public Optional<Store> delete(Long id) {
        Optional<Store> optionalDeletedStore = storeRepository.findById(id);
        if (optionalDeletedStore.isEmpty() || optionalDeletedStore.get().getEntityStatus().equals(EntityStatus.DELETED)) {
            return Optional.empty();
        }

        Store deletedStore = optionalDeletedStore.get();
        deletedStore.setEntityStatus(EntityStatus.DELETED);
        storeRepository.save(deletedStore);

        return optionalDeletedStore;
    }

    public StoreDto deleteDto(Long id) {
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