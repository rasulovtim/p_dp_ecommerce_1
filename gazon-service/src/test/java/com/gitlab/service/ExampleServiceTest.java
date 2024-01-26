package com.gitlab.service;

import com.gitlab.enums.EntityStatus;
import com.gitlab.model.Example;
import com.gitlab.repository.ExampleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExampleServiceTest {

    @Mock
    private ExampleRepository exampleRepository;
    @InjectMocks
    private ExampleService exampleService;

    @Test
    void should_find_all_examples() {
        List<Example> expectedResult = generateExamplesOnlyActive();
        when(exampleRepository.findAll()).thenReturn(generateExamplesOnlyActive());

        List<Example> actualResult = exampleService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_active_example_by_id() {
        long id = 1L;
        Example generateExampleActive = generateExampleActive();

        when(exampleRepository.findById(id)).thenReturn(Optional.of(generateExampleActive));

        Optional<Example> actualResult = exampleService.findById(id);

        assertEquals(generateExampleActive, actualResult.orElse(null));
    }

    @Test
    void should_find_deleted_example_by_id() {
        long id = 1L;
        Example generateExampleDeleted = generateExampleDeleted();
        generateExampleDeleted.setId(id);

        when(exampleRepository.findById(generateExampleDeleted.getId())).thenReturn(Optional.empty());

        Optional<Example> actualResult = exampleService.findById(id);

        assertEquals(Optional.empty(), actualResult);
    }

    @Test
    void should_save_example() {
        Example generateExampleActive = generateExampleActive();
        when(exampleRepository.save(generateExampleActive)).thenReturn(generateExampleActive);

        Example actualResult = exampleService.save(generateExampleActive);

        assertEquals(generateExampleActive, actualResult);
    }

    @Test
    void should_update_active_example() {
        long id = 1L;
        Example exampleToUpdate = new Example();
        exampleToUpdate.setExampleText("modifiedText");

        Example exampleBeforeUpdate = new Example(id, "unmodifiedText", EntityStatus.ACTIVE);
        Example updatedExample = new Example(id, "modifiedText", EntityStatus.ACTIVE);

        when(exampleRepository.findById(id)).thenReturn(Optional.of(exampleBeforeUpdate));
        when(exampleRepository.save(updatedExample)).thenReturn(updatedExample);

        Optional<Example> actualResult = exampleService.update(id, exampleToUpdate);

        assertEquals(updatedExample, actualResult.orElse(null));
    }
    @Test
    void should_update_deleted_example() {
        long id = 1L;
        Example exampleToUpdate = new Example();
        exampleToUpdate.setExampleText("modifiedText");

        Example exampleBeforeUpdate = new Example(id, "unmodifiedText", EntityStatus.DELETED);
        Example updatedExample = new Example(id, "modifiedText", EntityStatus.ACTIVE);

        when(exampleRepository.findById(id)).thenReturn(Optional.of(exampleBeforeUpdate));
        when(exampleRepository.save(updatedExample)).thenReturn(updatedExample);

        Optional<Example> actualResult = exampleService.update(id, exampleToUpdate);

        assertEquals(updatedExample, actualResult.orElse(null));
    }

    @Test
    void should_not_update_example_when_entity_not_found() {
        long id = 1L;
        Example exampleToUpdate = new Example();
        exampleToUpdate.setExampleText("modifiedText");

        when(exampleRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Example> actualResult = exampleService.update(id, exampleToUpdate);

        verify(exampleRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_updated_exampleText_field_if_null() {
        long id = 1L;
        Example exampleToUpdate = new Example();
        exampleToUpdate.setExampleText(null);

        Example exampleBeforeUpdate = new Example(id, "unmodifiedText", EntityStatus.ACTIVE);

        when(exampleRepository.findById(id)).thenReturn(Optional.of(exampleBeforeUpdate));
        when(exampleRepository.save(exampleBeforeUpdate)).thenReturn(exampleBeforeUpdate);

        Optional<Example> actualResult = exampleService.update(id, exampleToUpdate);

        verify(exampleRepository).save(exampleBeforeUpdate);
        assertEquals(exampleBeforeUpdate, actualResult.orElse(null));
        assertEquals("unmodifiedText", exampleBeforeUpdate.getExampleText());
    }

    @Test
    void should_delete_example() {
        Example example = generateExampleActive();
        when(exampleRepository.findById(example.getId())).thenReturn(Optional.of(example));
        Example delete = exampleService.delete(example.getId()).orElseGet(null);

        assertEquals(EntityStatus.DELETED, delete.getEntityStatus());

    }

    @Test
    void should_not_delete_example_when_entity_not_found() {
        long id = 1L;
        when(exampleRepository.findById(id)).thenReturn(Optional.empty());

        exampleService.delete(id);

        verify(exampleRepository, never()).deleteById(anyLong());
    }

    private List<Example> generateExamplesOnlyActive() {
        return List.of(
                new Example(1L, "text1", EntityStatus.ACTIVE),
                new Example(3L, "text3", EntityStatus.ACTIVE),
                new Example(5L, "text5", EntityStatus.ACTIVE));
    }
    private List<Example> generateExamplesOnlyDeleted() {
        return List.of(
                new Example(2L, "text2", EntityStatus.DELETED),
                new Example(4L, "text4", EntityStatus.DELETED));
    }

    private Example generateExampleActive() {
        return new Example(1L, "text1", EntityStatus.ACTIVE);
    }
    private Example generateExampleDeleted() {
        return new Example(1L, "text1", EntityStatus.DELETED);
    }
}