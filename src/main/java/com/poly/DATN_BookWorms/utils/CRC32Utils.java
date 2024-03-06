package com.poly.DATN_BookWorms.utils;

import java.util.zip.CRC32;

import org.springframework.stereotype.Service;

@Service
public class CRC32Utils {
	public long getCRC32Hash(String input) {
        CRC32 crc32 = new CRC32();
        crc32.update(input.getBytes());
        return crc32.getValue();
    }
}
