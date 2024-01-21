package com.sheldon.apibackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
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
import com.sheldon.apibackend.service.UserService;
import com.sheldon.apiclientsdk.client.ApiClient;
import com.sheldon.apicommon.model.entity.InterfaceInfo;
import com.sheldon.apicommon.model.entity.User;
import com.sheldon.apicommon.model.enums.InterfaceStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
//        interfaceInfo.setFavourNum(0);
//        interfaceInfo.setThumbNum(0);
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

//        return ResultUtils.success(interfaceInfoPage);
        return ResultUtils.success(interfaceInfoPage);

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
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 判断该接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // TODO 判断该接口是否可以调用
        String interfaceInfoId = String.valueOf(interfaceInfo.getId());
        String url = interfaceInfo.getUrl();
        com.sheldon.apiclientsdk.model.User user = new com.sheldon.apiclientsdk.model.User();
        user.setUsername("sheldon");
        String res = apiClient.postJson(user, url, interfaceInfoId);
        if (StringUtils.isBlank(res)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口调用失败");
        }

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
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 判断该接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

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

        Long id = interfaceInfoInvokeRequest.getId();
        String requestParams = interfaceInfoInvokeRequest.getRequestParams();

        // 判断参数是否正确
        if (id <= 0 || StringUtils.isBlank(requestParams)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 判断该接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (!interfaceInfo.getStatue().equals(InterfaceStatusEnum.ONLINE.getValue())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口已下线");
        }

        // 查询当前的登录用户
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();

        // 调用接口
        Long interfaceInfoId = interfaceInfo.getId();
        String url = interfaceInfo.getUrl();
        ApiClient apiClient = new ApiClient(accessKey, secretKey);
        Gson gson = new Gson();
        com.sheldon.apiclientsdk.model.User user = gson.fromJson(requestParams, com.sheldon.apiclientsdk.model.User.class);
        String usernameByPost = apiClient.postJson(user, url, String.valueOf(interfaceInfoId));
        return ResultUtils.success(usernameByPost);
    }


    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     *//*
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listMyInterfaceInfoVOByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
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
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOPage(interfaceInfoPage, request));
    }

    // endregion

    *//**
     * 分页搜索（从 ES 查询，封装类）
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     *//*
    @PostMapping("/search/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> searchInterfaceInfoVOByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
            HttpServletRequest request) {
        long size = interfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.searchFromEs(interfaceInfoQueryRequest);
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOPage(interfaceInfoPage, request));
    }

    *//**
     * 编辑（用户）
     *
     * @param interfaceInfoEditRequest
     * @param request
     * @return
     *//*
    @PostMapping("/edit")
    public BaseResponse<Boolean> editInterfaceInfo(@RequestBody InterfaceInfoEditRequest interfaceInfoEditRequest, HttpServletRequest request) {
        if (interfaceInfoEditRequest == null || interfaceInfoEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoEditRequest, interfaceInfo);
        List<String> tags = interfaceInfoEditRequest.getTags();
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User loginUser = userService.getLoginUser(request);

        long id = interfaceInfoEditRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldInterfaceInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }*/

}
