package com.tzplatform.web.home;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.home.SystemHomeService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformHome")
public class SystemHomeController {

    @Resource
    private SystemHomeService systemHomeService;


    /**
     * 应用总数
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/appTotal", produces = "application/json;charset=utf-8")
    public BaseResultDto appTotal() {
        return systemHomeService.appTotal();
    }

    /**
     * 用户总数
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/userTotal", produces = "application/json;charset=utf-8")
    public BaseResultDto userTotal() {
        return systemHomeService.userTotal();
    }

    /**
     * 接口总数
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/apiTotal", produces = "application/json;charset=utf-8")
    public BaseResultDto apiTotal() {
        return systemHomeService.apiTotal();
    }

}
