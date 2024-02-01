package com.sheldon.apibackend.model.dto.userInterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName userInterfaceInfoQueryTotalAndRemainRequest
 * @Author 26483
 * @Date 2024/1/24 10:45
 * @Version 1.0
 * @Description UserInterfaceInfoDetailRequest
 */
@Data
public class UserInterfaceInfoDetailRequest implements Serializable{

    /**
     * 接口 id
     */
    private Long interfaceInfoId;

    private static final long serialVersionUID = 1L;

}
