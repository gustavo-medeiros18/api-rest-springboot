package br.com.erudio.config;

import br.com.erudio.serialization.converter.YamlJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
   * Constant to represent the 'application/x-yaml' media type.
   */
  private static final MediaType MEDIA_TYPE_APPLICATION_YML =
      MediaType.valueOf("application/x-yaml");

  /**
   * The Value annotation is used to inject the value of the specified
   * property into a specific field. In this case, we are injecting the
   * value of the 'cors.originPatterns' property into the corsOriginPatterns
   * field.
   * <p>
   * Here, the injected value is a comma-separated list of allowed origins for
   * CORS requests. If the property is not set, the default value is used.
   * <p>
   * The injected value comes from the application.properties file.
   */
  @Value("${cors.originPatterns: default}")
  private String corsOriginPatterns = "";

  /**
   * The extendMessageConverters method is used to add a new message converter
   * to the list of converters.
   * <p>
   * Here, we are adding a new message converter that converts the response
   * body to YAML format.
   */
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(new YamlJackson2HttpMessageConverter());
  }

  /**
   * The addCorsMappings method is used to configure the CORS (Cross-Origin
   * Resource Sharing) policy in the application.
   * <p>
   * Here, we are allowing requests from the origins specified in the
   * corsOriginPatterns field. We are also allowing all HTTP methods and
   * credentials to be sent with the request.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    var allowedOrigins = corsOriginPatterns.split(",");

    registry.addMapping("/**")
        .allowedMethods("*")
        .allowedOrigins(allowedOrigins)
        .allowCredentials(true);
  }

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
        // Disable specifying media type through URL parameter.
        .favorParameter(false)
        // Don't ignore the HTTP Accept header.
        .ignoreAcceptHeader(false)
        // Allow using any file extension for media types, not just registered ones.
        .useRegisteredExtensionsOnly(false)
        // Set default content type to JSON if no specific media type is requested.
        .defaultContentType(MediaType.APPLICATION_JSON)
        // Associate 'json' parameter value with APPLICATION_JSON media type.
        .mediaType("json", MediaType.APPLICATION_JSON)
        // Associate 'xml' parameter value with APPLICATION_XML media type.
        .mediaType("xml", MediaType.APPLICATION_XML)
        // Associate 'x-yaml' parameter value with MEDIA_TYPE_APPLICATION_YML media type.
        .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
  }
}
