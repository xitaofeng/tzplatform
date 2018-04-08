package com.tzplatform.web.system;

import com.tzplatform.entity.api.PlatFormApi;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
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
     * 添加接口
     *
     * @param apiType
     * @retur
     */
    @RequestMapping(value = "/addApi", produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto addApi(@RequestHeader(value = "TZ-Account") String account, PlatFormApi apiType) {
        apiType.setHandleuser(account);
        return platFormApiManagerService.addApi(apiType);
    }

    /**
     * 修改接口
     *
     * @param apiType
     * @return
     */
    @RequestMapping(value = "/editApi", produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto editApi(@RequestHeader(value = "TZ-Account") String account, PlatFormApi apiType) {
        apiType.setHandleuser(account);
        return platFormApiManagerService.editApi(apiType);
    }

    /**
     * 删除接口
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteApi", produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto deleteApi(String id) {
        return platFormApiManagerService.deleteApi(id);
    }

    /**
     * 查询接口列表
     *
     * @param apiType
     * @return
     */
    @RequestMapping(value = "/queryListApi", produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto queryListApi(PlatFormApi apiType) {
        return platFormApiManagerService.queryListApi(apiType);
    }

    /**
     * 统计接口数量
     *
     * @return
     */
    @RequestMapping(value = "/statisticApi", produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto statisticsApi() {
        return platFormApiManagerService.statisticsApi();
    }

    /**
     * api 树状查询
     * @param apiType
     * @return
     */
    @RequestMapping(value = "/apiTree", produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto apiTree(PlatFormApi apiType) {
        return platFormApiManagerService.apiTree(apiType);
    }
}
