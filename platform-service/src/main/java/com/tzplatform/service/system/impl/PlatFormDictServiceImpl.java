package com.tzplatform.service.system.impl;

import com.tzplatform.dao.system.PlatFormDictDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.system.PlatFormDict;
import com.tzplatform.entity.webapp.PlatFormAppAccessCount;
import com.tzplatform.service.system.PlatFormDictService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "platFormDictService")
public class PlatFormDictServiceImpl implements PlatFormDictService {

    @Resource
    private PlatFormDictDao platFormDictDao;

    /**
     * 添加字典
     *
     * @param platFormDict
     * @return
     */
    @Override
    @AopLog(menuname = "字典管理",description = "添加字典")
    public BaseResultDto addDict(PlatFormDict platFormDict) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormDict.setId(UidUtils.getId());
        Integer checked = this.checkedDictCode(platFormDict);
        if(0 == checked){
            Integer result = platFormDictDao.addDict(platFormDict);
            if (1 == result) {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
            }
        }else{
            baseResultDto.setCode(ResultMessage.CHECKED_CODE);
            baseResultDto.setMsg(ResultMessage.CHECKED_CODE_MESSAGE);
        }

        return baseResultDto;
    }

    /**
     * 修改字典
     *
     * @param platFormDict
     * @return
     */
    @Override
    @AopLog(menuname = "字典管理",description = "修改字典")
    public BaseResultDto editDict(PlatFormDict platFormDict) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer checked = this.checkedDictCode(platFormDict);
        if(0 == checked){
            Integer result = platFormDictDao.editDict(platFormDict);
            if (1 == result) {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
            }
        }else{
            baseResultDto.setCode(ResultMessage.CHECKED_CODE);
            baseResultDto.setMsg(ResultMessage.CHECKED_CODE_MESSAGE);
        }
        return baseResultDto;
    }


    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    @Override
    @AopLog(menuname = "字典管理",description = "删除字典")
    public BaseResultDto delDict(String id) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormDictDao.delDict(id);
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
     * 查询字典总数
     *
     * @param platFormDict
     * @return
     */
    @Override
    public Integer dictListCount(PlatFormDict platFormDict) {
        return platFormDictDao.dictListCount(platFormDict);
    }

    /**
     * 查询字典类型列表
     *
     * @param platFormDict
     * @return
     */
    @Override
    public BaseResultDto dictList(PlatFormDict platFormDict) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormDict> resultList = platFormDictDao.dictList(platFormDict);
        Integer resultCount = platFormDictDao.dictListCount(platFormDict);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 查询字典列表
     * @param platFormDict
     * @return
     */
    @Override
    public BaseResultDto dictValueList(PlatFormDict platFormDict) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormDict> resultList = platFormDictDao.dictValueList(platFormDict);
        Integer resultCount = platFormDictDao.dictValueListCount(platFormDict);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 校验字典代码是否重复
     * @return
     */
    private int checkedDictCode(PlatFormDict platFormDict){
       int result = platFormDictDao.checkedDictCode(platFormDict);
        return result;

    }

}
