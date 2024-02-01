package com.sheldon.apibackend.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName userInterfaceInfoQueryTotalAndRemainRequest
 * @Author 26483
 * @Date 2024/1/24 10:45
 * @Version 1.0
 * @Description UserInterfaceInfoDetailVO
 */
@Data
public class UserInterfaceInfoDetailVO implements Serializable{

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    private static final long serialVersionUID = 1L;

}
