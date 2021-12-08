package com.sc.ballot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse implements Response{
    private int code;
    private String message;
    private Response value;

    public GenericResponse() {

    }
    public GenericResponse(int code, String message) {
        super();
        this.code = code;
        this.message = message;
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

    public Response getValue() {
        return value;
    }

    public void setValue(Response response) {
        this.value = response;
    }
}
