package com.sheldon.apibackend.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sheldon.apibackend.common.ErrorCode;
import com.sheldon.apibackend.exception.BusinessException;
import com.sheldon.apibackend.exception.ThrowUtils;
import com.sheldon.apibackend.mapper.InterfaceInfoMapper;
import com.sheldon.apibackend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.sheldon.apibackend.service.InterfaceInfoService;
import com.sheldon.apicommon.model.entity.InterfaceInfo;
import com.sheldon.apicommon.model.vo.InterfaceInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sheldon
 * @description 针对表【interface_info(my_api.`interface_info`)】的数据库操作Service实现
 * @createDate 2024-01-05 16:40:08
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {

        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String host = interfaceInfo.getHost();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        Integer statue = interfaceInfo.getStatue();
        String method = interfaceInfo.getMethod();
        Long userId = interfaceInfo.getUserId();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, host, url, requestHeader, responseHeader, method), ErrorCode.PARAMS_ERROR);
            ThrowUtils.throwIf(userId == null, ErrorCode.PARAMS_ERROR);
        }

        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 255) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "描述过长");
        }
        if (StringUtils.isNotBlank(host) && host.length() > 255) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "host过长");
        }
        if (StringUtils.isNotBlank(url) && url.length() > 255) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "url过长");
        }
        if (StringUtils.isNotBlank(requestHeader) && requestHeader.length() > 255) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求头过长");
        }
        if (StringUtils.isNotBlank(responseHeader) && responseHeader.length() > 255) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "响应头过长");
        }
        if (statue != null && statue != 0 && statue != 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "状态不合法");
        }
        if (StringUtils.isNotBlank(method) && method.length() > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求方法过长");
        }
        if (userId != null && userId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户id不合法");
        }
    }

    /**
     * 获取查询包装类
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (interfaceInfoQueryRequest == null) {
            return queryWrapper;
        }

        Long id = interfaceInfoQueryRequest.getId();
        String name = interfaceInfoQueryRequest.getName();
        String description = interfaceInfoQueryRequest.getDescription();
        String url = interfaceInfoQueryRequest.getUrl();
        String requestHeader = interfaceInfoQueryRequest.getRequestHeader();
        String responseHeader = interfaceInfoQueryRequest.getResponseHeader();
        Integer statue = interfaceInfoQueryRequest.getStatue();
        String method = interfaceInfoQueryRequest.getMethod();
        Long userId = interfaceInfoQueryRequest.getUserId();

        // 拼接查询条件
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.like(StringUtils.isNotBlank(url), "url", url);
        queryWrapper.like(StringUtils.isNotBlank(requestHeader), "request_header", requestHeader);
        queryWrapper.like(StringUtils.isNotBlank(responseHeader), "response_header", responseHeader);
        queryWrapper.eq(statue != null, "statue", statue);
        queryWrapper.eq(StringUtils.isNotBlank(method), "method", method);
        queryWrapper.eq(userId != null, "userId", userId);

        return queryWrapper;
    }
}




