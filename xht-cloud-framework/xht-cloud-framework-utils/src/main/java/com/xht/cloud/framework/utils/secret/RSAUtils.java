package com.xht.cloud.framework.utils.secret;

import com.xht.cloud.framework.exception.BizException;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public class RSAUtils {
    private static final String RSA = "RSA";

    /**
     * 生成公钥私钥
     */
    public static KeyPair generate() throws Exception {
        return generate(512);
    }

    /**
     * 生成公钥私钥 秘钥大小不得小于 2048 位
     */
    public static KeyPair generate(int keySize) throws Exception {
        if (keySize < 512) {
            throw new BizException("秘钥大小不得小于 512 位");
        }
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 根据公钥、私钥字符串创建 {@link KeyPair}
     *
     * @param publicKey  公钥
     * @param privateKey 私钥
     * @return {@link KeyPair}
     */
    public static KeyPair create(String publicKey, String privateKey) {
        return new KeyPair(publicKey(publicKey), privateKey(privateKey));
    }

    /**
     * 获取公钥字符串
     */
    public static String publicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * 获取私钥字符串
     */
    public static String privateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * 字符串转换成公钥
     */
    @SneakyThrows
    public static PublicKey publicKey(String publicKey) {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 字符串转换成私钥钥
     */
    @SneakyThrows
    public static PrivateKey privateKey(String privateKey) {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 私钥签名
     */
    public static byte[] sign(PrivateKey key, String algorithm, InputStream in)
            throws InvalidKeyException, NoSuchAlgorithmException, IOException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(key);
        byte[] buffer = new byte[4096];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            signature.update(buffer, 0, len);
        }
        return signature.sign();
    }

    /**
     * 公钥验签
     */
    public static boolean validate(PublicKey key, String algorithm, InputStream in, byte[] sign)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(key);
        byte[] buffer = new byte[4096];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            signature.update(buffer, 0, len);
        }
        return signature.verify(sign);
    }
}
