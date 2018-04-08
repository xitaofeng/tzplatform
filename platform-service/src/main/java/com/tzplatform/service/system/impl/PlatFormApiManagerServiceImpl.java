package com.tzplatform.service.system.impl;

import com.tzplatform.dao.system.PlatFormApiDao;
import com.tzplatform.dao.system.PlatFormApiManagerDao;
import com.tzplatform.entity.api.PlatFormApi;
import com.tzplatform.entity.api.PlatFormApiType;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.common.TreeDto;
import com.tzplatform.service.system.PlatFormApiManagerService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service(value = "PlatFormApiManagerService")
public class PlatFormApiManagerServiceImpl implements PlatFormApiManagerService {


    @Autowired
    private PlatFormApiManagerDao platFormApiManagerDao;

    @Autowired
    private PlatFormApiDao platFormApiDao;

    /**
     * 增加接口类型
     *
     * @param api
     * @return
     */
    @Override
    @AopLog(menuname = "接口管理", description = "添加接口")
    public BaseResultDto addApi(PlatFormApi api) {
        BaseResultDto baseResultDto = new BaseResultDto();
        api.setId(UidUtils.getId());
        api.setCreatetime(new Date());
        Integer count = platFormApiManagerDao.addApi(api);
        if (1 == count) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 修改接口类型
     *
     * @param api
     * @return
     */
    @Override
    @AopLog(menuname = "接口管理", description = "修改接口")
    public BaseResultDto editApi(PlatFormApi api) {
        BaseResultDto baseResultDto = new BaseResultDto();
        api.setUpdatetime(new Date());
        Integer count = platFormApiManagerDao.editApi(api);
        if (1 == count) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_UPDATE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_UPDATE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 删除接口类型
     *
     * @param id
     * @return
     */
    @Override
    @AopLog(menuname = "接口管理", description = "删除接口")
    public BaseResultDto deleteApi(String id) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer count = platFormApiManagerDao.deleteApi(id);
        if (count == 1) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_DELETE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_DELETE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询接口类型列表
     *
     * @param api
     * @return
     */
    @Override
    public BaseResultDto queryListApi(PlatFormApi api) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormApi> apiList = platFormApiManagerDao.queryListApi(api);
        Integer count = platFormApiManagerDao.queryListCount(api);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setTotal(count);
        baseResultDto.setData(apiList);
        return baseResultDto;
    }

    /**
     * 统计接口数量
     *
     * @return
     */
    @Override
    public BaseResultDto statisticsApi() {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<LinkedHashMap> list = platFormApiManagerDao.statisticsApi();
        baseResultDto.setData(list);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        return baseResultDto;
    }

    /**
     * 接口树状查询
     * @param platFormApi
     * @return
     */
    @Override
    public BaseResultDto apiTree(PlatFormApi platFormApi) {
        BaseResultDto baseResultDto = new BaseResultDto();
        PlatFormApiType apiType = new PlatFormApiType();
        List<TreeDto> apiTypeList = platFormApiDao.queryListApi(apiType).stream().map(resultDto -> {
            TreeDto treeDto = new TreeDto();
            treeDto.setLabel(resultDto.getTypename());
            treeDto.setId(resultDto.getId());

            PlatFormApi queryparam = new PlatFormApi();
            queryparam.setApittypeid(resultDto.getId());
            List<TreeDto> apiList = platFormApiManagerDao.queryListApi(queryparam).stream().map(apiDto -> {
                TreeDto childDto = new TreeDto();
                childDto.setId(apiDto.getId());
                childDto.setLabel(apiDto.getApiname());
                return childDto;
            }).collect(Collectors.toList());

            treeDto.setChildren(apiList);
            return treeDto;
        }).collect(Collectors.toList());

        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(apiTypeList);
        return baseResultDto;
    }
}
