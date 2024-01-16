package com.sheldon.service.inner;

import com.sheldon.apicommon.model.entity.InterfaceInfo;
import com.sheldon.apicommon.service.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;

/**
 * @ClassName InnerInterfaceInfoTest
 * @Author 26483
 * @Date 2024/1/16 1:00
 * @Version 1.0
 * @Description TODO
 */
public class InnerInterfaceInfoTest {

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @Test
    public void testGetInvokeInterfaceInfo() {

    }


}
