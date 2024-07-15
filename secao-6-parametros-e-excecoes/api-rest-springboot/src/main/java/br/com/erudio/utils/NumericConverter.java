package br.com.erudio.utils;

public class NumericConverter {
  public static Double convertToDouble(String strNumber) {
    if (strNumber == null) return 0D;

    String number = strNumber.replaceAll(",", ".");
    if (NumericVerifier.isNumeric(number)) return Double.parseDouble(number);
    return 0D;
  }
}