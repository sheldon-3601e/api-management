package com.sheldon.apibackend.model.vo;

import com.sheldon.apicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 接口信息封装
 *
 * @author <a href="https://github.com/sheldon-3601e">Sheldon</a>
 * @from <a href="https://github.com/sheldon-3601e">github</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoAnalysisVO extends InterfaceInfo implements Serializable {

    private Integer totalNum;

    private static final long serialVersionUID = 1L;

}
