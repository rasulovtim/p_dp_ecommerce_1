package com.gitlab.service;

import com.gitlab.model.Store;
import com.gitlab.repository.StoreRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public @NonNull List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Transactional
    public Optional<Store> update(Long id, Store store) {
        Optional<Store> currentOptionalStore = findById(id);
        Store currentStore;
        if (currentOptionalStore.isEmpty()) {
            return currentOptionalStore;
        } else {
            currentStore = currentOptionalStore.get();
        }
        if (store.getManagers() != null) {
            currentStore.setManagers(store.getManagers());
        }
        if (store.getOwner() != null) {
            currentStore.setOwner(store.getOwner());
        }
        if (store.getProducts() != null) {
            currentStore.setProducts(store.getProducts());
        }
        return Optional.of(storeRepository.save(currentStore));
    }

    @Transactional
    public Store save(Store store) {
        return storeRepository.save(store);
    }

    @Transactional
    public Optional<Store> delete(Long id) {
        Optional<Store> foundProduct = findById(id);
        if (foundProduct.isPresent()) {
            storeRepository.deleteById(id);
        }
        return foundProduct;
    }
}
