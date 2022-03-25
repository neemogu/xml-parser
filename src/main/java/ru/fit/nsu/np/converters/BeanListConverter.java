package ru.fit.nsu.np.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class BeanListConverter<T> implements AttributeConverter<List<T>, String> {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<T> data) {
        if (data == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> convertToEntityAttribute(String data) {
        try {
            return StringUtils.isEmpty(data) ? Collections.emptyList() : MAPPER.readValue(data, new TypeReference<>() {
            });
        } catch (Exception err) {
            log.error("Fails to convert data {}", data, err);
            return null;
        }
    }
}
