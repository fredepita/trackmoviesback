package fr.epita.trackmoviesback.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * config pour la deserialisation des json en objet *
 */
@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        //les champs json avec "" devant etre mappé dans un tableau  seront considéré comme des tableaux null
        //il faut aussi indiquer dans les DTO l'annotation @JsonIgnoreProperties(ignoreUnknown = true)
        return new
                ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT )
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT )
                .enable(JsonParser.Feature.IGNORE_UNDEFINED);

    }
}
