package com.xht.cloud.framework.utils.secret;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述 ：MD5工具类
 *
 * @author 小糊涂
 **/
public class MD5Utils {
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 生成签名
     */
    public static String generateSignature(String sig) {
        return generateSignature(sig.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成签名
     */
    public static String generateSignature(byte[] bytes) {
        char[] hexDigest = digestAsHexChars(bytes);
        return new String(hexDigest);
    }

    private static byte[] digest(byte[] bytes) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            return instance.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm MD5", e);
        }
    }


    private static char[] digestAsHexChars(byte[] bytes) {
        return encodeHex(digest(bytes));
    }

    private static char[] encodeHex(byte[] bytes) {
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return chars;
    }
}
