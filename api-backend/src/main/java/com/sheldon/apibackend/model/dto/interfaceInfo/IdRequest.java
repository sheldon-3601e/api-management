package com.sheldon.apibackend.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName IdRequest
 * @Author sheldon
 * @Date 2024/1/9 15:14
 * @Version 1.0
 * @Description TODO
 */
@Data
public class IdRequest implements Serializable {

    private Long id;

    private static final long serialVersionUID = 1L;

}
