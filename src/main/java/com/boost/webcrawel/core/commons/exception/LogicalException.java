package com.boost.webcrawel.core.commons.exception;

public class LogicalException extends BaseException {

    private static final long serialVersionUID = -6527073160488586546L;

    public LogicalException(ServerError err) {
        this(err, err.getMessage());
    }


    public LogicalException(ServerError err, String clientMsg) {
        super(err, clientMsg, null);
    }

}
