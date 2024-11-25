package com.example.comeonplayerserviceassignment.utils;

import java.util.ArrayList;
import java.util.List;

public class ResponseWrapper<T>
{
   private List<ErrorModel> errors = new ArrayList<>();
    private String token;
    private T data;

    public ResponseWrapper() {
    }

    public List<ErrorModel> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorModel> errors) {
        this.errors = errors;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public void addError(ErrorModel error) {
        this.errors.add(error);
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "errors=" + errors +
                ", token='" + token + '\'' +
                ", data=" + data +
                '}';
    }
}
