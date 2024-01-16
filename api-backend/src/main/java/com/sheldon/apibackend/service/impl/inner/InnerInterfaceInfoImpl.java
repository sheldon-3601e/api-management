package com.sheldon.apibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sheldon.apibackend.common.ErrorCode;
import com.sheldon.apibackend.exception.BusinessException;
import com.sheldon.apibackend.mapper.InterfaceInfoMapper;
import com.sheldon.apicommon.model.entity.InterfaceInfo;
import com.sheldon.apicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @ClassName InnerInterfaceInfoImpl
 * @Author 26483
 * @Date 2024/1/16 1:37
 * @Version 1.0
 * @Description TODO
 */
@DubboService
public class InnerInterfaceInfoImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInvokeInterfaceInfo(Long interfaceInfoId) {
        if (interfaceInfoId == null || interfaceInfoId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", interfaceInfoId);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        return interfaceInfo;
    }
}
