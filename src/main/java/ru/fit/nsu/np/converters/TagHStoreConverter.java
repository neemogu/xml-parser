package ru.fit.nsu.np.converters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.fit.nsu.np.openmap.dao.TagBean;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Converter
@Slf4j
public class TagHStoreConverter implements AttributeConverter<Map<String, String>, String> {

    private static final String HSTORE_SEPARATOR_TOKEN = "=>";
    private static final Pattern HSTORE_ENTRY_PATTERN = Pattern.compile(String.format("\"(.*)\"%s\"(.*)\"", HSTORE_SEPARATOR_TOKEN));

    @Override
    public String convertToDatabaseColumn(Map<String, String> tags) {
        if (tags == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> tag: tags.entrySet()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append("\"");
            builder.append(tag.getKey());
            builder.append("\"");
            builder.append(HSTORE_SEPARATOR_TOKEN);
            builder.append("\"");
            builder.append(tag.getValue());
            builder.append("\"");
        }
        return builder.toString();
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return Collections.emptyMap();
        }
        Map<String, String> tags = new HashMap<>();
        final StringTokenizer tokenizer = new StringTokenizer(dbData, ",");

        while (tokenizer.hasMoreTokens()) {
            final Matcher matcher = HSTORE_ENTRY_PATTERN.matcher(tokenizer.nextToken().trim());
            if (matcher.find()) {
                tags.put(matcher.group(1), matcher.group(2));
            }
        }

        return tags;
    }
}
