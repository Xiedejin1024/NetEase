package com.example.netease.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5util {

    private static MessageDigest mDigest = null;

    static {
        try {
            mDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    /*
     * 对key进行MD5加密，如果没有MD5加密算法，直接使用key对于的hash值
     * */

    public static String Str2MD5(String key) {
        String cacheKey;
        if (mDigest == null) {
            return String.valueOf(key.hashCode());
        }
        mDigest.update(key.getBytes());
        cacheKey = bytes2HexString(mDigest.digest());
        return cacheKey;
    }

    private static String bytes2HexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xff);
            if (hexString.length() == 1) {
                sb.append("0");
            }
            sb.append(hexString);
        }

        return sb.toString();
    }


    public static String MD52Str(String md5) {
        byte[] array = mDigest.digest(md5.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(Integer.toHexString((array[i] & 0xff) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }


}
