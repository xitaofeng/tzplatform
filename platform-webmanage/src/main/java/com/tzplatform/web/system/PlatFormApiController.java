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
     * 添加接口类型
     * @param apiType
     * @retur
     */
    @RequestMapping(value = "/addApi", produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto addApi(PlatFormApiType apiType){
        return platFormApiService.addApi(apiType);
    }

    /**
     * 修改接口类型
     * @param apiType
     * @return
     */
    @RequestMapping(value="/editApi",produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto editApi(PlatFormApiType apiType){
        return platFormApiService.editApi(apiType);
    }

    /**
     * 删除接口类型
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteApi",produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto deleteApi(String id){
        return platFormApiService.deleteApi(id);
    }

    /**
     * 查询接口类型列表
     * @param apiType
     * @return
     */
    @RequestMapping(value = "/queryListApi",produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto queryListApi(PlatFormApiType apiType){
        return platFormApiService.queryListApi(apiType);
    }
}
