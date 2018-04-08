package com.tzplatform.service.home.impl;

import com.tzplatform.dao.system.PlatFormApiManagerDao;
import com.tzplatform.dao.user.PlatFormUserDao;
import com.tzplatform.dao.webapp.PlatFormWebAppDao;
import com.tzplatform.entity.api.PlatFormApi;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.user.PlatFormUser;
import com.tzplatform.entity.webapp.PlatFormWebApp;
import com.tzplatform.service.home.SystemHomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "systemHomeService")
public class SystemHomeServiceImpl implements SystemHomeService {

    @Resource
    private PlatFormApiManagerDao platFormApiManagerDao;

    @Resource
    private PlatFormUserDao platFormUserDao;

    @Resource
    private PlatFormWebAppDao platFormWebAppDao;

    /**
     * 应用总数
     *
     * @return
     */
    @Override
    public BaseResultDto appTotal() {
        PlatFormWebApp platFormWebApp = new PlatFormWebApp();
        BaseResultDto baseResultDto = new BaseResultDto();
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(platFormWebAppDao.webAppListCount(platFormWebApp));
        return baseResultDto;
    }

    /**
     * 注册厂商总数
     *
     * @return
     */
    @Override
    public BaseResultDto userTotal() {
        PlatFormUser platFormUser = new PlatFormUser();
        platFormUser.setUsertype(CommonEnum.USER_TYPE.厂商用户.getValue());
        BaseResultDto baseResultDto = new BaseResultDto();
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(platFormUserDao.userListCount(platFormUser));
        return baseResultDto;
    }

    /**
     * 接口注册总数
     *
     * @return
     */
    @Override
    public BaseResultDto apiTotal() {
        PlatFormApi platFormApi = new PlatFormApi();
        BaseResultDto baseResultDto = new BaseResultDto();
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(platFormApiManagerDao.queryListCount(platFormApi));
        return baseResultDto;
    }
}
