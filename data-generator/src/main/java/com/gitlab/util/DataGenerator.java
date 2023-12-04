package com.gitlab.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class DataGenerator {

    private static final Random random = new Random();

    public static String generateRandomString(int lengthSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lengthSequence; i++) {
            int randomNumber = random.nextInt(0, 10);
            stringBuilder.append(randomNumber);
        }
        return stringBuilder.toString();
    }
}