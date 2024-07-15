package br.com.erudio.apirestspringboot;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController()
@RequestMapping("/greeting")
public class GreetingController {
  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  /**
   * The RequestParam annotation is used to bind a
   * query parameter to a method parameter. It accepts
   * some attributes, such as value and defaultValue.
   */
  @RequestMapping(method = RequestMethod.GET)
  public Greeting greeting(
      @RequestParam(value = "name", defaultValue = "World") String name
  ) {
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }
}
