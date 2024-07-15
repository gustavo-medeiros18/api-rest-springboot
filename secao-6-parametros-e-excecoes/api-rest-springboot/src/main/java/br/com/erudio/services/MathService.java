package br.com.erudio.services;

import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.utils.NumericConverter;
import org.springframework.stereotype.Service;

import static br.com.erudio.utils.NumericVerifier.isNumeric;
import static br.com.erudio.utils.NumericConverter.convertToDouble;

@Service
public class MathService {
  public Double sum(String numberOne, String numberTwo) {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException(
          "Please set a numeric value!"
      );

    return NumericConverter.convertToDouble(numberOne) +
        NumericConverter.convertToDouble(numberTwo);
  }

  public Double subtraction(String numberOne, String numberTwo) {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException(
          "Please set a numeric value!"
      );

    return convertToDouble(numberOne) -
        convertToDouble(numberTwo);
  }

  public Double multiplication(String numberOne, String numberTwo) {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException(
          "Please set a numeric value!"
      );

    return convertToDouble(numberOne) *
        convertToDouble(numberTwo);
  }

  public Double division(String numberOne, String numberTwo) {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException(
          "Please set a numeric value!"
      );
    if (convertToDouble(numberTwo) == 0)
      throw new UnsupportedMathOperationException(
          "Division by zero is not allowed!"
      );

    return convertToDouble(numberOne) /
        convertToDouble(numberTwo);
  }

  public Double average(String numberOne, String numberTwo) {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException(
          "Please set a numeric value!"
      );

    return (convertToDouble(numberOne) +
        convertToDouble(numberTwo)) / 2;
  }

  public Double squareRoot(String number) {
    if (!isNumeric(number))
      throw new UnsupportedMathOperationException(
          "Please set a numeric value!"
      );

    return Math.sqrt(convertToDouble(number));
  }
}
