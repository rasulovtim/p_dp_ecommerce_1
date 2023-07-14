package com.gitlab.service;

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
        List<Example> expectedResult = generateExamples();
        when(exampleRepository.findAll()).thenReturn(generateExamples());

        List<Example> actualResult = exampleService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_example_by_id() {
        long id = 1L;
        Example expectedResult = generateExample();
        when(exampleRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<Example> actualResult = exampleService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_example() {
        Example expectedResult = generateExample();
        when(exampleRepository.save(expectedResult)).thenReturn(expectedResult);

        Example actualResult = exampleService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_example() {
        long id = 1L;
        Example exampleToUpdate = new Example();
        exampleToUpdate.setExampleText("modifiedText");

        Example exampleBeforeUpdate = new Example(id, "unmodifiedText");
        Example updatedExample = new Example(id, "modifiedText");

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

        Example exampleBeforeUpdate = new Example(id, "unmodifiedText");

        when(exampleRepository.findById(id)).thenReturn(Optional.of(exampleBeforeUpdate));
        when(exampleRepository.save(exampleBeforeUpdate)).thenReturn(exampleBeforeUpdate);

        Optional<Example> actualResult = exampleService.update(id, exampleToUpdate);

        verify(exampleRepository).save(exampleBeforeUpdate);
        assertEquals(exampleBeforeUpdate, actualResult.orElse(null));
        assertEquals("unmodifiedText", exampleBeforeUpdate.getExampleText());
    }

    @Test
    void should_delete_example() {
        long id = 1L;
        when(exampleRepository.findById(id)).thenReturn(Optional.of(generateExample()));

        exampleService.delete(id);

        verify(exampleRepository).deleteById(id);
    }

    @Test
    void should_not_delete_example_when_entity_not_found() {
        long id = 1L;
        when(exampleRepository.findById(id)).thenReturn(Optional.empty());

        exampleService.delete(id);

        verify(exampleRepository, never()).deleteById(anyLong());
    }

    private List<Example> generateExamples() {
        return List.of(
                new Example(1L, "text1"),
                new Example(2L, "text2"),
                new Example(3L, "text3"),
                new Example(4L, "text4"),
                new Example(5L, "text5"));
    }

    private Example generateExample() {
        return new Example(1L, "text1");
    }
}