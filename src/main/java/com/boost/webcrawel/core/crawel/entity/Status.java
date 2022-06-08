package com.boost.webcrawel.core.crawel.entity;


import java.util.stream.Stream;

public enum Status {

    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    IGNORED("IGNORED");

    private String databaseValue;

    Status(String databaseValue) {
        this.databaseValue = databaseValue;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    public static Status of(String databaseValue) {
        return Stream.of(Status.values())
                .filter(companyDocumentType -> companyDocumentType.getDatabaseValue().equals(databaseValue))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
