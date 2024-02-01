package com.sheldon.apiclientsdk.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.sheldon.apiclientsdk.model.User;
import com.sheldon.apiclientsdk.service.BaseService;
import com.sheldon.apiclientsdk.utils.SignUtils;

import java.util.HashMap;

/**
 * @ClassName ApiClient
 * @Author 26483
 * @Date 2024/1/8 15:06
 * @Version 1.0
 * @Description ApiClient
 */
public class ApiClient {

    private static final String GATEWAY_URL = "http://localhost:8090";

    private String accessKey;
    private String secretKey;

    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String doInvoke(String body, String interfaceInfoId) {
        InterfaceInfoServiceRegistry interfaceInfoServiceRegistry = new InterfaceInfoServiceRegistry();
        BaseService interfaceInfoService = interfaceInfoServiceRegistry.getInterfaceInfoService(interfaceInfoId);
        String result = interfaceInfoService.doInvoke(body, interfaceInfoId, accessKey, secretKey);
        return result;
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




    public String postJson(User user, String url, String interfaceInfoId) {
        String json = JSONUtil.toJsonStr(user);
        String result = HttpRequest.post(GATEWAY_URL + url)
                .addHeaders(SignUtils.getHeaders(json, interfaceInfoId, accessKey, secretKey))
                .body(json)
                .execute().body();
        return result;
    }

}
