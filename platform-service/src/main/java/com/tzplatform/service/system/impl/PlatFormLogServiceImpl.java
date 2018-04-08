package com.tzplatform.service.system.impl;

import com.tzplatform.dao.system.PlatFormLogDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.system.PlatFormExceptionLog;
import com.tzplatform.entity.system.PlatFormNormalLog;
import com.tzplatform.service.system.PlatFormLogService;
import com.tzplatform.utils.common.UidUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "platFormLogService")
public class PlatFormLogServiceImpl implements PlatFormLogService {

    @Resource
    private PlatFormLogDao platFormLogDao;
    /**
     * 查询异常日志
     * @param exceptionLog
     * @return
     */
    @Override
    public BaseResultDto queryexceptionLog(PlatFormExceptionLog exceptionLog) {
        BaseResultDto baseResultDto=new BaseResultDto();
        List<PlatFormExceptionLog> logList=platFormLogDao.queryexceptionLog(exceptionLog);
        Integer count=platFormLogDao.queryexceptionCount(exceptionLog);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setTotal(count);
        baseResultDto.setData(logList);
        return baseResultDto;
    }

    /**
     * 查询操作日志
     * @param normalLog
     * @return
     */
    @Override
    public BaseResultDto querynormalLog(PlatFormNormalLog normalLog) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormNormalLog> normalLogList=platFormLogDao.querynormalLog(normalLog);
        Integer count=platFormLogDao.querynormalLogCount(normalLog);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(normalLogList);
        baseResultDto.setTotal(count);
        return baseResultDto;
    }

    /**
     * 添加操作日志
     * @param normalLog
     * @return
     */
    public BaseResultDto insertNormalLog(PlatFormNormalLog normalLog){
        BaseResultDto baseResultDto = new BaseResultDto();
        normalLog.setId(UidUtils.getId());
        Integer count =platFormLogDao.inserNormalLog(normalLog);
        if(1==count){
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        }else{
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 添加异常日志
     * @param exceptionLog
     * @return
     */
    public BaseResultDto insertExceptionLog(PlatFormExceptionLog exceptionLog){
        BaseResultDto baseResultDto = new BaseResultDto();
        exceptionLog.setId(UidUtils.getId());
        Integer count=platFormLogDao.insertExceptionLog(exceptionLog);
        if(1==count){
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        }else{
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
        }
        return baseResultDto;
    }

    /**
     * 根据account获取accountid
     * @param account
     * @return
     */
    @Override
    public BaseResultDto queryIdByAccount(String account) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if(StringUtils.isNotBlank(account)){
            PlatFormNormalLog normalLog=platFormLogDao.queryIdByAccount(account);
            baseResultDto.setData(normalLog);
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        }else{
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }
}
