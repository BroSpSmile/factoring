package com.smile.start.commons;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * 唯一序列号生成器
 * @author Joseph
 * @version v1.0 2019/1/23 19:46, SerialNoGenerator.java
 * @since 1.8
 */
public class SerialNoGenerator {
    private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private SerialNoGenerator() {
    }

    public static String generateSerialNo(String tag, int num) {
        Random random = new SecureRandom();
        byte[] bytes = new byte[num];
        random.nextBytes(bytes);

        char[] chars = new char[bytes.length];

        for(int i = 0; i < bytes.length; ++i) {
            chars[i] = DEFAULT_CODEC[(bytes[i] & 255) % DEFAULT_CODEC.length];
        }

        String randomCode = new String(chars);
        String time = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        return tag + time + randomCode;
    }
}
