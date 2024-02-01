package com.sheldon.apiinterface.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @ClassName MyController
 * @Author 26483
 * @Date 2024/1/23 1:14
 * @Version 1.0
 * @Description 自己开发的模拟接口
 */
@RestController
@RequestMapping("/my")
public class MyController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/poisonous_chicken_soup")
    public String getPoisonousChickenSoup(){
        String i = new Random().nextInt(1000) + "";
        return stringRedisTemplate.opsForValue().get(i);

    }

}
