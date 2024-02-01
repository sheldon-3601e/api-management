package com.sheldon.apiclientsdk.service;

/**
 * @ClassName BaseService
 * @Author 26483
 * @Date 2024/1/23 10:27
 * @Version 1.0
 * @Description BaseService
 */
public interface BaseService {

    String doInvoke(String body, String interfaceInfoId, String accessKey, String secretKey);

}
