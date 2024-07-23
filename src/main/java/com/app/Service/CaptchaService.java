package com.app.Service;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.tuple.ImmutablePair;

import jakarta.servlet.http.HttpSession;

public interface CaptchaService {

    public ImmutablePair<String, BufferedImage> getCaptcha();

    public void validateCaptcha(HttpSession session, String captcha);

}
