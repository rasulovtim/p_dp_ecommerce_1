package com.gitlab.service;

import com.gitlab.model.SelectedProduct;
import com.gitlab.repository.SelectedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectedProductService {

    private final SelectedProductRepository selectedProductRepository;




    public List<SelectedProduct> findAll() {
        return selectedProductRepository.findAll();
    }

    public Optional<SelectedProduct> findById(Long id) {
        return selectedProductRepository.findById(id);
    }

    @Transactional
    public SelectedProduct save(SelectedProduct selectedProduct) {
        return selectedProductRepository.save(selectedProduct);
    }


    @Transactional
    public Optional<SelectedProduct> update(Long id, SelectedProduct selectedProduct) {
        Optional<SelectedProduct> optionalSelectedProduct = findById(id);
        SelectedProduct currentSelectedProduct;
        if (optionalSelectedProduct.isEmpty()) {
            return optionalSelectedProduct;
        } else {
            currentSelectedProduct = optionalSelectedProduct.get();
        }
        if (selectedProduct.getCount() != null) {
            currentSelectedProduct.setCount(selectedProduct.getCount());
        }
        return Optional.of(selectedProductRepository.save(currentSelectedProduct));
    }

    @Transactional
    public Optional<SelectedProduct> delete(Long id) {
        Optional<SelectedProduct> optionalSelectedProduct = findById(id);
        if (optionalSelectedProduct.isPresent()) {
            selectedProductRepository.deleteById(id);
        }
        return optionalSelectedProduct;
    }

}
