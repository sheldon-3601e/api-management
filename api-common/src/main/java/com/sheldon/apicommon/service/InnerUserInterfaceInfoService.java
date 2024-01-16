package com.sheldon.apicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sheldon.apicommon.model.entity.UserInterfaceInfo;

/**
 * @ClassName InnerUserInterfaceInfoService
 * @Author 26483
 * @Date 2024/1/15 12:08
 * @Version 1.0
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口加一
     *
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    Boolean invokeCount(Long userId, Long interfaceInfoId);

}
