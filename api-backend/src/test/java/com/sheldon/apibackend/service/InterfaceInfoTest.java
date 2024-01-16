package com.sheldon.apibackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sheldon.apibackend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.sheldon.apicommon.model.entity.InterfaceInfo;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @ClassName InterfaceInfoTest
 * @Author sheldon
 * @Date 2024/1/5 23:10
 * @Version 1.0
 * @Description TODO
 */
public class InterfaceInfoTest {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Test
    public void test01() {

        InterfaceInfoQueryRequest interfaceInfoQueryRequest = new InterfaceInfoQueryRequest();
        interfaceInfoQueryRequest.setCurrent(1);
        interfaceInfoQueryRequest.setPageSize(5);
        QueryWrapper<InterfaceInfo> queryWrapper = interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest);

        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(1, 5), queryWrapper);
        System.out.println(interfaceInfoPage);

    }

}
