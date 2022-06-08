package com.boost.webcrawel.core.commons.exception;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 8606514442336986896L;
    private final ServerError error;

    private final String developerMessage;

    private final String uniqueIdentifier;

    private final String clientMsg;

    private String traceId;

    protected Object[] params;

    public BaseException(ServerError err) {
        this(err, null);
    }

    public BaseException(ServerError err, String clientMsg) {
        this(err, clientMsg, null);
    }

    public BaseException(ServerError err, String clientMsg, String developerMessage) {
        super(err.getMessage());
        this.error = err;
        this.uniqueIdentifier = String.valueOf(UUID.randomUUID());
        this.clientMsg = clientMsg;
        this.developerMessage = developerMessage;
    }

    public ErrorDto toErrorDto() {
        ErrorDto errDto = new ErrorDto();
        errDto.setHttpStatus(error.getStatus().value());
        errDto.setMessage(String.format(error.getMessage(), params));
        errDto.setClientMsg(clientMsg);
        errDto.setDeveloperMessage(developerMessage);
        errDto.setUniqueIdentifier(uniqueIdentifier);
        errDto.setParams(params);
        errDto.setTraceId(traceId);
        return errDto;
    }

}
