package br.com.erudio.serialization.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

/**
 * The AbstractJackson2HttpMessageConverter class is an abstract class that needs to
 * be extended to allow the usage of Jackson to convert the response body to YAML.
 */
public class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
  protected YamlJackson2HttpMessageConverter() {
    /**
     * The YAMLMapper class can be used to serialize and deserialize YAML content.
     * The setSerializationInclusion method is used to specify which properties of the
     * object should be included in the serialization.
     *
     * Here, we are using the NON_NULL value to include only properties that are not null.
     *
     * The parseMediaType method is used to parse the media type of the response body.
     * Setting the media type of the response body to application/x-yaml
     */
    super(
        new YAMLMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL),
        MediaType.parseMediaType("application/x-yaml")
    );
  }
}
