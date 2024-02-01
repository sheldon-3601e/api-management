package com.sheldon.apibackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sheldon.apibackend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.sheldon.apicommon.model.entity.InterfaceInfo;
import com.sheldon.apicommon.model.vo.InterfaceInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sheldon
 * @description 针对表【interface_info(my_api.`interface_info`)】的数据库操作Service
 * @createDate 2024-01-05 16:40:08
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验规则
     *
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

}
