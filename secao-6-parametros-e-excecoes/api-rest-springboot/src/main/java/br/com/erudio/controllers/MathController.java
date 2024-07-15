package br.com.erudio.controllers;

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
  ) {
    return 1D;
  }
}
