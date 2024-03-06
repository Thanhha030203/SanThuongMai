package com.poly.DATN_BookWorms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
@Service
public class SHA_1 {

	public String getSHA1Hash( String input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			byte[] hashBytes = messageDigest.digest(input.getBytes());

			StringBuilder sb = new StringBuilder();
			for (byte hashByte : hashBytes) {
				sb.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString().substring(0,10);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
