package com.gitlab.controller;

import com.gitlab.controller.api.PostomatRestApi;
import com.gitlab.dto.PostomatDto;
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

    @Override
    public ResponseEntity<List<PostomatDto>> getAll() {
        List<PostomatDto> postomatDtos = postomatService.findAllDto();

        if (postomatDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(postomatDtos);
        }
    }

    @Override
    public ResponseEntity<PostomatDto> get(Long id) {
        return postomatService.findByIdDto(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PostomatDto> create(PostomatDto postomatDto) {
        PostomatDto createdPostomatDto = postomatService.saveDto(postomatDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdPostomatDto);
    }

    @Override
    public ResponseEntity<PostomatDto> update(Long id, PostomatDto postomatDto) {
        return postomatService.updateDto(id, postomatDto)
                .map(value -> ResponseEntity.ok(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return postomatService.deleteDto(id).isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok().build();
    }
}
