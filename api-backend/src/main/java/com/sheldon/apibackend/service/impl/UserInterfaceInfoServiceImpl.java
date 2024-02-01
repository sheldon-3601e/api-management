package com.sheldon.apibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sheldon.apibackend.common.ErrorCode;
import com.sheldon.apibackend.constant.CommonConstant;
import com.sheldon.apibackend.exception.BusinessException;
import com.sheldon.apibackend.mapper.UserInterfaceInfoMapper;
import com.sheldon.apibackend.model.dto.userInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.sheldon.apibackend.service.UserInterfaceInfoService;
import com.sheldon.apibackend.utils.SqlUtils;
import com.sheldon.apicommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sheldon
 * @description 针对表【user_interface_info(my_api.`interface_info`)】的数据库操作Service实现
 * @createDate 2024-01-10 12:24:06
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public Wrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest) {

        if (userInterfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long id = userInterfaceInfoQueryRequest.getId();
        Long userId = userInterfaceInfoQueryRequest.getUserId();
        Long interfaceInfoId = userInterfaceInfoQueryRequest.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfoQueryRequest.getTotalNum();
        Integer leftNum = userInterfaceInfoQueryRequest.getLeftNum();
        Integer statue = userInterfaceInfoQueryRequest.getStatue();
        String sortField = userInterfaceInfoQueryRequest.getSortField();
        String sortOrder = userInterfaceInfoQueryRequest.getSortOrder();

        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(userId != null, "userId", userId);
        queryWrapper.eq(interfaceInfoId != null, "interfaceInfoId", interfaceInfoId);
        queryWrapper.eq(totalNum != null, "totalNum", totalNum);
        queryWrapper.eq(leftNum != null, "leftNum", leftNum);
        queryWrapper.eq(statue != null, "statue", statue);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);

        return queryWrapper;

    }

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {


        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer statue = userInterfaceInfo.getStatue();


        // 创建时，参数不能为空
        if (add && (userId == null || interfaceInfoId == null || statue == null)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);

        }

        // 有参数则校验
        if (userId != null && userId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户id不合法");
        }
        if (interfaceInfoId != null && interfaceInfoId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口id不合法");
        }
        if (statue != null && statue != 0 && statue != 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "状态不合法");
        }

    }

    @Override
    public Boolean invokeCount(Long userId, Long interfaceInfoId) {

        // 校验参数，抛出异常即可
        if (userId == null || interfaceInfoId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId", userId);
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);

        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");

        return this.update(updateWrapper);
    }

    @Override
    public UserInterfaceInfo selectOne(Long userId, Long interfaceInfoId) {

        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId)
                .eq("interfaceInfoId", interfaceInfoId);
        return userInterfaceInfoMapper.selectOne(queryWrapper);
    }
}




