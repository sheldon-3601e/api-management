package com.sheldon.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.sheldon.apiclientsdk.model.User;
import com.sheldon.apiclientsdk.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ApiClient
 * @Author 26483
 * @Date 2024/1/8 15:06
 * @Version 1.0
 * @Description TODO
 */
public class ApiClient {

    private static final String GATEWAY_URL = "http://localhost:8090";

    private String accessKey;
    private String secretKey;

    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getName(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.get(GATEWAY_URL + "/api/name/get", paramMap);
        return result;
    }

    public String postName(String name) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post(GATEWAY_URL + "/api/name/post", paramMap);
        return result;
    }


    private Map<String, String> getHeaders(String body, String interfaceInfoId) {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        headerMap.put("interfaceInfoId", interfaceInfoId);
        // headerMap.put("secretKey", secretKey);
         headerMap.put("nonce", RandomUtil.randomNumbers(5));
         headerMap.put("body", body);
         headerMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
         headerMap.put("sign", SignUtils.genSign(body, secretKey));
        return headerMap;
    }

    public String postJson(User user, String url, String interfaceInfoId) {
        String json = JSONUtil.toJsonStr(user);
        String result = HttpRequest.post(GATEWAY_URL + url)
                .addHeaders(getHeaders(json, interfaceInfoId))
                .body(json)
                .execute().body();
        return result;
    }

}
