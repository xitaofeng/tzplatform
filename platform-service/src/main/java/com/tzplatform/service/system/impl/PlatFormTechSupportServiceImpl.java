package com.tzplatform.service.system.impl;

import com.tzplatform.dao.system.PlatFormTechSupportDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.system.PlatFormTechSupport;
import com.tzplatform.service.system.PlatFormTechSupportService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service(value = "platFormTechSupportService")
public class PlatFormTechSupportServiceImpl implements PlatFormTechSupportService {

    @Resource
    private PlatFormTechSupportDao platFormTechSupportDao;

    /**
     * 添加问题
     *
     * @param platFormTechSupport
     * @return
     */
    @Override
    @AopLog(menuname = "技术问答",description = "添加问题")
    public BaseResultDto addQuestion(PlatFormTechSupport platFormTechSupport) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormTechSupport.setId(UidUtils.getId());
        platFormTechSupport.setCreatetime(new Date());
        Integer result = platFormTechSupportDao.addQuestion(platFormTechSupport);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 问题修改
     * @param platFormTechSupport
     * @return
     */
    @Override
    @AopLog(menuname = "技术问答",description = "修改问题")
    public BaseResultDto editQuestion(PlatFormTechSupport platFormTechSupport) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormTechSupport.setCreatetime(new Date());
        Integer result = platFormTechSupportDao.editQuestion(platFormTechSupport);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_UPDATE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_UPDATE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 删除问题
     * @param id
     * @return
     */
    @Override
    @AopLog(menuname = "技术问答",description = "删除问题")
    public BaseResultDto delQuestion(String id) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormTechSupportDao.delQuestion(id);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_DELETE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_DELETE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 问题查询
     * @param platFormTechSupport
     * @return
     */
    @Override
    public BaseResultDto questionList(PlatFormTechSupport platFormTechSupport) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormTechSupport> resultList = platFormTechSupportDao.questionList(platFormTechSupport);
        Integer resultCount = platFormTechSupportDao.questionListCount(platFormTechSupport);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }
}
