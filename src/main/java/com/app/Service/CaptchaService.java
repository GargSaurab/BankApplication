package com.app.Service;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.tuple.ImmutablePair;

public interface CaptchaService {

    public ImmutablePair<String, BufferedImage> getCaptcha();

    public void validateCaptcha(String captchaId, String captcha);

}
