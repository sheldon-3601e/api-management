package com.sheldon.apibackend.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sheldon.apibackend.model.dto.userInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.sheldon.apicommon.model.entity.UserInterfaceInfo;

/**
 * @author sheldon
 * @description 针对表【user_interface_info(my_api.`interface_info`)】的数据库操作Service
 * @createDate 2024-01-10 12:24:06
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 获取查询条件
     *
     * @param userInterfaceInfoQueryRequest
     * @return
     */
    Wrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest);

    /**
     * 校验
     *
     * @param userInterfaceInfo
     * @param add
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口加一
     *
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    Boolean invokeCount(Long userId, Long interfaceInfoId);

    UserInterfaceInfo selectOne(Long userId, Long interfaceInfoId);
}
