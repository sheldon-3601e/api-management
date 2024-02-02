package com.sheldon.apiclientsdk.client;

import com.sheldon.apiclientsdk.service.BaseService;
import com.sheldon.apiclientsdk.service.impl.GetPoisonousChickenSoupImpl;
import com.sheldon.apiclientsdk.service.impl.GetUserNameImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InterfaceInfoServiceRegistry
 * @Author 26483
 * @Date 2024/1/23 11:16
 * @Version 1.0
 * @Description 接口服务注册
 */
@Component
public class InterfaceInfoServiceRegistry {

    private final GetPoisonousChickenSoupImpl getPoisonousChickenSoup = new GetPoisonousChickenSoupImpl();
    private final GetUserNameImpl getUserName = new GetUserNameImpl();

    private final Map<String, BaseService> interfaceInfoServiceMap = new HashMap() {
        {
            put("1", getPoisonousChickenSoup);
            put("2", getUserName);
        }
    };


    public BaseService getInterfaceInfoService(String interfaceInfoId) {
        if (interfaceInfoId == null) {
            return null;
        }
        return interfaceInfoServiceMap.get(interfaceInfoId);

    }
}
