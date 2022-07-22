package com.example.system.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES加解密工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class EncryptAesUtil {
    /**
     * 密钥 AES加解密要求key必须要128个比特位（这里需要长度为16，否则会报错）
     */
    private static final String KEY = "aabbccddeeffgghh";
    /**
     * 偏移量
     */
    private static final String IV = "aabbccddeeffgghh";
    /**
     * 算法
     */
    private static final String ALGORITHMS = "AES/CBC/PKCS5Padding";
    /**
     * 静态常量
     */
    private static final String AES = "AES";

    public static void main(String[] args) {
        log.info("加密密钥和解密密钥：{}", KEY);
        String content = "123456";
        log.info("加密前：" + content);
        String encrypt = aesEncrypt(content);
        log.info("加密后：" + encrypt);
        String decrypt = aesDecrypt(encrypt);
        log.info("解密后：" + decrypt);
    }

    /**
     * aes加密
     *
     * @param content 内容
     * @return {@link String}
     */
    public static String aesEncrypt(String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            // 初始化为加密模式的密码器
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            //偏移量
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            //使用加密密钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), AES);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            //加密
            byte[] result = cipher.doFinal(byteContent);
            // 使用base64解码
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * aes解密
     *
     * @param encryptStr 加密str
     * @return {@link String}
     */
    public static String aesDecrypt(String encryptStr) {
        try {
            if (StringUtils.isEmpty(encryptStr)) {
                return null;
            }
            // 初始化为解密模式的密码器
            Cipher cipher = Cipher.getInstance(ALGORITHMS);

            IvParameterSpec iv = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] result = cipher.doFinal(Base64.decodeBase64(encryptStr));

            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
