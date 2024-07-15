package br.com.erudio.controllers;

import br.com.erudio.services.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController()
@RequestMapping()
public class MathController {
  private final AtomicLong counter = new AtomicLong();

  @Autowired
  private MathService service;

  @RequestMapping(
      value = "sum/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double sum(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) {
    return this.service.sum(numberOne, numberTwo);
  }

  @RequestMapping(
      value = "subtraction/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double subtraction(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) {
    return this.service.subtraction(numberOne, numberTwo);
  }

  @RequestMapping(
      value = "multiplication/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double multiplication(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) {
    return this.service.multiplication(numberOne, numberTwo);
  }

  @RequestMapping(
      value = "division/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double division(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) {
    return this.service.division(numberOne, numberTwo);
  }

  @RequestMapping(
      value = "average/{numberOne}/{numberTwo}",
      method = RequestMethod.GET
  )
  public Double average(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) {
    return this.service.average(numberOne, numberTwo);
  }

  @RequestMapping(
      value = "squareroot/{number}",
      method = RequestMethod.GET
  )
  public Double squareRoot(
      @PathVariable(value = "number") String number
  ) {
    return this.service.squareRoot(number);
  }
}
