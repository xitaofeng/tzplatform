package com.tzplatform.web.system;

import com.tzplatform.entity.api.PlatFormNotice;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.system.PlatFormNoticeService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformNotice")
public class PlatFormNoticeController {
    @Resource
    private PlatFormNoticeService platFormNoticeService;

    /**
     * 添加公告
     *
     * @param platFormNotice
     * @return
     */
    @RequestMapping(value = "addNotice", produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto addNotice(@RequestHeader(value = "TZ-Account")String account, PlatFormNotice platFormNotice) {
        platFormNotice.setCreateuser(account);
        return platFormNoticeService.addNotice(platFormNotice);
    }

    /**
     * 修改公告
     * @param platFormNotice
     * @return
     */
    @RequestMapping(value = "updateNotice",produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto updateNotice(PlatFormNotice platFormNotice){
        return platFormNoticeService.updateNotice(platFormNotice);
    }

    /**
     * 删除公告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteNotice",produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto deleteNotice(String id) {
        return platFormNoticeService.deleteNotice(id);
    }

    /**
     * 查询公告
     * @param platFormNotice
     * @return
     */
    @RequestMapping(value = "queryNotice",produces = "application/json;charset=utf-8")
    @AccessToken
    public BaseResultDto queryNotice(PlatFormNotice platFormNotice){
        return platFormNoticeService.queryNotice(platFormNotice);
    }

}
