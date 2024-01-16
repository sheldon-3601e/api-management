package com.sheldon.apibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sheldon.apibackend.common.ErrorCode;
import com.sheldon.apibackend.exception.BusinessException;
import com.sheldon.apibackend.mapper.InterfaceInfoMapper;
import com.sheldon.apicommon.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @ClassName InnerInterfaceInfoServiceImplTest
 * @Author 26483
 * @Date 2024/1/16 1:01
 * @Version 1.0
 * @Description TODO
 */
class InnerInterfaceInfoServiceImplTest {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Test
    void getInvokeInterfaceInfo() {
        String url = "http://localhost:8123/api/name/user";
        String method = "POST";
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        System.out.println(interfaceInfo);
    }
}