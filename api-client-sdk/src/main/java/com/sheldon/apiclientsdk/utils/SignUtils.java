package com.sheldon.apiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @ClassName SignUtils
 * @Author 26483
 * @Date 2024/1/8 16:29
 * @Version 1.0
 * @Description TODO
 */
public class SignUtils {


    public static String genSign(String body, String secretKey) {

        String sign = body +"." + secretKey;
        Digester sha = new Digester(DigestAlgorithm.SHA256);
        return sha.digestHex(body);
    }
}
