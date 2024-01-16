package com.sheldon.apibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sheldon.apicommon.model.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sheldon
 * @description 针对表【user_interface_info(my_api.`interface_info`)】的数据库操作Mapper
 * @createDate 2024-01-10 12:24:06
 * @Entity com.sheldon.apibackend.model.entity.UserInterfaceInfo
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listInterfaceInvokeTop(@Param("limit") Integer limit);

}




