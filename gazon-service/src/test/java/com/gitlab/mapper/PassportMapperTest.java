package com.gitlab.mapper;

import com.gitlab.dto.PassportDto;
import com.gitlab.model.Passport;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PassportMapperTest {

    private final PassportMapper mapper = Mappers.getMapper(PassportMapper.class);

    @Test
    void should_map_example_to_Dto() {
        Passport passport = new Passport();

        passport.setId(1L);
        passport.setCitizenship(Passport.Citizenship.RUSSIA);
        passport.setFirstName("testFirstName");
        passport.setLastName("testLastName");
        passport.setPatronym("testPatronym");
        passport.setBirthDate(LocalDate.of(2000, 01, 01));
        passport.setIssueDate(LocalDate.of(2014, 01, 01));
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
        passportDto.setCitizenship(Passport.Citizenship.RUSSIA);
        passportDto.setFirstName("testFirstName");
        passportDto.setLastName("testLastName");
        passportDto.setPatronym("testPatronym");
        passportDto.setBirthDate(LocalDate.of(2000, 01, 01));
        passportDto.setIssueDate(LocalDate.of(2014, 01, 01));
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

}
