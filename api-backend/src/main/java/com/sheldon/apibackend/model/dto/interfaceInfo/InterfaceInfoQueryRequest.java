package com.sheldon.apibackend.model.dto.interfaceInfo;

import com.sheldon.apibackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/sheldon-3601e">Sheldon</a>
 * @from <a href="https://github.com/sheldon-3601e">github</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

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

    /**
     * 创建人
     */
    private Long userId;

}