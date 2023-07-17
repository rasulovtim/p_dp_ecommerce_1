package com.gitlab.service;

import com.gitlab.model.Example;
import com.gitlab.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;

    public List<Example> findAll() {
        return exampleRepository.findAll();
    }

    public Optional<Example> findById(Long id) {
        return exampleRepository.findById(id);
    }

    public Example save(Example example) {
        return exampleRepository.save(example);
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