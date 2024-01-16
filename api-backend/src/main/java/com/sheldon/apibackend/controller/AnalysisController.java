package com.sheldon.apibackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sheldon.apibackend.annotation.AuthCheck;
import com.sheldon.apibackend.common.BaseResponse;
import com.sheldon.apibackend.common.ErrorCode;
import com.sheldon.apibackend.common.ResultUtils;
import com.sheldon.apibackend.exception.BusinessException;
import com.sheldon.apibackend.mapper.UserInterfaceInfoMapper;
import com.sheldon.apibackend.model.vo.InterfaceInfoAnalysisVO;
import com.sheldon.apibackend.service.InterfaceInfoService;
import com.sheldon.apicommon.model.entity.InterfaceInfo;
import com.sheldon.apicommon.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName AnalysisController
 * @Author 26483
 * @Date 2024/1/16 11:29
 * @Version 1.0
 * @Description TODO
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/interface/invoke/top")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoAnalysisVO>> listInterfaceInvokeTop() {
        // TODO 细品
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listInterfaceInvokeTop(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<InterfaceInfoAnalysisVO> interfaceInfoAnalusisVoList = list.stream().map(interfaceInfo -> {
            InterfaceInfoAnalysisVO interfaceInfoAnalysisVO = new InterfaceInfoAnalysisVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoAnalysisVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoAnalysisVO.setTotalNum(totalNum);
            return interfaceInfoAnalysisVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoAnalusisVoList);
    }

}
