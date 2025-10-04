package com.hassuna.tech.htoffice.base.application;

public class MaskingUtils {

  /**
   * Masks all characters in the input string except for the last three characters. If the input
   * string is null or has three or fewer characters, it is returned unchanged.
   *
   * @param input the input string to be masked
   * @return the masked string with all but the last three characters replaced by asterisks
   */
  public static String maskExceptLast3(String input) {
    if (input == null) {
      return null;
    }
    int len = input.length();
    if (len <= 3) {
      return input;
    }
    int maskCount = len - 3;
    return "*".repeat(maskCount) + input.substring(maskCount);
  }
}
