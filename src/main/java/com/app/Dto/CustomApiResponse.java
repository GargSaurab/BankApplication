package com.app.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomApiResponse <T> {
    
    private ResponseInfo info;
    private  T  data;
}
