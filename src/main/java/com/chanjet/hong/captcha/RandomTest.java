package com.chanjet.hong.captcha;

import java.util.Random;

public class RandomTest {
	public static void main(String[] args) {
		Random random = new Random();
		int total =100;
		while (total > 0){
			total--;
			System.out.println(random.nextBoolean());
		}
	}
}
