package com.gitlab.service;

import com.gitlab.dto.ExampleDto;
import com.gitlab.mapper.ExampleMapper;
import com.gitlab.model.Example;
import com.gitlab.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;

    private final ExampleMapper exampleMapper;

    public List<Example> findAll() {
        return exampleRepository.findAll();
    }

    public List<ExampleDto> findAllDto() {
        List<Example> examples = exampleRepository.findAll();
        return examples.stream()
                .map(exampleMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<Example> getPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return exampleRepository.findAll(pageRequest);
    }

    public Page<ExampleDto> getPageDto(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Example> examplePage = exampleRepository.findAll(pageRequest);
        return examplePage.map(exampleMapper::toDto);
    }

    public Optional<Example> findById(Long id) {
        return exampleRepository.findById(id);
    }

    public Optional<ExampleDto> findByIdDto(Long id) {
        return exampleRepository.findById(id)
                .map(exampleMapper::toDto);
    }

    public Example save(Example example) {
        return exampleRepository.save(example);
    }

    public ExampleDto saveDto(ExampleDto exampleDto) {
        Example example = exampleMapper.toEntity(exampleDto);
        Example savedExample = exampleRepository.save(example);
        return exampleMapper.toDto(savedExample);
    }

    public Optional<Example> update(Long id, Example example) {
        Optional<Example> optionalSavedExample = findById(id);
        Example savedExample;
        if (optionalSavedExample.isEmpty()) {
            return optionalSavedExample;
        } else {
            savedExample = optionalSavedExample.get();
        }
        if (example.getExampleText() != null) {
            savedExample.setExampleText(example.getExampleText());
        }
        return Optional.of(exampleRepository.save(savedExample));
    }

    public Optional<ExampleDto> updateDto(Long id, ExampleDto exampleDto) {
        Optional<Example> optionalSavedExample = findById(id);
        if (optionalSavedExample.isEmpty()) {
            return Optional.empty();
        }

        Example savedExample = optionalSavedExample.get();
        if (exampleDto.getExampleText() != null) {
            savedExample.setExampleText(exampleDto.getExampleText());
        }

        savedExample = exampleRepository.save(savedExample);
        return Optional.of(exampleMapper.toDto(savedExample));
    }

    public Optional<Example> delete(Long id) {
        Optional<Example> optionalSavedExample = findById(id);
        if (optionalSavedExample.isEmpty()) {
            return optionalSavedExample;
        } else {
            exampleRepository.deleteById(id);
            return optionalSavedExample;
        }
    }
}