package com.sheldon.apibackend.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author <a href="https://github.com/sheldon-3601e">Sheldon</a>
 * @from <a href="https://github.com/sheldon-3601e">github</a>
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 服务器地址
     */
    private String host;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭 1-开启）
     */
    private Integer statue;

    /**
     * 请求方式
     */
    private String method;

}