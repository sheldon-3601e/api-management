package com.sheldon.apiclientsdk.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ApiInvokeRequestDTO
 * @Author 26483
 * @Date 2024/1/23 10:28
 * @Version 1.0
 * @Description TODO
 */
@Data
public class ApiInvokeRequestDTO implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 请求参数
     */
    private String requestParams;

    private static final long serialVersionUID = 1L;

}
