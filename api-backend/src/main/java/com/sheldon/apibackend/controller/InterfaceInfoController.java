package com.sheldon.apibackend.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sheldon.apibackend.annotation.AuthCheck;
import com.sheldon.apibackend.common.BaseResponse;
import com.sheldon.apibackend.common.DeleteRequest;
import com.sheldon.apibackend.common.ErrorCode;
import com.sheldon.apibackend.common.ResultUtils;
import com.sheldon.apibackend.constant.UserConstant;
import com.sheldon.apibackend.exception.BusinessException;
import com.sheldon.apibackend.exception.ThrowUtils;
import com.sheldon.apibackend.model.dto.interfaceInfo.*;
import com.sheldon.apibackend.service.InterfaceInfoService;
import com.sheldon.apibackend.service.UserInterfaceInfoService;
import com.sheldon.apibackend.service.UserService;
import com.sheldon.apiclientsdk.client.ApiClient;
import com.sheldon.apicommon.model.entity.InterfaceInfo;
import com.sheldon.apicommon.model.entity.User;
import com.sheldon.apicommon.model.entity.UserInterfaceInfo;
import com.sheldon.apicommon.model.enums.InterfaceStatusEnum;
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
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private ApiClient apiClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);

        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        ThrowUtils.throwIf(oldInterfaceInfo.getStatue() == 1, ErrorCode.SYSTEM_ERROR);
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param interfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/item")
    public BaseResponse<InterfaceInfo> getInterfaceInfoVOById(Long id, HttpServletRequest request) {

        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                     HttpServletRequest request) {

        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);

        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));

        return ResultUtils.success(interfaceInfoPage);

    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page")
    public BaseResponse<Page<InterfaceInfo>> listMyInterfaceInfoByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                           HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        interfaceInfoQueryRequest.setUserId(loginUser.getId());
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
        return ResultUtils.success(interfaceInfoPage);
    }

    /**
     * 针对单个接口的基本检验
     * @param id 接口id
     * @return 接口信息
     */
    public InterfaceInfo isInterfaceInfoExist(Long id) {
        // 判断参数是否正确
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 判断该接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return interfaceInfo;
    }

    /**
     * 接口上线（仅管理员）
     *
     * @param idRequest
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {

        Long id = idRequest.getId();

        // 判断参数是否正确
        isInterfaceInfoExist(id);

        InterfaceInfo newInterfaceInfo = new InterfaceInfo();
        newInterfaceInfo.setId(id);
        newInterfaceInfo.setStatue(InterfaceStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(newInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 接口下线（仅管理员）
     *
     * @param idRequest
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {

        Long id = idRequest.getId();

        // 判断参数是否正确
        isInterfaceInfoExist(id);

        InterfaceInfo newInterfaceInfo = new InterfaceInfo();
        newInterfaceInfo.setId(id);
        newInterfaceInfo.setStatue(InterfaceStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(newInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 调用接口
     *
     * @param interfaceInfoInvokeRequest
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest, HttpServletRequest request) {

        Long interfaceInfoId = interfaceInfoInvokeRequest.getId();
        String requestParams = interfaceInfoInvokeRequest.getRequestParams();

        InterfaceInfo interfaceInfo = isInterfaceInfoExist(interfaceInfoId);

        if (!interfaceInfo.getStatue().equals(InterfaceStatusEnum.ONLINE.getValue())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口已下线");
        }

        // 校验请求参数是否正确
        if (!CharSequenceUtil.isAllEmpty(interfaceInfo.getRequestParams(), requestParams)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 查询当前的登录用户
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();

        // 查询是否有剩余次数
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.selectOne(loginUser.getId(), interfaceInfoId);
        if (userInterfaceInfo == null) {
            UserInterfaceInfo userInterfaceInfo1 = new UserInterfaceInfo();
            userInterfaceInfo1.setUserId(loginUser.getId());
            userInterfaceInfo1.setInterfaceInfoId(interfaceInfoId);
            userInterfaceInfo1.setTotalNum(0);
            userInterfaceInfo1.setLeftNum(5);
            boolean save = userInterfaceInfoService.save(userInterfaceInfo1);
            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建用户接口信息失败");
            }
        } else {
            // 该用户调用过该接口，需要判断是否有剩余次数
            ThrowUtils.throwIf(userInterfaceInfo.getLeftNum() <= 0, ErrorCode.SYSTEM_ERROR, "您的调用次数已用完");
        }

        // 调用接口
        String chickenSoup = new ApiClient(accessKey, secretKey)
                .doInvoke(null, String.valueOf(interfaceInfoId));
        return ResultUtils.success(chickenSoup);
    }

}
