package br.com.erudio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Our WebConfig class must implement the WebMvcConfigurer interface to
 * customize the Java-based configuration for Spring MVC.
 * <p>
 * It's also necessary to add the @Configuration annotation to the class to
 * indicate that it's a configuration class.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
  /**
   * The configureContentNegotiation method is used to configure the content
   * negotiation strategy.
   * <p>
   * Content negotiation is a process in which the server selects one of
   * the various possible response formats to send to the client, based on
   * what the client has requested.
   */
  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer
        // Enable specifying media type through URL parameter.
        .favorParameter(true)
        // Set the URL parameter name to specify the media type.
        .parameterName("mediaType")
        // Ignore the HTTP Accept header.
        .ignoreAcceptHeader(true)
        // Allow using any file extension for media types, not just registered ones.
        .useRegisteredExtensionsOnly(false)
        // Set default content type to JSON if no specific media type is requested.
        .defaultContentType(MediaType.APPLICATION_JSON)
        // Associate 'json' parameter value with APPLICATION_JSON media type.
        .mediaType("json", MediaType.APPLICATION_JSON)
        // Associate 'xml' parameter value with APPLICATION_XML media type.
        .mediaType("xml", MediaType.APPLICATION_XML);
  }
}
