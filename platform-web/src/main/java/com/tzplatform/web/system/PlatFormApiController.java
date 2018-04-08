package com.tzplatform.web.system;

import com.tzplatform.entity.api.PlatFormApiType;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.system.PlatFormApiService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformApi")
public class PlatFormApiController {
    @Resource
    private PlatFormApiService platFormApiService;

    /**
     * 查询接口类型列表
     * @param apiType
     * @return
     */
    @RequestMapping(value = "/queryListApi",produces = "application/json;charset=utf-8")
    public BaseResultDto queryListApi(PlatFormApiType apiType){
        return platFormApiService.queryListApi(apiType);
    }
}
