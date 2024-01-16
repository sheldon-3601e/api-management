package com.sheldon.apibackend.service.impl.inner;

import com.sheldon.apibackend.service.UserInterfaceInfoService;
import com.sheldon.apicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @ClassName InnerUserInterfaceInfoServiceImpl
 * @Author 26483
 * @Date 2024/1/15 13:04
 * @Version 1.0
 * @Description TODO
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public Boolean invokeCount(Long userId, Long interfaceInfoId) {
        return userInterfaceInfoService.invokeCount(userId, interfaceInfoId);
    }
}
