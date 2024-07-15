package br.com.erudio.controllers;

import br.com.erudio.exceptions.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController()
@RequestMapping()
public class MathController {
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping(
      value = "sum/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double sum(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException("Please set a numeric value!");

    return convertToDouble(numberOne) + convertToDouble(numberTwo);
  }

  @RequestMapping(
      value = "sub/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double sub(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException("Please set a numeric value!");

    return convertToDouble(numberOne) - convertToDouble(numberTwo);
  }

  @RequestMapping(
      value = "mult/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double mult(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException("Please set a numeric value!");

    return convertToDouble(numberOne) * convertToDouble(numberTwo);
  }

  @RequestMapping(
      value = "div/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double div(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    if (convertToDouble(numberTwo) == 0)
      throw new UnsupportedMathOperationException("Division by zero is not allowed!");

    return convertToDouble(numberOne) / convertToDouble(numberTwo);
  }

  @RequestMapping(
      value = "avg/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double avg(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo))
      throw new UnsupportedMathOperationException("Please set a numeric value!");

    return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
  }

  private Double convertToDouble(String strNumber) {
    if (strNumber == null) return 0D;

    String number = strNumber.replaceAll(",", ".");
    if (isNumeric(number)) return Double.parseDouble(number);

    return 0D;
  }

  private boolean isNumeric(String strNumber) {
    if (strNumber == null) return false;

    String number = strNumber.replaceAll(",", ".");
    return number.matches("[-+]?[0-9]*\\.?[0-9]+");
  }
}
