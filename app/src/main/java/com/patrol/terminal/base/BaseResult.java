package com.patrol.terminal.base;

/**
 * Created by fg on 2017/7/25.
 * 解析实体基类
 */

public class BaseResult<T> {
    private static int SUCCESS_CODE = 1;
    private int code;
    private String message;
    private String msg;
    private T result;
    private boolean error;
    private String type;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSuccess(){
        return getCode() == SUCCESS_CODE;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResults() {
        return result;
    }

    public void setResults(T results) {
        this.result = results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
