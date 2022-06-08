package com.boost.webcrawel.core.commons.exception;

import org.springframework.http.HttpStatus;

public enum ServerError {

    INTERNAL_SERVER_ERROR("Internal Error Occurred !", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PAGE("Invalid Page"),
    INVALID_URL("Please provide valid url"),
    UNREGISTERED_URL("This url is unregistered, please register it first"),
    REGISTERED_BEFORE_URL("This url is registered before so it can not be craweled"),
    FAILED_TO_CRAWEL_THIS_WEBSITE("Failed to crawel this website"),
    UN_EXISTED_WEBSITE("This website is not existed"),
    WEBSITE_UNDER_CRAWLING("This website is under craweling now, please try again later");

    public enum Type {
        ERROR, WARNING, INFO;
    }

    private Type type;
    private transient HttpStatus status;
    private boolean notifyDevelopers;
    private String message;

    ServerError(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    ServerError(String message, HttpStatus status) {
        this(message, Type.ERROR, status, false);
    }

    ServerError(String message, Type t, HttpStatus status, boolean notifyDevloper) {
        this.type = t;
        this.status = status;
        this.notifyDevelopers = notifyDevloper;
        this.message = message;
    }


    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


}
