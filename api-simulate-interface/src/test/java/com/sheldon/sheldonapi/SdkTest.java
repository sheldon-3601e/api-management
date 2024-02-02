package com.sheldon.sheldonapi;


import com.sheldon.apiclientsdk.client.ApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @ClassName SdkTest
 * @Author 26483
 * @Date 2024/1/8 17:16
 * @Version 1.0
 * @Description SdkTest
 */
@SpringBootTest
public class SdkTest {

    @Resource
    private ApiClient apiClient;

    @Test
    public void test() {
//        String result01 = apiClient.getName("sheldon01");
//        User user = new User();
//        user.setUsername("sheldon02");
//        String result02 = apiClient.postJson(user);
//        System.out.println(result01);
//        System.out.println(result02);
    }

}
