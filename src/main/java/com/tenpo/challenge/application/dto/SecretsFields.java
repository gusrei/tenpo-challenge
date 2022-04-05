package com.tenpo.challenge.application.dto;

import java.util.stream.Stream;

/**
 * this enum represent the sensible user information
 */
public enum SecretsFields {

    PASSWORD("password"),
    CONFIRMED_PASSWORD("passwordConfirm");

    private String fieldName;

    SecretsFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public static Stream<SecretsFields> stream() {
        return Stream.of(SecretsFields.values());
    }

    public String getFieldName() {
        return fieldName;
    }

}
