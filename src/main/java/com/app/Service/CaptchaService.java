package com.app.Service;

import java.awt.image.BufferedImage;

import jakarta.servlet.http.HttpSession;

public interface CaptchaService {

    public BufferedImage getCaptcha(HttpSession session);

    public void validateCaptcha(HttpSession session, String captcha);

}
