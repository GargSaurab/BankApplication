package com.app.Service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService{

public Logger logger = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    @Override
    public BufferedImage getCaptcha() {

        int min = 100000;
        int max = 999999;

        Random random = new Random();
        int captchaVal = random.nextInt(max - min + 1) + min;
        String captchaString = String.valueOf(captchaVal);
        
        logger.info(captchaString);
        
        int width = 200;
        int height = 100;

        BufferedImage captcha = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = captcha.getGraphics();
        g.setColor(Color.WHITE); // Applied on All subsequent drawing operations until another
        //  setColor is applied.
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Courier New", Font.ITALIC, 40)); // All subsequent drawing operations, like drawing text or shapes, will use black color.
        g.setColor(Color.black);

        //Centers the CAPTCHA text horizontally and vertically within the image.
        int x = (width - g.getFontMetrics().stringWidth(captchaString)) / 2;
        int y = (height + g.getFontMetrics().getAscent()) / 2;

        g.drawString(captchaString, x, y);
        g.dispose();

        return captcha ;
       
    }

}


// java.awt.image.bufferedimage: To hold the image, we create the BufferedImage object;
//  we use BufferedImage class. This object is used to store an image in RAM.


// Graphics is an abstract class provided by Java AWT which is used to draw or paint on the 
// components. It consists of various fields which hold information like components to be 
//painted, font, color, XOR mode, etc., and methods that allow drawing various shapes on the GUI components.