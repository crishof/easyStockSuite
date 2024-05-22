package org.crishof.invoicesv.controller;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T data;
    private String error;

    public ApiResponse(T data) {
        this.data = data;
        this.error = null;
    }

    public ApiResponse(String error) {
        this.error = error;
        this.data = null;
    }
}
