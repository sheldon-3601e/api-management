package com.sheldon.apiclientsdk.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
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

}
