package com.gitlab.service;

import com.gitlab.dto.SelectedProductDto;
import com.gitlab.mapper.SelectedProductMapper;
import com.gitlab.model.SelectedProduct;
import com.gitlab.repository.SelectedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectedProductService {

    private final SelectedProductRepository selectedProductRepository;
    private final SelectedProductMapper selectedProductMapper;

    public List<SelectedProduct> findAll() {
        return selectedProductRepository.findAll();
    }

    public List<SelectedProductDto> findAllDto() {
        List<SelectedProduct> selectedProducts = selectedProductRepository.findAll();
        return selectedProducts.stream()
                .map(selectedProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<SelectedProduct> findById(Long id) {
        return selectedProductRepository.findById(id);
    }

    public Optional<SelectedProductDto> findByIdDto(Long id) {
        Optional<SelectedProduct> selectedProductOptional = selectedProductRepository.findById(id);
        return selectedProductOptional.map(selectedProductMapper::toDto);
    }

    @Transactional
    public SelectedProduct save(SelectedProduct selectedProduct) {
        return selectedProductRepository.save(selectedProduct);
    }

    @Transactional
    public SelectedProductDto saveDto(SelectedProductDto selectedProductDto) {
        SelectedProduct selectedProduct = selectedProductMapper.toEntity(selectedProductDto);
        SelectedProduct savedSelectedProduct = selectedProductRepository.save(selectedProduct);
        return selectedProductMapper.toDto(savedSelectedProduct);
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

    @Transactional
    public Optional<SelectedProductDto> deleteDto(Long id) {
        Optional<SelectedProduct> selectedProductOptional = selectedProductRepository.findById(id);
        if (selectedProductOptional.isPresent()) {
            selectedProductRepository.deleteById(id);
        }
        return selectedProductOptional.map(selectedProductMapper::toDto);
    }
    public Optional<SelectedProductDto> updateSelectedProduct(Long id, SelectedProductDto selectedProductDto) {
        Optional<SelectedProduct> optionalSelectedProduct = selectedProductRepository.findById(id);
        if (optionalSelectedProduct.isEmpty()) {
            return Optional.empty();
        }

        SelectedProduct currentSelectedProduct = optionalSelectedProduct.get();
        // Обновление полей, если они не null в DTO
        if (selectedProductDto.getCount() != null) {
            currentSelectedProduct.setCount(selectedProductDto.getCount());
        }

        // Расчет незамаппированных полей
        selectedProductMapper.calculatedUnmappedFields(selectedProductDto, currentSelectedProduct);

        SelectedProduct updatedSelectedProduct = selectedProductRepository.save(currentSelectedProduct);
        SelectedProductDto updatedProductDto = selectedProductMapper.toDto(updatedSelectedProduct);
        return Optional.of(updatedProductDto);
    }

    public Optional<SelectedProductDto> findByIdAndMapToDto(Long id) {
        Optional<SelectedProduct> selectedProductOptional = selectedProductRepository.findById(id);
        if (selectedProductOptional.isEmpty()) {
            return Optional.empty();
        }

        SelectedProduct selectedProduct = selectedProductOptional.get();
        SelectedProductDto selectedProductDto = selectedProductMapper.toDto(selectedProduct);

        // Рассчитываем незамаппированные поля
        BigDecimal sum = selectedProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(selectedProduct.getCount()));
        double totalWeight = selectedProduct.getProduct().getWeight() * selectedProduct.getCount();

        // Устанавливаем значения незамаппированных полей в DTO
        selectedProductDto.setSum(sum);
        selectedProductDto.setTotalWeight((long) totalWeight);

        return Optional.of(selectedProductDto);
    }

    public double calculateTotalWeight(SelectedProductDto selectedProductDto) {
        SelectedProduct selectedProduct = selectedProductMapper.toEntity(selectedProductDto);

        // Расчет общего веса
        double totalWeight = selectedProduct.getProduct().getWeight() * selectedProduct.getCount();

        return totalWeight;
    }
}
