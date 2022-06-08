package com.boost.webcrawel.core.commons.exception.advice;


import brave.Tracer;
import com.boost.webcrawel.core.commons.exception.ErrorDto;
import com.boost.webcrawel.core.commons.exception.LogicalException;
import com.boost.webcrawel.core.commons.exception.ServerError;
import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@ControllerAdvice(annotations = {RestController.class})
public class ApiExceptionAdvice {

    private final static Logger logger = LoggerFactory.getLogger(ApiExceptionAdvice.class);

    private final Tracer tracer;

    @Autowired
    public ApiExceptionAdvice(Tracer tracer) {
        this.tracer = tracer;
    }

    @ExceptionHandler(LogicalException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> processLogicalException(LogicalException ex) {
        ex.setTraceId(tracer.currentSpan().context().traceIdString());
        return new ResponseEntity<>(ex.toErrorDto(), HttpStatus.valueOf(ex.toErrorDto().getHttpStatus()));
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> processRuntimeException(RuntimeException ex) {

        if (ex.getCause() instanceof LogicalException) {
            LogicalException logicalException = (LogicalException) ex.getCause();
            return new ResponseEntity<>(logicalException.toErrorDto(), HttpStatus.valueOf(logicalException.toErrorDto().getHttpStatus()));
        }
        ErrorDto generalError = new ErrorDto();
        generalError.setMessage(ServerError.INTERNAL_SERVER_ERROR.getMessage());
        generalError.setDeveloperMessage(ex.getMessage());
        generalError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        generalError.setUniqueIdentifier(UUID.randomUUID().toString());
        generalError.setTraceId(tracer.currentSpan().context().traceIdString());
        logger.error()
                .field("errorUUID", generalError.getUniqueIdentifier())
                .field("httpStatusCode", generalError.getHttpStatus())
                .field("DeveloperMessage", generalError.getDeveloperMessage())
                .field("Message", generalError.getMessage())
                .field("ClientMsg", generalError.getClientMsg())
                .field("Params", generalError.getParams())
                .field("Code", generalError.getCode())
                .field("traceId", generalError.getTraceId())
                .exception(ServerError.INTERNAL_SERVER_ERROR.getMessage(), ex)
                .log();
        return new ResponseEntity<>(generalError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
