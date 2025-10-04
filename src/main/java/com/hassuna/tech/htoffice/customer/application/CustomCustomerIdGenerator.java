package com.hassuna.tech.htoffice.customer.application;

import java.util.UUID;
import java.util.regex.Pattern;

public class CustomCustomerIdGenerator {

  private static final String PREFIX = "HT";
  private static final int RANDOM_PART_LENGTH = 6;
  private static final String B2B_ALIAS = "B";
  private static final String B2C_ALIAS = "C";

  private static final Pattern ID_PATTERN =
      Pattern.compile(
          "^"
              + PREFIX
              + "-("
              + B2B_ALIAS
              + "|"
              + B2C_ALIAS
              + ")-[A-F0-9]{"
              + RANDOM_PART_LENGTH
              + "}$");

  private static String generateCustomerId(String customerAlias) {
    String random =
        UUID.randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, RANDOM_PART_LENGTH)
            .toUpperCase();
    return PREFIX + "-" + customerAlias + "-" + random;
  }

  public static String generateB2bCustomerId() {
    return generateCustomerId(B2B_ALIAS);
  }

  public static String generateB2cCustomerId() {
    return generateCustomerId(B2C_ALIAS);
  }

  public static boolean isValidCustomerId(String id) {
    return id != null && ID_PATTERN.matcher(id).matches();
  }

  public static boolean isValidB2bCustomerId(String id) {
    assertValidCustomerId(id);
    return id.contains("-" + B2B_ALIAS + "-");
  }

  public static void assertValidCustomerId(String id) {
    if (!isValidCustomerId(id)) {
      throw new IllegalArgumentException(
          "Invalid customer id format. Expected "
              + PREFIX
              + "-[A-F0-9]{"
              + RANDOM_PART_LENGTH
              + "}.");
    }
  }
}
