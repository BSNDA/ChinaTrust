package com.reddate.chinatrust.exception;


import com.reddate.chinatrust.constatnt.ErrorCode;

/**
 * ChinaTrust Exception. Base Exception for ChinaTrust Project.
 *
 */
@SuppressWarnings("serial")
public class ChinaTrustBaseException extends RuntimeException {

    private ErrorCode errorCode = ErrorCode.BASE_ERROR;

    /**
     * constructor.
     *
     * @param msg exception message
     * @param cause exception object
     */
    public ChinaTrustBaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * constructor.
     *
     * @param msg exception message
     */
    public ChinaTrustBaseException(String msg) {
        super(msg);
    }

    /**
     * constructor.
     *
     * @param errorCode the errorCode
     */
    public ChinaTrustBaseException(ErrorCode errorCode) {
        this(errorCode.getCode() + " - " + errorCode.getCodeDesc());
        this.errorCode = errorCode;
    }

    /**
     * get associated error code.
     *
     * @return ErrorCode
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        StringBuilder builder = new StringBuilder();
        builder
            .append(s)
            .append(". Error code = ")
            .append(getErrorCode().getCode())
            .append(", Error message : ")
            .append(getMessage());
        return builder.toString();
    }
}
