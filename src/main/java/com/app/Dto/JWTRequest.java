package com.app.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JWTRequest {

    private String name;
    private String password;
    private String captchaId;
    private String captcha;

}
