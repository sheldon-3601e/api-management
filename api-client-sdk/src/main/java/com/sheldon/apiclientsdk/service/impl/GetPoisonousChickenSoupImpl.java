package com.sheldon.apiclientsdk.service.impl;

import cn.hutool.http.HttpRequest;
import com.sheldon.apiclientsdk.model.ApiInvokeRequestDTO;
import com.sheldon.apiclientsdk.service.BaseService;
import com.sheldon.apiclientsdk.utils.SignUtils;
import org.springframework.stereotype.Service;

import static com.sheldon.apiclientsdk.model.UrlEnum.GATEWAY_URL;

/**
 * @ClassName MyServiceImpl
 * @Author 26483
 * @Date 2024/1/23 10:47
 * @Version 1.0
 * @Description TODO
 */
@Service
public class GetPoisonousChickenSoupImpl implements BaseService {
    @Override
    public String doInvoke(String body, String interfaceInfoId, String accessKey, String secretKey) {

        String result = HttpRequest.get(GATEWAY_URL.getValue() + "/api/my/poisonous_chicken_soup")
                .addHeaders(SignUtils.getHeaders(null, interfaceInfoId, accessKey, secretKey))
                .execute().body();
        return result;
    }
}
