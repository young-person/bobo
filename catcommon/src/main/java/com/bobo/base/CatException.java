package com.bobo.base;

/**
 * cat自定义异常
 */
public class CatException extends RuntimeException{

    private static final long serialVersionUID = -1949770547060521702L;

    public CatException(final String message) {
        super(message);
    }

    public CatException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
