package com.gitlab.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Citizenship {

    UKRAINE("Украина"),
    BELARUS("Беларусь"),
    RUSSIA("Россия"),
    KAZAKHSTAN("Казахстан"),
    AZERBAIJAN("Азербайджан"),
    ARMENIA("Армения"),
    GEORGIA("Грузия"),
    MOLDOVA("Молдова"),
    TAJIKISTAN("Таджикистан"),
    TURKMENISTAN("Туркменистан"),
    UZBEKISTAN("Узбекистан"),
    KYRGYZSTAN("Кыргызстан");

    private final String citizenshipRussianTranslation;
}