package com.sheldon.apicommon.service;

import com.sheldon.apicommon.model.entity.InterfaceInfo;

/**
 * @ClassName InnerInterfaceInfo
 * @Author 26483
 * @Date 2024/1/16 1:36
 * @Version 1.0
 * @Description TODO
 */
public interface InnerInterfaceInfoService {

    /**
     *从数据库查询模拟接口是否存在
     *
     * @param url
     * @param method
     * @return
     */
    InterfaceInfo getInvokeInterfaceInfo(String url, String method);
}
