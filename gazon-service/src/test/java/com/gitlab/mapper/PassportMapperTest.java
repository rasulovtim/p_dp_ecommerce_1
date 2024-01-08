package com.gitlab.mapper;

import com.gitlab.dto.PassportDto;
import com.gitlab.enums.Citizenship;
import com.gitlab.model.Passport;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PassportMapperTest {

    private final PassportMapper mapper = Mappers.getMapper(PassportMapper.class);

    @Test
    void should_map_example_to_Dto() {
        Passport passport = new Passport();

        passport.setId(1L);
        passport.setCitizenship(Citizenship.RUSSIA);
        passport.setFirstName("testFirstName");
        passport.setLastName("testLastName");
        passport.setPatronym("testPatronym");
        passport.setBirthDate(LocalDate.of(2000, 1, 1));
        passport.setIssueDate(LocalDate.of(2014, 1, 1));
        passport.setPassportNumber("1111 111111");
        passport.setIssuer("Test Otdel Police #1");
        passport.setIssuerNumber("111-111");

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

        PassportDto passportDto = new PassportDto();

        passportDto.setId(1L);
        passportDto.setCitizenship(Citizenship.RUSSIA);
        passportDto.setFirstName("testFirstName");
        passportDto.setLastName("testLastName");
        passportDto.setPatronym("testPatronym");
        passportDto.setBirthDate(LocalDate.of(2000, 1, 1));
        passportDto.setIssueDate(LocalDate.of(2014, 1, 1));
        passportDto.setPassportNumber("1111 111111");
        passportDto.setIssuer("Test Otdel Police #1");
        passportDto.setIssuerNumber("111-111");


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
    void should_map_example_list_to_Dto() {
        Passport passport1 = new Passport();
        passport1.setId(1L);
        passport1.setCitizenship(Citizenship.RUSSIA);
        passport1.setFirstName("testFirstName_1");
        passport1.setLastName("testLastName_1");
        passport1.setPatronym("testPatronym_1");
        passport1.setBirthDate(LocalDate.of(2000, 1, 1));
        passport1.setIssueDate(LocalDate.of(2014, 1, 1));
        passport1.setPassportNumber("1111 111111");
        passport1.setIssuer("Test Otdel Police #1");
        passport1.setIssuerNumber("111-111");

        Passport passport2 = new Passport();
        passport2.setId(2L);
        passport2.setCitizenship(Citizenship.MOLDOVA);
        passport2.setFirstName("testFirstName_2");
        passport2.setLastName("testLastName_2");
        passport2.setPatronym("testPatronym_2");
        passport2.setBirthDate(LocalDate.of(2001, 1, 1));
        passport2.setIssueDate(LocalDate.of(2015, 1, 1));
        passport2.setPassportNumber("2222 222222");
        passport2.setIssuer("Test Otdel Police #2");
        passport2.setIssuerNumber("222-222");

        List<Passport> passports = new ArrayList<>(2);
        passports.add(passport1);
        passports.add(passport2);

        List<PassportDto> passportDtos = mapper.toDto(passports);

        assertNotNull(passportDtos);
        assertEquals(passports.size(), passportDtos.size());

        for (int i = 0; i < passports.size(); i++) {
            Passport expectedResult = passports.get(i);
            PassportDto actualResult = passportDtos.get(i);

            assertNotNull(actualResult);
            assertEquals(expectedResult.getId(), actualResult.getId());
            assertEquals(expectedResult.getCitizenship(), actualResult.getCitizenship());
            assertEquals(expectedResult.getFirstName(), actualResult.getFirstName());
            assertEquals(expectedResult.getLastName(), actualResult.getLastName());
            assertEquals(expectedResult.getPatronym(), actualResult.getPatronym());
            assertEquals(expectedResult.getBirthDate(), actualResult.getBirthDate());
            assertEquals(expectedResult.getIssueDate(), actualResult.getIssueDate());
            assertEquals(expectedResult.getPassportNumber(), actualResult.getPassportNumber());
            assertEquals(expectedResult.getIssuer(), actualResult.getIssuer());
            assertEquals(expectedResult.getIssuerNumber(), actualResult.getIssuerNumber());
        }
    }

    @Test
    void should_map_exampleDto_list_to_Entity() {
        PassportDto passportDto1 = new PassportDto();
        passportDto1.setId(1L);
        passportDto1.setCitizenship(Citizenship.RUSSIA);
        passportDto1.setFirstName("testFirstName_1");
        passportDto1.setLastName("testLastName_1");
        passportDto1.setPatronym("testPatronym_1");
        passportDto1.setBirthDate(LocalDate.of(2000, 1, 1));
        passportDto1.setIssueDate(LocalDate.of(2014, 1, 1));
        passportDto1.setPassportNumber("1111 111111");
        passportDto1.setIssuer("Test Otdel Police #1");
        passportDto1.setIssuerNumber("111-111");

        PassportDto passportDto2 = new PassportDto();
        passportDto2.setId(2L);
        passportDto2.setCitizenship(Citizenship.MOLDOVA);
        passportDto2.setFirstName("testFirstName_2");
        passportDto2.setLastName("testLastName_2");
        passportDto2.setPatronym("testPatronym_2");
        passportDto2.setBirthDate(LocalDate.of(2001, 1, 1));
        passportDto2.setIssueDate(LocalDate.of(2015, 1, 1));
        passportDto2.setPassportNumber("2222 222222");
        passportDto2.setIssuer("Test Otdel Police #2");
        passportDto2.setIssuerNumber("222-222");

        List<PassportDto> passportDtos = new ArrayList<>(2);
        passportDtos.add(passportDto1);
        passportDtos.add(passportDto2);

        List<Passport> passports = mapper.toEntity(passportDtos);

        assertNotNull(passports);
        assertEquals(passportDtos.size(), passports.size());

        for (int i = 0; i < passportDtos.size(); i++) {
            PassportDto expectedResult = passportDtos.get(i);
            Passport actualResult = passports.get(i);

            assertNotNull(actualResult);
            assertEquals(expectedResult.getId(), actualResult.getId());
            assertEquals(expectedResult.getCitizenship(), actualResult.getCitizenship());
            assertEquals(expectedResult.getFirstName(), actualResult.getFirstName());
            assertEquals(expectedResult.getLastName(), actualResult.getLastName());
            assertEquals(expectedResult.getPatronym(), actualResult.getPatronym());
            assertEquals(expectedResult.getBirthDate(), actualResult.getBirthDate());
            assertEquals(expectedResult.getIssueDate(), actualResult.getIssueDate());
            assertEquals(expectedResult.getPassportNumber(), actualResult.getPassportNumber());
            assertEquals(expectedResult.getIssuer(), actualResult.getIssuer());
            assertEquals(expectedResult.getIssuerNumber(), actualResult.getIssuerNumber());
        }
    }
}
