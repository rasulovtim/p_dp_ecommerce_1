package com.gitlab.service;

import com.gitlab.dto.PostomatDto;
import com.gitlab.mapper.PostomatMapper;
import com.gitlab.model.Postomat;
import com.gitlab.repository.PostomatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostomatService {

    private final PostomatRepository postomatRepository;

    private final PostomatMapper postomatMapper;

    public List<Postomat> findAll() {
        return postomatRepository.findAll();
    }

    public List<PostomatDto> findAllDto() {
        List<Postomat> postomats = postomatRepository.findAll();
        return postomats.stream()
                .map(postomatMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Postomat> findById(Long id) {
        return postomatRepository.findById(id);
    }

    public Optional<PostomatDto> findByIdDto(Long id) {
        return postomatRepository.findById(id)
                .map(postomatMapper::toDto);
    }

    public Postomat save(Postomat postomat) {
        return postomatRepository.save(postomat);
    }

    public PostomatDto saveDto(PostomatDto postomatDto) {
        Postomat postomat = postomatMapper.toEntity(postomatDto);
        Postomat savedPostomat = postomatRepository.save(postomat);
        return postomatMapper.toDto(savedPostomat);
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

    public Optional<PostomatDto> updateDto(Long id, PostomatDto postomatDto) {
        Optional<PostomatDto> optionalSavedPostomatDto = findByIdDto(id);

        if (optionalSavedPostomatDto.isEmpty()) {
            return Optional.empty();
        }

        PostomatDto savedPostomatDto = optionalSavedPostomatDto.get();

        if (postomatDto.getDirections() != null) {
            savedPostomatDto.setDirections(postomatDto.getDirections());
        }
        if (postomatDto.getAddress() != null) {
            savedPostomatDto.setAddress(postomatDto.getAddress());
        }
        if (postomatDto.getShelfLifeDays() != null) {
            savedPostomatDto.setShelfLifeDays(postomatDto.getShelfLifeDays());
        }

        PostomatDto updatedPostomatDto = saveDto(savedPostomatDto);
        return Optional.ofNullable(updatedPostomatDto);
    }

    public Optional<Postomat> delete(Long id) {
        Optional<Postomat> optionalSavedPostomat = findById(id);
        if (optionalSavedPostomat.isPresent()) {
            postomatRepository.deleteById(id);
        }
        return optionalSavedPostomat;
    }

    public Optional<PostomatDto> deleteDto(Long id) {
        Optional<PostomatDto> optionalSavedPostomatDto = findByIdDto(id);

        if (optionalSavedPostomatDto.isPresent()) {
            postomatRepository.deleteById(id);
        }

        return optionalSavedPostomatDto;
    }
}