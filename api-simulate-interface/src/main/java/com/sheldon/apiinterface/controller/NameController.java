package com.sheldon.apiinterface.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName NameController
 * @Author 26483
 * @Date 2024/1/8 14:46
 * @Version 1.0
 * @Description TODO
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getName(String name, HttpServletRequest request){
        System.out.println(request.getHeader("sheldon"));
        return "GET Request: " + name;
    }

    @PostMapping("/post")
    public String postName(@RequestParam String name){
        return "POST Request: " + name;
    }

    @PostMapping("/user")
    public String postJson(@RequestBody String name, HttpServletRequest request){
        return "POST json Request: " + name;
    }

}
