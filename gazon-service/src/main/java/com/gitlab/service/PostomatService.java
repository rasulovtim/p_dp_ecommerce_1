package com.gitlab.service;

import com.gitlab.model.Postomat;
import com.gitlab.repository.PostomatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostomatService {

    private final PostomatRepository postomatRepository;

    public List<Postomat> findAll() {
        return postomatRepository.findAll();
    }

    public Optional<Postomat> findById(Long id) {
        return postomatRepository.findById(id);
    }

    public Postomat save(Postomat postomat) {
        return postomatRepository.save(postomat);
    }

    public Optional<Postomat> update(Long id, Postomat postomat) {
        Optional<Postomat> optionalSavedPostomat = findById(id);
        Postomat savedPostomat;
        if (optionalSavedPostomat.isEmpty()) {
            return optionalSavedPostomat;
        } else {
            savedPostomat = optionalSavedPostomat.get();
        }

        if (postomat.getDirections() != null) {
            savedPostomat.setDirections(postomat.getDirections());
        }
        if (postomat.getAddress() != null) {
            savedPostomat.setAddress(postomat.getAddress());
        }
        if (postomat.getShelfLifeDays() != null) {
            savedPostomat.setShelfLifeDays(postomat.getShelfLifeDays());
        }

        return Optional.of(postomatRepository.save(savedPostomat));
    }

    public Optional<Postomat> delete(Long id) {
        Optional<Postomat> optionalSavedPostomat = findById(id);
        if (optionalSavedPostomat.isPresent()) {
            postomatRepository.deleteById(id);
        }
        return optionalSavedPostomat;
    }
}