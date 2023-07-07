package com.gitlab.controller;

import com.gitlab.controller.api.PostomatRestApi;
import com.gitlab.dto.PostomatDto;
import com.gitlab.mapper.PostomatMapper;
import com.gitlab.service.PostomatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PostomatRestController implements PostomatRestApi {

    private final PostomatService postomatService;

    private final PostomatMapper postomatMapper;

    @Override
    public ResponseEntity<List<PostomatDto>> getAll() {
        var postomats = postomatService.findAll();
        return postomats.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(postomats.stream().map(postomatMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PostomatDto> get(Long id) {
        return postomatService.findById(id)
                .map(value -> ResponseEntity.ok(postomatMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PostomatDto> create(PostomatDto postomatDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postomatMapper
                        .toDto(postomatService
                                .save(postomatMapper
                                        .toEntity(postomatDto))));
    }

    @Override
    public ResponseEntity<PostomatDto> update(Long id, PostomatDto postomatDto) {
        return postomatService.update(id, postomatMapper.toEntity(postomatDto))
                .map(value -> ResponseEntity.ok(postomatMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return postomatService.delete(id).isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}
