package com.poly.DATN_BookWorms.utils;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
@Service
public class CRC32_SHA256 {
    public  String getCodeCRC32C(String input) {
        String sha256hex = Hashing.crc32()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }
}
