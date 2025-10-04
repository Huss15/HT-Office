package com.hassuna.tech.htoffice.customer.application;

import java.util.UUID;
import java.util.regex.Pattern;

public class CustomCustomerIdGenerator {

    private static final String PREFIX = "HT";
    private static final int RANDOM_PART_LENGTH = 6;
    private static final Pattern B2B_ID_PATTERN =
            Pattern.compile("^" + PREFIX + "-[A-F0-9]{" + RANDOM_PART_LENGTH + "}$");

    public static String generateCustomerId() {
        String random = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, RANDOM_PART_LENGTH)
                .toUpperCase();
        return PREFIX + "-" + random;
    }

    public static boolean isValidCustomerId(String id) {
        return id != null && B2B_ID_PATTERN.matcher(id).matches();
    }

    public static void assertValidCustomerId(String id) {
        if (!isValidCustomerId(id)) {
            throw new IllegalArgumentException(
                    "Invalid customer id format. Expected " + PREFIX + "-[A-F0-9]{" + RANDOM_PART_LENGTH + "}.");
        }
    }
}