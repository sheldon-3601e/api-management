package com.sheldon.apibackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sheldon.apibackend.annotation.AuthCheck;
import com.sheldon.apibackend.common.BaseResponse;
import com.sheldon.apibackend.common.DeleteRequest;
import com.sheldon.apibackend.common.ErrorCode;
import com.sheldon.apibackend.common.ResultUtils;
import com.sheldon.apibackend.constant.UserConstant;
import com.sheldon.apibackend.exception.BusinessException;
import com.sheldon.apibackend.exception.ThrowUtils;
import com.sheldon.apibackend.model.dto.userInterfaceInfo.UserInterfaceInfoAddRequest;
import com.sheldon.apibackend.model.dto.userInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.sheldon.apibackend.model.dto.userInterfaceInfo.UserInterfaceInfoUpdateRequest;
import com.sheldon.apibackend.model.dto.userInterfaceInfo.UserInterfaceInfoDetailRequest;
import com.sheldon.apibackend.service.UserInterfaceInfoService;
import com.sheldon.apibackend.service.UserService;
import com.sheldon.apiclientsdk.client.ApiClient;
import com.sheldon.apicommon.model.entity.User;
import com.sheldon.apicommon.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 接口信息表
 *
 * @author shedlon
 */
@RestController
@RequestMapping("/userInterfaceInfo")
@Slf4j
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private ApiClient apiClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param userInterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest, HttpServletRequest request) {
        if (userInterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);

        userInterfaceInfo.setUserId(loginUser.getId());
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newUserInterfaceInfoId = userInterfaceInfo.getId();
        return ResultUtils.success(newUserInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUserInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldUserInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userInterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param userInterfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest) {
        if (userInterfaceInfoUpdateRequest == null || userInterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoUpdateRequest, userInterfaceInfo);
        // 参数校验
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, false);
        long id = userInterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldUserInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/item")
    public BaseResponse<UserInterfaceInfo> getUserInterfaceInfoVOById(Long id, HttpServletRequest request) {

        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return ResultUtils.success(userInterfaceInfo);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param userInterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<UserInterfaceInfo>> listUserInterfaceInfoByPage(@RequestBody UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest,
                                                                             HttpServletRequest request) {

        long current = userInterfaceInfoQueryRequest.getCurrent();
        long size = userInterfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);

        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size),
                userInterfaceInfoService.getQueryWrapper(userInterfaceInfoQueryRequest));

        return ResultUtils.success(userInterfaceInfoPage);
    }

    /**
     * 获取总调用次数和剩余调用次数
     *
     * @param userInterfaceInfoDetailRequest
     * @param request
     * @return
     */
    @PostMapping("/get/numbers")
    public BaseResponse<UserInterfaceInfo> queryTotalAndRemain(@RequestBody UserInterfaceInfoDetailRequest userInterfaceInfoDetailRequest,
                                                                             HttpServletRequest request) {

        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        Long interfaceInfoId = userInterfaceInfoDetailRequest.getInterfaceInfoId();

        // 校验参数
        ThrowUtils.throwIf(interfaceInfoId == null || interfaceInfoId < 0, ErrorCode.PARAMS_ERROR);

        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.selectOne(userId, interfaceInfoId);

        return ResultUtils.success(userInterfaceInfo);
    }

}
