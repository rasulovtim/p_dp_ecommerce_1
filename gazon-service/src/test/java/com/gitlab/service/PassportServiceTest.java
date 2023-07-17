package com.gitlab.service;

import com.gitlab.model.Passport;
import com.gitlab.repository.PassportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassportServiceTest {

    @Mock
    private PassportRepository passportRepository;
    @InjectMocks
    private PassportService passportService;

    @Test
    void should_find_all_passports() {
        List<Passport> expectedResult = generatePassports();
        when(passportRepository.findAll()).thenReturn(generatePassports());

        List<Passport> actualResult = passportService.findAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_find_passport_by_id() {
        long id = 1L;
        Passport expectedResult = generatePassport();
        when(passportRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Optional<Passport> actualResult = passportService.findById(id);

        assertEquals(expectedResult, actualResult.orElse(null));
    }

    @Test
    void should_save_passport() {
        Passport expectedResult = generatePassport();
        when(passportRepository.save(expectedResult)).thenReturn(expectedResult);

        Passport actualResult = passportService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void should_update_passport() {
        long id = 1L;
        Passport passportToUpdate = new Passport();

        passportToUpdate.setFirstName("modFirstName"); //modified
        passportToUpdate.setId(id);
        passportToUpdate.setCitizenship(Passport.Citizenship.RUSSIA);
        passportToUpdate.setLastName("testLastName");
        passportToUpdate.setPatronym("testPatronym");
        passportToUpdate.setBirthDate(LocalDate.of(1990, 1, 1));
        passportToUpdate.setIssueDate(LocalDate.of(2020, 1, 1));
        passportToUpdate.setPassportNumber("1234 567890");
        passportToUpdate.setIssuer("Test Otedel police №1");
        passportToUpdate.setIssuerNumber("111-111");

        Passport passportBeforeUpdate = new Passport();

        passportBeforeUpdate.setFirstName("unModFirstName"); //unmodified
        passportBeforeUpdate.setId(id);
        passportBeforeUpdate.setCitizenship(Passport.Citizenship.KAZAKHSTAN);
        passportBeforeUpdate.setLastName("testLastName");
        passportBeforeUpdate.setPatronym("testPatronym");
        passportBeforeUpdate.setBirthDate(LocalDate.of(1990, 1, 1));
        passportBeforeUpdate.setIssueDate(LocalDate.of(2020, 1, 1));
        passportBeforeUpdate.setPassportNumber("1234 567890");
        passportBeforeUpdate.setIssuer("Test Otedel police №1");
        passportBeforeUpdate.setIssuerNumber("111-111");

        Passport updatedPassport = new Passport();

        updatedPassport.setFirstName("modFirstName"); //modified
        updatedPassport.setId(id);
        updatedPassport.setCitizenship(Passport.Citizenship.RUSSIA);
        updatedPassport.setLastName("testLastName");
        updatedPassport.setPatronym("testPatronym");
        updatedPassport.setBirthDate(LocalDate.of(1990, 1, 1));
        updatedPassport.setIssueDate(LocalDate.of(2020, 1, 1));
        updatedPassport.setPassportNumber("1234 567890");
        updatedPassport.setIssuer("Test Otedel police №1");
        updatedPassport.setIssuerNumber("111-111");

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(updatedPassport)).thenReturn(updatedPassport);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        assertEquals(updatedPassport, actualResult.orElse(null));
    }

    @Test
    void should_not_update_passport_when_entity_not_found() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository, never()).save(any());
        assertNull(actualResult.orElse(null));
    }

    @Test
    void should_not_updated_citizenship_field_if_null() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();
        passportToUpdate.setCitizenship(null);

        Passport passportBeforeUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(passportBeforeUpdate)).thenReturn(passportBeforeUpdate);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository).save(passportBeforeUpdate);
        assertEquals(passportBeforeUpdate, actualResult.orElse(null));
        assertEquals(Passport.Citizenship.RUSSIA, passportBeforeUpdate.getCitizenship());
    }

    @Test
    void should_not_updated_FirstName_field_if_null() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();
        passportToUpdate.setFirstName(null);

        Passport passportBeforeUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(passportBeforeUpdate)).thenReturn(passportBeforeUpdate);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository).save(passportBeforeUpdate);

        assertEquals(passportBeforeUpdate, actualResult.orElse(null));
        assertEquals("firstName", passportBeforeUpdate.getFirstName());
    }

    @Test
    void should_not_updated_lastName_field_if_null() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();
        passportToUpdate.setLastName(null);

        Passport passportBeforeUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(passportBeforeUpdate)).thenReturn(passportBeforeUpdate);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository).save(passportBeforeUpdate);

        assertEquals(passportBeforeUpdate, actualResult.orElse(null));
        assertEquals("lastName", passportBeforeUpdate.getLastName());
    }

    @Test
    void should_not_updated_birthDate_field_if_null() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();
        passportToUpdate.setBirthDate(null);

        Passport passportBeforeUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(passportBeforeUpdate)).thenReturn(passportBeforeUpdate);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository).save(passportBeforeUpdate);

        assertEquals(passportBeforeUpdate, actualResult.orElse(null));
        assertEquals(LocalDate.of(2000,1,1), passportBeforeUpdate.getBirthDate());
    }

    @Test
    void should_not_updated_issueDate_field_if_null() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();
        passportToUpdate.setIssueDate(null);

        Passport passportBeforeUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(passportBeforeUpdate)).thenReturn(passportBeforeUpdate);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository).save(passportBeforeUpdate);

        assertEquals(passportBeforeUpdate, actualResult.orElse(null));
        assertEquals(LocalDate.of(2015,1,1), passportBeforeUpdate.getIssueDate());
    }

    @Test
    void should_not_updated_passportNumber_field_if_null() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();
        passportToUpdate.setPassportNumber(null);

        Passport passportBeforeUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(passportBeforeUpdate)).thenReturn(passportBeforeUpdate);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository).save(passportBeforeUpdate);

        assertEquals(passportBeforeUpdate, actualResult.orElse(null));
        assertEquals("1111 111111", passportBeforeUpdate.getPassportNumber());
    }

    @Test
    void should_not_updated_issuer_field_if_null() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();
        passportToUpdate.setIssuer(null);

        Passport passportBeforeUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(passportBeforeUpdate)).thenReturn(passportBeforeUpdate);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository).save(passportBeforeUpdate);

        assertEquals(passportBeforeUpdate, actualResult.orElse(null));
        assertEquals("Otdel police #1", passportBeforeUpdate.getIssuer());
    }

    @Test
    void should_not_updated_issuerNumber_field_if_null() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();
        passportToUpdate.setIssuerNumber(null);

        Passport passportBeforeUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(passportBeforeUpdate)).thenReturn(passportBeforeUpdate);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository).save(passportBeforeUpdate);

        assertEquals(passportBeforeUpdate, actualResult.orElse(null));
        assertEquals("111-111", passportBeforeUpdate.getIssuerNumber());
    }

    @Test
    void should_not_updated_patronym_field_if_null() {
        long id = 1L;
        Passport passportToUpdate = generatePassport();
        passportToUpdate.setPatronym(null);

        Passport passportBeforeUpdate = generatePassport();

        when(passportRepository.findById(id)).thenReturn(Optional.of(passportBeforeUpdate));
        when(passportRepository.save(passportBeforeUpdate)).thenReturn(passportBeforeUpdate);

        Optional<Passport> actualResult = passportService.update(id, passportToUpdate);

        verify(passportRepository).save(passportBeforeUpdate);

        assertEquals(passportBeforeUpdate, actualResult.orElse(null));
        assertEquals("patronym", passportBeforeUpdate.getPatronym());
    }

    @Test
    void should_delete_passport() {
        long id = 1L;
        when(passportRepository.findById(id)).thenReturn(Optional.of(generatePassport()));

        passportService.delete(id);

        verify(passportRepository).deleteById(id);
    }

    @Test
    void should_not_delete_passport_when_entity_not_found() {
        long id = 1L;
        when(passportRepository.findById(id)).thenReturn(Optional.empty());

        passportService.delete(id);

        verify(passportRepository, never()).deleteById(anyLong());
    }

    private List<Passport> generatePassports() {
        return List.of(
                generatePassport(1L),
                generatePassport(2L),
                generatePassport(3L),
                generatePassport(4L),
                generatePassport(5L)
        );

    }

    private Passport generatePassport(Long id) {
        Passport passport = generatePassport();
        passport.setId(id);
        return passport;
    }

    private Passport generatePassport() {
        return new Passport(1L, Passport.Citizenship.RUSSIA, "firstName", "lastName", "patronym",
                LocalDate.of(2000, 1, 1), LocalDate.of(2015,1,1),
                "1111 111111", "Otdel police #1", "111-111");
    }


}
