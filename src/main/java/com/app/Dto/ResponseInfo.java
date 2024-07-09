package com.app.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseInfo {
    
    private int status;
    private String message;

}
