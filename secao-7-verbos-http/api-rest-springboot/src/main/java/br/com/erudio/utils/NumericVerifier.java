package br.com.erudio.utils;

public class NumericVerifier {
  public static boolean isNumeric(String strNumber) {
    if (strNumber == null) return false;

    String number = strNumber.replaceAll(",", ".");
    return number.matches("[-+]?[0-9]*\\.?[0-9]+");
  }
}
