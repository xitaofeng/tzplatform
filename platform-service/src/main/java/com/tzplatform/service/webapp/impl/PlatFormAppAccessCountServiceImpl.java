package com.tzplatform.service.webapp.impl;

import com.tzplatform.dao.webapp.PlatFormAppAccessCountDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.webapp.PlatFormAppAccessCount;
import com.tzplatform.service.webapp.PlatFormAppAccessCountService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "platFormAppAccessCountService")
public class PlatFormAppAccessCountServiceImpl implements PlatFormAppAccessCountService {

    @Resource
    private PlatFormAppAccessCountDao platFormAppAccessCountDao;


    /**
     * 编辑应用统计表
     *
     * @param platFormAppAccessCount
     * @return
     */
    @Override
    public BaseResultDto updateAccessCount(PlatFormAppAccessCount platFormAppAccessCount, String appid) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer count = 0;
        List<PlatFormAppAccessCount> resultList = platFormAppAccessCountDao.accessList(appid);
        if (resultList.size() > 0) {
            PlatFormAppAccessCount accessResult = resultList.get(0);
            platFormAppAccessCount.setId(accessResult.getId());
            count = platFormAppAccessCountDao.updateAccessCount(platFormAppAccessCount);
        } else {
            platFormAppAccessCount.setId(UidUtils.getId());
            count = platFormAppAccessCountDao.addAccessCount(platFormAppAccessCount);
        }
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
     * 增加收藏人数 -- 加一
     * @param appid
     * @return
     */
    @Override
    public BaseResultDto updateCollectCount(String appid) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer count=platFormAppAccessCountDao.updateCollectCount(appid);
        if(1==count){
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        }else{
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 客户端取消收藏,更新收藏人数 -- 减一
     * @param appid
     */
    @Override
    public void updateCollectCountCut(String appid) {
        platFormAppAccessCountDao.updateCollectCountCut(appid);
    }

    /**
     * 利用缓存更新使用人数表
     * @param appid
     * @param usecount
     */
    @Override
    public Integer updateUseCount(String appid, Long usecount) {
        Integer count=platFormAppAccessCountDao.updateUseCount(appid,usecount);
        return count;
    }
}
