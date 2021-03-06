package com.abc.hong.captcha;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.BackgroundProducer;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.backgrounds.SquigglesBackgroundProducer;
import nl.captcha.backgrounds.TransparentBackgroundProducer;
import nl.captcha.gimpy.BlockGimpyRenderer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;
import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.gimpy.RippleGimpyRenderer;
import nl.captcha.gimpy.ShearGimpyRenderer;
import nl.captcha.gimpy.StretchGimpyRenderer;
import nl.captcha.noise.CurvedLineNoiseProducer;
import nl.captcha.noise.NoiseProducer;
import nl.captcha.noise.StraightLineNoiseProducer;
import nl.captcha.text.producer.DefaultTextProducer;


import java.util.Random;
public  class CaptchaGenerator extends java.lang.Object implements java.io.Serializable
{
	
	
	/**
	 * 
	 */
	private static void ShuffleArray(char[] array)
	{
	    int index;
	    char temp;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        temp = array[index];
	        array[index] = array[i];
	        array[i] = temp;
	    }
	}

	
	public static char[] generateText (char[] input) {
		
		    int len =  2 + (int)(Math.random()*4);
//			int len =  5;
		    char[]  temp = new char[len];
		    for(int i=0; i< len; i++) {
		    	int x = new Random().nextInt(input.length);
		    	temp[i] = input[x];
		    }
		return temp;
		
	}
	private static final long serialVersionUID = 1L;

	
	public static void generate_captcha(int image_number, char[] input, String[] background,
			String[] noise, String[] gimpy) 
					throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		//System.out.println("Starting ");
		char[] temp = generateText(input);
		int random_noise = new  Random().nextInt(2);
		int random_background = new  Random().nextInt(2);
		int random_gimpy = 1;
		
		
		Captcha captcha;
		 Captcha.Builder builder = new Captcha.Builder(200, 50);
		 builder.addText(new DefaultTextProducer(temp.length, temp));
		 Random random = new Random();
		 if(random.nextBoolean()){
			 builder.addNoise();
		 }
		 if(random.nextBoolean()){
			 builder.addBackground();
		 }
		
		 for(int i= 0; i< random_noise;i++){
			 int x = new  Random().nextInt(noise.length);
			 String class_name = "nl.captcha.noise."+ noise[x];
			 builder.addNoise((NoiseProducer) Class.forName(class_name).newInstance());
		 }
		 for(int i= 0; i< random_background ;i++){
			 int x = new  Random().nextInt(noise.length);
			 String class_name = "nl.captcha.backgrounds."+background[x];
			 builder.addBackground( (BackgroundProducer) Class.forName(class_name).newInstance());
		 }
//		 for(int i= 0; i< random_gimpy;i++){
//			 int x = new  Random().nextInt(noise.length);
//			 String class_name = "nl.captcha.gimpy."+ gimpy[x];
//			 builder.gimp( (GimpyRenderer) Class.forName(class_name).newInstance());
//		 }
		 
		captcha = builder.build();
	 
		
		BufferedImage bufferedImage = captcha.getImage();
		File outputfile = new File("/Users/honggh/PycharmProjects/Captcha-Decoder/hong-spec-test2/" + image_number + "_" +  captcha.getAnswer() + ".jpg");
		//File outputfile = new File("/Users/geetika/Desktop/" + image_number + "_" +  captcha.getAnswer() + ".jpg");
		ImageIO.write(bufferedImage, "jpg", outputfile);
		
	}
	
	
	public static void main(String args[]) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		int image_number = 0;
		String allChar = "零一二三四五六七八九十百千万亿壹贰叁肆伍陆柒捌玖拾佰仟0123456789";
		char []alpha = new char[allChar.length()];
		for(int i=0;i<allChar.length();i++){
			alpha[i] = allChar.charAt(i);
		}
//		char[] alpha = new char[36];
		int i = 0;
		String [] background = {"FlatColorBackgroundProducer","GradiatedBackgroundProducer",
				"SquigglesBackgroundProducer", "TransparentBackgroundProducer"};
		String [] noise = {"StraightLineNoiseProducer", "CurvedLineNoiseProducer"};
		String [] gimpy = {"FishEyeGimpyRenderer","BlockGimpyRenderer", 
				"DropShadowGimpyRenderer","FishEyeGimpyRenderer","GimpyRenderer", 
				"RippleGimpyRenderer","ShearGimpyRenderer","StretchGimpyRenderer"};
//		for (char letter = 'A'; letter <= 'Z'; letter++)
//		{
//		    alpha[i++] = letter;
//		}
//		for (char letter = 'a'; letter <= 'z'; letter++)
//		{
//		    alpha[i++] = letter;
//		}
//		for (char letter = '0'; letter <= '9'; letter++)
//		{
//		    alpha[i++] = letter;
//		}
		
		ShuffleArray(alpha);

		
		for (image_number = 0; image_number < 1; image_number++) {
//			char[]arr = generateText(alpha);
//			System.out.println(new String(arr));
			for(int index =0;index<200;index++){
				generate_captcha(image_number, alpha, background, noise, gimpy);
				
			}
			System.out.println("multiple of 2000 is done so far");
		}
		System.out.println("Done");
	}
	
}

