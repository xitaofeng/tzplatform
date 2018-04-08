package com.tzplatform.web.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.system.PlatFormExceptionLog;
import com.tzplatform.entity.system.PlatFormNormalLog;
import com.tzplatform.service.system.PlatFormLogService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformlog")
public class PlatFormLogController {
    @Resource
    private PlatFormLogService platFormLogService;

    /**
     * 查询操作日志
     * @param normalLog
     * @return
     */
    @RequestMapping(value = "/querynormalLog",produces = "application/json;charset=utf-8")
    public BaseResultDto querynormalLog(PlatFormNormalLog normalLog){
        return platFormLogService.querynormalLog(normalLog);
    }

    /**
     * 查询异常日志
     * @param exceptionLog
     * @return
     */
    @RequestMapping(value = "/queryexceptionLog",produces = "application/json;charset=utf-8")
    public BaseResultDto queryexceptionLog(PlatFormExceptionLog exceptionLog){
        return platFormLogService.queryexceptionLog(exceptionLog);
    }
}
