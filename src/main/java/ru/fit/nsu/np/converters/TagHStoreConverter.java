package ru.fit.nsu.np.converters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.fit.nsu.np.openmap.dao.TagBean;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Converter
@Slf4j
public class TagHStoreConverter implements AttributeConverter<List<TagBean>, String> {

    private static final String HSTORE_SEPARATOR_TOKEN = "=>";
    private static final Pattern HSTORE_ENTRY_PATTERN = Pattern.compile(String.format("\"(.*)\"%s\"(.*)\"", HSTORE_SEPARATOR_TOKEN));

    @Override
    public String convertToDatabaseColumn(List<TagBean> tags) {
        if (tags == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        for (TagBean tagBean : tags) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append("\"");
            builder.append(tagBean.getK());
            builder.append("\"");
            builder.append(HSTORE_SEPARATOR_TOKEN);
            builder.append("\"");
            builder.append(tagBean.getV());
            builder.append("\"");
        }
        return builder.toString();
    }

    @Override
    public List<TagBean> convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return Collections.emptyList();
        }
        List<TagBean> tagBeans = new ArrayList<>();
        final StringTokenizer tokenizer = new StringTokenizer(dbData, ",");

        while (tokenizer.hasMoreTokens()) {
            final Matcher matcher = HSTORE_ENTRY_PATTERN.matcher(tokenizer.nextToken().trim());
            if (matcher.find()) {
                TagBean bean = new TagBean();
                bean.setK(matcher.group(1));
                bean.setV(matcher.group(2));
                tagBeans.add(bean);
            }
        }

        return tagBeans;
    }
}
