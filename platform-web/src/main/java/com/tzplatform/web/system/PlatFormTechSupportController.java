package com.tzplatform.web.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.system.PlatFormTechSupport;
import com.tzplatform.service.system.PlatFormTechSupportService;
import com.tzplatform.service.tokenfilter.WebAccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/platformQuestion")
public class PlatFormTechSupportController {

    @Resource
    private PlatFormTechSupportService platFormTechSupportService;

    /**
     * 查询问题
     *
     * @param platFormTechSupport
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/questionList", produces = "application/json; charset=utf-8")
    public BaseResultDto questionList(PlatFormTechSupport platFormTechSupport, HttpServletRequest request) {
        return platFormTechSupportService.questionList(platFormTechSupport);
    }


    /**
     * 添加问题
     *
     * @param platFormTechSupport
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/addQuestion", produces = "application/json; charset=utf-8")
    public BaseResultDto addQuestion(PlatFormTechSupport platFormTechSupport, HttpServletRequest request) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormTechSupport.setQuestionuser(userid);
        return platFormTechSupportService.addQuestion(platFormTechSupport);
    }

    /**
     * 查询用户问题
     * @param platFormTechSupport
     * @param request
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/questionUserList", produces = "application/json; charset=utf-8")
    public BaseResultDto questionUserList(PlatFormTechSupport platFormTechSupport, HttpServletRequest request) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormTechSupport.setQuestionuser(userid);
        return platFormTechSupportService.questionList(platFormTechSupport);
    }

    /**
     * 删除问题
     * @param id
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delQuestion", produces = "application/json; charset=utf-8")
    public BaseResultDto delQuestion(String id) {
        return platFormTechSupportService.delQuestion(id);
    }

    /**
     * 修改问题
     *
     * @param platFormTechSupport
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/edituserquestion", produces = "application/json; charset=utf-8")
    public BaseResultDto editQuestion(PlatFormTechSupport platFormTechSupport, HttpServletRequest request) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormTechSupport.setQuestionuser(userid);
        return platFormTechSupportService.editQuestion(platFormTechSupport);
    }

}
