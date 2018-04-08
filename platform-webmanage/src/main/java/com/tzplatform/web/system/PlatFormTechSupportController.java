package com.tzplatform.web.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.system.PlatFormTechSupport;
import com.tzplatform.service.system.PlatFormTechSupportService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformQuestion")
public class PlatFormTechSupportController {

    @Resource
    private PlatFormTechSupportService platFormTechSupportService;

    /**
     * 添加问题
     * @param platFormTechSupport
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addQuestion", produces = "application/json; charset=utf-8")
    public BaseResultDto addQuestion(PlatFormTechSupport platFormTechSupport) {
        return platFormTechSupportService.addQuestion(platFormTechSupport);
    }

    /**
     * 修改问题
     * @param platFormTechSupport
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/editQuestion", produces = "application/json; charset=utf-8")
    public BaseResultDto editQuestion(PlatFormTechSupport platFormTechSupport) {
        return platFormTechSupportService.editQuestion(platFormTechSupport);
    }

    /**
     * 删除问题
     * @param id
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delQuestion", produces = "application/json; charset=utf-8")
    public BaseResultDto delQuestion(String id) {
        return platFormTechSupportService.delQuestion(id);
    }

    /**
     * 查询问题
     * @param platFormTechSupport
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/questionList", produces = "application/json; charset=utf-8")
    public BaseResultDto questionList(PlatFormTechSupport platFormTechSupport) {
        return platFormTechSupportService.questionList(platFormTechSupport);
    }
}
