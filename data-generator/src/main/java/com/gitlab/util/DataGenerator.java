package com.gitlab.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class DataGenerator {
    public static String generateRandomString(int lengthSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < lengthSequence; i++) {
            int randomNumber = random.nextInt(0, 10);
            stringBuilder.append(randomNumber);
        }

        return stringBuilder.toString();
    }
}
