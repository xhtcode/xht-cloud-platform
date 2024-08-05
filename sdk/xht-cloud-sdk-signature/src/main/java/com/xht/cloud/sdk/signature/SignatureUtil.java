package com.xht.cloud.sdk.signature;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 描述 ：签名加密
 *
 * @author 小糊涂
 **/
public final class SignatureUtil {

    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String EMPTY_STR = "";
    private static final String DELIMITED = ":";

    /**
     * 生成签名
     *
     * @param prefix        appId+appKey+nonce+timestamp
     * @param requestParams 请求地址后缀参数
     * @param body          请求体内容
     * @return 加密后的数据
     */
    public static String generateSignature(String prefix, Map<String, String[]> requestParams, String body) {
        if (!StringUtils.hasText(prefix)) throw new RuntimeException("the prefix argument must not be null");
        String concat = prefix
                .concat(DELIMITED).concat(parseRequestParams(requestParams))
                .concat(DELIMITED).concat(body);
        return generateSignature(concat.getBytes(StandardCharsets.UTF_8));
    }

    private static String parseRequestParams(Map<String, String[]> requestParams) {
        if (Objects.isNull(requestParams)) return EMPTY_STR;
        StringBuilder builder = new StringBuilder();
        Map<String, String[]> params = new HashMap<>(requestParams);
        params.forEach((k, v) -> {
            for (String s : v) {
                builder.append(String.format("%s=%s&", k, s));
            }
        });
        return builder.toString();
    }

    /**
     * 生成签名
     */
    private static String generateSignature(byte[] bytes) {
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
