package com.gitlab.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

@UtilityClass
public class DataGenerator {

    private static final Random random = new Random();

    public static String generateRandomNumericString(int lengthSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lengthSequence; i++) {
            int randomNumber = random.nextInt(0, 10);
            stringBuilder.append(randomNumber);
        }
        return stringBuilder.toString();
    }

    public static String generateRandomString(int lengthSequence) {
        return RandomStringUtils.randomAlphabetic(lengthSequence);
    }

    public static String generatePassportIssuerNumber() {
        return generateRandomNumericString(3) + "-" + generateRandomNumericString(3);
    }

    public static String generatePassportNumber() {
        return generateRandomNumericString(4) + " " + generateRandomNumericString(6);
    }
}