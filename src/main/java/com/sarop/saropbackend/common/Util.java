package com.sarop.saropbackend.common;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.UUID;

@UtilityClass
public class Util {
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}
