package com.example.test.utils;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseData<T> {
    private String code;
    private T data;
    private String message;
}
