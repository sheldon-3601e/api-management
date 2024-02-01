package com.sheldon.apibackend.service;

import com.sheldon.apicommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @ClassName UserInterfaceInfoTest
 * @Author 26483
 * @Date 2024/1/10 14:06
 * @Version 1.0
 * @Description UserInterfaceInfoTest
 */
@SpringBootTest
public class UserInterfaceInfoTest {

    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    public void testInvokeCount() {
        boolean result = innerUserInterfaceInfoService.invokeCount(1L, 1L);
        System.out.println(result);
    }

}
