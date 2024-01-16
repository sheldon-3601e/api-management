package com.sheldon.apibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sheldon.apibackend.common.ErrorCode;
import com.sheldon.apibackend.exception.BusinessException;
import com.sheldon.apibackend.mapper.UserMapper;
import com.sheldon.apicommon.model.entity.User;
import com.sheldon.apicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @ClassName InnerUserServiceImpl
 * @Author 26483
 * @Date 2024/1/15 13:06
 * @Version 1.0
 * @Description TODO
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
