package com.sheldon.sheldonapi;

import com.sheldon.apiclientsdk.client.ApiClient;
import com.sheldon.apiclientsdk.model.User;
import org.junit.jupiter.api.Test;

/**
 * @ClassName ClientTest
 * @Author 26483
 * @Date 2024/1/8 15:13
 * @Version 1.0
 * @Description TODO
 */
public class ClientTest {

    @Test
    public void testName() {
        String accessKey = "sheldon";
        String secretKey = "sheldon";
        ApiClient apiClient = new ApiClient(accessKey, secretKey);
        String res01 = apiClient.getName("sheldon01");
        String res02 = apiClient.postName("sheldon02");
        User user = new User();
        user.setUsername("sheldon03");
        String res03 = apiClient.postJson(user);
        System.out.println(res01);
        System.out.println(res02);
        System.out.println(res03);

    }

    @Test
    public void testSign() {
        String accessKey = "sheldon";
        String secretKey = "sheldon";
        ApiClient apiClient = new ApiClient(accessKey, secretKey);
        User user = new User();
        user.setUsername("sheldon03");
        String res03 = apiClient.postJson(user);
        System.out.println(res03);
    }
}
