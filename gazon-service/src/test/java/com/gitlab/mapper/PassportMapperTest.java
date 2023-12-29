package com.gitlab.mapper;

import com.gitlab.dto.PassportDto;
import com.gitlab.enums.Citizenship;
import com.gitlab.model.Passport;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PassportMapperTest {

    private final PassportMapper mapper = Mappers.getMapper(PassportMapper.class);

    @Test
    void should_map_example_to_Dto() {
        Passport passport = getPassport(1L);

        PassportDto actualResult = mapper.toDto(passport);

        assertNotNull(actualResult);
        assertEquals(passport.getId(), actualResult.getId());
        assertEquals(passport.getCitizenship(), actualResult.getCitizenship());
        assertEquals(passport.getFirstName(), actualResult.getFirstName());
        assertEquals(passport.getLastName(), actualResult.getLastName());
        assertEquals(passport.getPatronym(), actualResult.getPatronym());
        assertEquals(passport.getBirthDate(), actualResult.getBirthDate());
        assertEquals(passport.getIssueDate(), actualResult.getIssueDate());
        assertEquals(passport.getPassportNumber(), actualResult.getPassportNumber());
        assertEquals(passport.getIssuer(), actualResult.getIssuer());
        assertEquals(passport.getIssuerNumber(), actualResult.getIssuerNumber());
    }

    @Test
    void should_map_exampleDto_to_Entity() {

        PassportDto passportDto = getPassportDto(1L);


        Passport actualResult = mapper.toEntity(passportDto);

        assertNotNull(actualResult);
        assertEquals(passportDto.getId(), actualResult.getId());
        assertEquals(passportDto.getCitizenship(), actualResult.getCitizenship());
        assertEquals(passportDto.getFirstName(), actualResult.getFirstName());
        assertEquals(passportDto.getLastName(), actualResult.getLastName());
        assertEquals(passportDto.getPatronym(), actualResult.getPatronym());
        assertEquals(passportDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(passportDto.getIssueDate(), actualResult.getIssueDate());
        assertEquals(passportDto.getPassportNumber(), actualResult.getPassportNumber());
        assertEquals(passportDto.getIssuer(), actualResult.getIssuer());
        assertEquals(passportDto.getIssuerNumber(), actualResult.getIssuerNumber());
    }

    @Test
    void should_map_passportList_to_DtoList() {
        List<Passport> passportList = List.of(getPassport(1L), getPassport(2L), getPassport(3L));

        List<PassportDto> passportDtoList = mapper.toDtoList(passportList);

        assertNotNull(passportDtoList);
        assertEquals(passportList.size(), passportList.size());
        for (int i = 0; i < passportDtoList.size(); i++) {
            PassportDto dto = passportDtoList.get(i);
            Passport entity = passportList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getCitizenship(), entity.getCitizenship());
            assertEquals(dto.getFirstName(), entity.getFirstName());
            assertEquals(dto.getLastName(), entity.getLastName());
            assertEquals(dto.getPatronym(), entity.getPatronym());
            assertEquals(dto.getBirthDate(), entity.getBirthDate());
            assertEquals(dto.getIssueDate(), entity.getIssueDate());
            assertEquals(dto.getPassportNumber(), entity.getPassportNumber());
            assertEquals(dto.getIssuer(), entity.getIssuer());
            assertEquals(dto.getIssuerNumber(), entity.getIssuerNumber());
        }
    }

    @Test
    void should_map_passportDtoList_to_EntityList() {
        List<PassportDto> passportDtoList = List.of(getPassportDto(1L), getPassportDto(2L), getPassportDto(3L));

        List<Passport> passportList = mapper.toEntityList(passportDtoList);

        assertNotNull(passportList);
        assertEquals(passportList.size(), passportList.size());
        for (int i = 0; i < passportList.size(); i++) {
            PassportDto dto = passportDtoList.get(i);
            Passport entity = passportList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getCitizenship(), entity.getCitizenship());
            assertEquals(dto.getFirstName(), entity.getFirstName());
            assertEquals(dto.getLastName(), entity.getLastName());
            assertEquals(dto.getPatronym(), entity.getPatronym());
            assertEquals(dto.getBirthDate(), entity.getBirthDate());
            assertEquals(dto.getIssueDate(), entity.getIssueDate());
            assertEquals(dto.getPassportNumber(), entity.getPassportNumber());
            assertEquals(dto.getIssuer(), entity.getIssuer());
            assertEquals(dto.getIssuerNumber(), entity.getIssuerNumber());
        }
    }

    @NotNull
    private Passport getPassport(Long id) {
        Passport passport = new Passport();

        passport.setId(id);
        passport.setCitizenship(Citizenship.RUSSIA);
        passport.setFirstName("testFirstName");
        passport.setLastName("testLastName");
        passport.setPatronym("testPatronym");
        passport.setBirthDate(LocalDate.of(2000, 1, 1));
        passport.setIssueDate(LocalDate.of(2014, 1, 1));
        passport.setPassportNumber("1111 11111" + id);
        passport.setIssuer("Test Otdel Police #" + id);
        passport.setIssuerNumber("111-11" + id);
        return passport;
    }

    @NotNull
    private PassportDto getPassportDto(Long id) {
        PassportDto passportDto = new PassportDto();

        passportDto.setId(id);
        passportDto.setCitizenship(Citizenship.RUSSIA);
        passportDto.setFirstName("testFirstName");
        passportDto.setLastName("testLastName");
        passportDto.setPatronym("testPatronym");
        passportDto.setBirthDate(LocalDate.of(2000, 1, 1));
        passportDto.setIssueDate(LocalDate.of(2014, 1, 1));
        passportDto.setPassportNumber("1111 11111" + id);
        passportDto.setIssuer("Test Otdel Police #" + id);
        passportDto.setIssuerNumber("111-11" + id);
        return passportDto;
    }
}
