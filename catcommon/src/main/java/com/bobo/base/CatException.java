package com.bobo.base;

/**
 * cat自定义异常
 */
public class CatException extends RuntimeException{

    private static final long serialVersionUID = -1949770547060521702L;

    private String code;
    private Object data;

    public CatException(final String message) {
        super(message);
    }

    public CatException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CatException(final Throwable cause) {
        super(cause);
    }
    public CatException(final String code,final Object data){
        super(code);
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
