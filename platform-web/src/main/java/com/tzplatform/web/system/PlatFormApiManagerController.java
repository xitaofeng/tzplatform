package com.tzplatform.web.system;

import com.tzplatform.entity.api.PlatFormApi;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.system.PlatFormApiManagerService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformApiManager")
public class PlatFormApiManagerController {
    @Resource
    private PlatFormApiManagerService platFormApiManagerService;

    /**
     * 查询接口类型列表
     *
     * @param apiType
     * @return
     */
    @RequestMapping(value = "/queryListApi", produces = "application/json;charset=utf-8")
    public BaseResultDto queryListApi(PlatFormApi apiType) {
        return platFormApiManagerService.queryListApi(apiType);
    }

    /**
     * api 树状查询
     * @param apiType
     * @return
     */
    @RequestMapping(value = "/apiTree", produces = "application/json;charset=utf-8")
    public BaseResultDto apiTree(PlatFormApi apiType) {
        return platFormApiManagerService.apiTree(apiType);
    }

}
