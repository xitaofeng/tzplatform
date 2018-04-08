package com.tzplatform.web.system;

import com.tzplatform.entity.api.PlatFormNotice;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.system.PlatFormNoticeService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformNotice")
public class PlatFormNoticeController {
    @Resource
    private PlatFormNoticeService platFormNoticeService;


    /**
     * 查询公告
     *
     * @param platFormNotice
     * @return
     */
    @RequestMapping(value = "queryNotice", produces = "application/json;charset=utf-8")
    public BaseResultDto queryNotice(PlatFormNotice platFormNotice) {
        return platFormNoticeService.queryNotice(platFormNotice);
    }

    /**
     * 查询公告详情
     * @param platFormNotice
     * @return
     */
    @RequestMapping(value = "noticeDetail", produces = "application/json;charset=utf-8")
    public BaseResultDto noticeDetail(PlatFormNotice platFormNotice) {
        return platFormNoticeService.noticeDetail(platFormNotice);
    }

}
