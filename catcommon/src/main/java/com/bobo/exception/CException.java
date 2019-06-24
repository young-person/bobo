package com.bobo.exception;

public class CException extends RuntimeException{

	private static final long serialVersionUID = -5177573753140652712L;
    public CException() {
        super();
    }

    public CException(String message) {
        super(message);
    }

    public CException(String message, Throwable cause) {
        super(message, cause);
    }

    public CException(Throwable cause) {
        super(cause);
    }

}
