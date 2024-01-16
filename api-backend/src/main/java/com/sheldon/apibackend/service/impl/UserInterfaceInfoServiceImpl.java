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

/**
 * @author sheldon
 * @description 针对表【user_interface_info(my_api.`interface_info`)】的数据库操作Service实现
 * @createDate 2024-01-10 12:24:06
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

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
        int current = userInterfaceInfoQueryRequest.getCurrent();
        int pageSize = userInterfaceInfoQueryRequest.getPageSize();
        String sortField = userInterfaceInfoQueryRequest.getSortField();
        String sortOrder = userInterfaceInfoQueryRequest.getSortOrder();

        QueryWrapper<UserInterfaceInfo> userInterfaceInfoQueryWrapper = new QueryWrapper<UserInterfaceInfo>()
                .eq(id != null, "id", id)
                .eq(userId != null, "userId", userId)
                .eq(interfaceInfoId != null, "interfaceInfoId", interfaceInfoId)
                .eq(totalNum != null, "totalNum", totalNum)
                .eq(leftNum != null, "leftNum", leftNum)
                .eq(statue != null, "statue", statue)
                .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                        sortField);

        return userInterfaceInfoQueryWrapper;

    }

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {

        // TODO 请在这里校验参数，抛出异常即可
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer leftNum = userInterfaceInfo.getLeftNum();

        // 创建时，参数不能为空
        if (add) {
            if (userId == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户 id 不能为空");
            }
            if (interfaceInfoId == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口 id 不能为空");
            }
        }
        // 有参数则校验
        if (leftNum < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "次数不能小于0");
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
}




