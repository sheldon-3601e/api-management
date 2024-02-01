package com.sheldon.apiclientsdk.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SignUtils
 * @Author 26483
 * @Date 2024/1/8 16:29
 * @Version 1.0
 * @Description SignUtils
 */
public class SignUtils {

    public static Map<String, String> getHeaders(String body, String interfaceInfoId, String accessKey, String secretKey) {
        String randomNumbers = RandomUtil.randomNumbers(5);
        String timesStamp = String.valueOf(System.currentTimeMillis());
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        headerMap.put("interfaceInfoId", interfaceInfoId);
        headerMap.put("nonce", randomNumbers);
        headerMap.put("body", body);
        headerMap.put("timestamp", timesStamp);
        headerMap.put("sign", genSign(timesStamp, randomNumbers, secretKey));
        return headerMap;
    }

    public static String genSign(String timesStamp, String nonce, String secretKey) {

        String sign = timesStamp + "." + nonce + "." + secretKey;
        Digester sha = new Digester(DigestAlgorithm.SHA256);
        return sha.digestHex(sign);
    }
}
