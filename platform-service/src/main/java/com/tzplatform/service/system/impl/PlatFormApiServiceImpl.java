package com.tzplatform.service.system.impl;

import com.tzplatform.dao.system.PlatFormApiDao;
import com.tzplatform.entity.api.PlatFormApiType;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.service.system.PlatFormApiService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service(value = "PlatFormApiService")
public class PlatFormApiServiceImpl implements PlatFormApiService{
    @Autowired
    private PlatFormApiDao platFormApiDao;

    /**
     * 增加接口类型
     * @param apiType
     * @return
     */
    @Override
    @AopLog(menuname = "接口类型管理",description = "添加接口类型")
    public BaseResultDto addApi(PlatFormApiType apiType) {
        BaseResultDto baseResultDto=new BaseResultDto();
        apiType.setId(UidUtils.getId());
        Integer count=platFormApiDao.addApi(apiType);
        if(1==count){
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        }else{
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 修改接口类型
     * @param apiType
     * @return
     */
    @Override
    @AopLog(menuname = "接口类型管理",description = "修改接口类型")
    public BaseResultDto editApi(PlatFormApiType apiType) {
        BaseResultDto baseResultDto=new BaseResultDto();
        Integer count = platFormApiDao.editApi(apiType);
        if(1==count){
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_UPDATE_MESSAGE);
        }else{
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_UPDATE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 删除接口类型
     * @param id
     * @return
     */
    @Override
    @AopLog(menuname = "接口类型管理",description = "删除接口类型")
    public BaseResultDto deleteApi(String id) {
        BaseResultDto baseResultDto=new BaseResultDto();
        Integer count = platFormApiDao.deleteApi(id);
        if(count==1){
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_DELETE_MESSAGE);
        }else{
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_DELETE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询接口类型列表
     * @param apiType
     * @return
     */
    @Override
    public BaseResultDto queryListApi(PlatFormApiType apiType) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormApiType> apiTypeList = platFormApiDao.queryListApi(apiType);
        Integer count = platFormApiDao.queryListCount(apiType);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setTotal(count);
        baseResultDto.setData(apiTypeList);
        return baseResultDto;
    }
}
