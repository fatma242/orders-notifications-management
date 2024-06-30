package com.example.demo.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
public class ResponseWrapper<T> {
    private T data;
    private String errorMessage;
    public ResponseWrapper(T data, String errorMessage){
        this.data = data;
        this.errorMessage = errorMessage;
    }
    public T getData(){
        return data;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
