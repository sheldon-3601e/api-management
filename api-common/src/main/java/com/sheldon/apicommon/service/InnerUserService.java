package com.sheldon.apicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sheldon.apicommon.model.entity.InterfaceInfo;
import com.sheldon.apicommon.model.entity.User;
import com.sheldon.apicommon.model.entity.UserInterfaceInfo;

/**
 * @ClassName UserInterfaceInfoService
 * @Author 26483
 * @Date 2024/1/15 12:08
 * @Version 1.0
 */
public interface InnerUserService {

    /**
     * 数据库是否已经分配给用户密钥（accessKey）
     *
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);

}
