package com.tzplatform.service.system.impl;

import com.tzplatform.dao.system.PlatFormNoticeDao;
import com.tzplatform.entity.api.PlatFormNotice;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.service.system.PlatFormNoticeService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service(value = "platFormNoticeService")
public class PlatFormNoticeServiceImpl implements PlatFormNoticeService {
    @Autowired
    private PlatFormNoticeDao platFormNoticeDao;

    /**
     * 添加通知公告
     *
     * @param platFormNotice
     * @return
     */
    @Override
    @AopLog(menuname = "接口管理", description = "添加公告")
    public BaseResultDto addNotice(PlatFormNotice platFormNotice) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormNotice.setId(UidUtils.getId());
        platFormNotice.setCreatetime(new Date());
        platFormNotice.setUpdatetime(new Date());
        Integer count = platFormNoticeDao.addNotice(platFormNotice);
        if (1 == count) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 删除公告
     *
     * @param id
     * @return
     */
    @Override
    @AopLog(menuname = "接口管理", description = "删除公告")
    public BaseResultDto deleteNotice(String id) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer count = platFormNoticeDao.deleteNotice(id);
        if (1 == count) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询公告
     *
     * @param platFormNotice
     * @return
     */
    @Override
    public BaseResultDto queryNotice(PlatFormNotice platFormNotice) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormNotice> noticeList = platFormNoticeDao.queryNotice(platFormNotice);
        Integer count = platFormNoticeDao.queryCount(platFormNotice);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(noticeList);
        baseResultDto.setTotal(count);
        return baseResultDto;
    }

    /**
     * 修改公告
     *
     * @param platFormNotice
     * @return
     */
    @Override
    @AopLog(menuname = "接口管理", description = "修改公告")
    public BaseResultDto updateNotice(PlatFormNotice platFormNotice) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormNotice.setUpdatetime(new Date());
        Integer count = platFormNoticeDao.updateNotice(platFormNotice);
        if (1 == count) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询公告详情
     * @param platFormNotice
     * @return
     */
    @Override
    public BaseResultDto noticeDetail(PlatFormNotice platFormNotice) {
        BaseResultDto baseResultDto = new BaseResultDto();
        PlatFormNotice resultNotice = new PlatFormNotice();
        List<PlatFormNotice> noticeList = platFormNoticeDao.queryNotice(platFormNotice);
        if (noticeList.size() > 0) {
            resultNotice = noticeList.get(0);
        }
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(resultNotice);
        return baseResultDto;
    }
}
