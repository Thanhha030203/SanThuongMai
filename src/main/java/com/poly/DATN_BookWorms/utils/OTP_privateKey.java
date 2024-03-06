package com.poly.DATN_BookWorms.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class OTP_privateKey {
		
	public int OTP() { 
		int c;
		do { 
			int b = ThreadLocalRandom.current().nextInt(10,20);
			int a = ThreadLocalRandom.current().nextInt(2000,9999);
			c = privatekey(a, b);
			
		}while(c<100000);
		return c;
	}

public static int privatekey(int euler,int pk) { 
	int max =0;
	for(int i =1; i<=1000;i++) { 
		int x = ((i* euler)+1)/pk;
		int y = (pk*x) % euler;
		if(y==1) {
			if(x>max) { 
				max = x;
			}
		}
		}
	return max;
		}
}
