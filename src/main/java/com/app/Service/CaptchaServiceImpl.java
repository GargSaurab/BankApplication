package com.app.Service;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class CaptchaServiceImpl implements CaptchaService{

    @Override
    public BufferedImage getCaptcha() {

        int min = 100000;
        int max = 999999;

        Random random = new Random();
        int captchaVal = random.nextInt(max - min + 1) + min;
        String captchaString = String.valueOf(captchaVal);
        
        
        int width = 200;
        int height = 100;

        BufferedImage captcha = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = captcha.getGraphics();
        g.setColor(Color.WHITE); // Applied on All subsequent drawing operations until another
        //  setColor is applied.
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Arial", Font.BOLD, 40)); // All subsequent drawing operations, like drawing text or shapes, will use black color.
        g.setColor(Color.black);
        g.drawString(captchaString, max, height);
        g.dispose();

        return captcha ;
       
    }

}


// java.awt.image.bufferedimage: To hold the image, we create the BufferedImage object;
//  we use BufferedImage class. This object is used to store an image in RAM.


// Graphics is an abstract class provided by Java AWT which is used to draw or paint on the 
// components. It consists of various fields which hold information like components to be 
//painted, font, color, XOR mode, etc., and methods that allow drawing various shapes on the GUI 
// components.