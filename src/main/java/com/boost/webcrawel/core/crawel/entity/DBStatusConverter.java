package com.boost.webcrawel.core.crawel.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class DBStatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        if (Objects.nonNull(status)) {
            return status.getDatabaseValue();
        }
        return null;
    }

    @Override
    public Status convertToEntityAttribute(String databaseValue) {
        if (StringUtils.isNotEmpty(databaseValue)) {
            return Status.of(databaseValue);
        }
        return null;
    }
}
