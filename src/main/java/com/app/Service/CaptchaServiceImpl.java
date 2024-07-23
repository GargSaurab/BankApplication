package com.app.Service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.app.CustomException.InvalidInputException;

import jakarta.servlet.http.HttpSession;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    // To store the captcha in in-memory distributed cache via Redis
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Array of fonts which is used to select a random font
    private static final String[] fonts = { "Jokerman", "Showcard Gothic", "Brush Script MT", "Mistral", "Rage Italic",
            "Chiller", "Papyrus", "Viner Hand ITC", "Harlow Solid Italic", "Blackadder ITC" };

    //  String to get random 6 character captcha
    private static final String alphaNumeric = "a1B2c3D4e5F6g7H8i9J1k2Lm3No4P5q6Rs7Tu8V9w0XyZ1Ab2Cd3Ef4Gh5Ij6Kl7Mn8Op9Qr0StU1v2Wx3Yz";

    public Logger logger = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    @Override
    public ImmutablePair<String, BufferedImage> getCaptcha() {

        //  Id for captcha to easily retrieval from cache
        String captchaId = UUID.randomUUID().toString();

        // gets a random alphanumeric captcha
        String captchaVal = randomizeCaptcha();

        logger.info(captchaVal);

        // Saves the captcha in the client's session
        redisTemplate.opsForValue().set(captchaId, captchaVal, 60, TimeUnit.SECONDS);

        // gets a random font each time
        String font = fonts[new SecureRandom().nextInt(fonts.length)];

        logger.info(font);

        int width = 200;
        int height = 100;

        BufferedImage captcha = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = captcha.createGraphics();
        g.setColor(Color.WHITE); // Applied on All subsequent drawing operations until another
        // setColor is applied.
        g.fillRect(0, 0, width, height);

        // puts random lines in the captcha
        g.setColor(Color.RED); 
        for (int i = 0; i < 10; i++) {
            g.drawLine(new Random().nextInt(width), new Random().nextInt(height), new Random().nextInt(width),
                    new Random().nextInt(height));
        }

        g.setFont(new Font(font, Font.ITALIC, 40)); // All subsequent drawing operations, like drawing text or shapes,
                                                    // will use black color.
        g.setColor(Color.black);

        // Centers the CAPTCHA text horizontally and vertically within the image.
        int x = (width - g.getFontMetrics().stringWidth(captchaVal)) / 2;
        int y = (height + g.getFontMetrics().getAscent()) / 2;

        g.drawString(captchaVal, x, y);
        g.dispose();

        // ImmutablePair class from Apache Commons Lang is a utility class designed to hold a pair of related objects.
        return new ImmutablePair<>(captchaId, captcha);

    }

    public String randomizeCaptcha() {
        StringBuilder str = new StringBuilder(6);

        SecureRandom random = new SecureRandom();

        int max = alphaNumeric.length() - 1;

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(max);

            str.append(alphaNumeric.charAt(index));
        }

        return str.toString();
    }

    @Override
    public void validateCaptcha(HttpSession session, String captcha) {

        logger.info(captcha);
         
         if(!session.getAttribute("captcha").equals(captcha))
           {
             throw new InvalidInputException("Wrong Captcha");
           }
    }

}

// java.awt.image.bufferedimage: To hold the image, we create the BufferedImage
// object;
// we use BufferedImage class. This object is used to store an image in RAM.

// Graphics is an abstract class provided by Java AWT which is used to draw or
// paint on the
// components. It consists of various fields which hold information like
// components to be
// painted, font, color, XOR mode, etc., and methods that allow drawing various
// shapes on the GUI components.