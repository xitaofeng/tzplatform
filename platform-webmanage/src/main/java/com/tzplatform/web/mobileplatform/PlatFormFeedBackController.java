package com.tzplatform.web.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.mobileplatform.FeedBackType;
import com.tzplatform.entity.mobileplatform.Feedback;
import com.tzplatform.entity.system.PlatFormDict;
import com.tzplatform.service.mobileplatform.PlatFormFeedBackService;
import com.tzplatform.service.system.PlatFormDictService;
import com.tzplatform.service.tokenIpfilter.AccessTokenIp;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 意见反馈
 */
@RestController
@RequestMapping(value = "/platformFeedBack")
public class PlatFormFeedBackController {

    @Resource
    private PlatFormFeedBackService platFormFeedBackService;
    @Resource
    private PlatFormDictService platFormDictService;


    /**
     * 添加修改意见类型
     * @param feedBackType
     * @return
     */

    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/feedBackType", produces = "application/json;charset=utf-8")
    public BaseResultDto feedBackType(FeedBackType feedBackType){
        return platFormFeedBackService.feedBackType(feedBackType);
    }

    /**
     * 意见类型 列表
     * @param feedBackType
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/feedBackTypeList", produces = "application/json;charset=utf-8")
    public BaseResultDto feedBackTypeList(FeedBackType feedBackType){
        return platFormFeedBackService.feedBackTypeList(feedBackType);
    }

    /**
     * 新增 意见
     * @param feedBack
     * @return
     */

    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addFeedBack", produces = "application/json;charset=utf-8")
    public BaseResultDto addFeedBack(Feedback feedBack,@RequestParam(value = "images", required = false) CommonsMultipartFile[] images){
        return platFormFeedBackService.addFeedBack(feedBack,images);
    }

    /**
     * 意见反馈 列表
     * @param feedBack
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/feedBackList", produces = "application/json;charset=utf-8")
    public BaseResultDto feedBackList(Feedback feedBack, HttpServletRequest request){
        return platFormFeedBackService.feedBackList(feedBack,request);
    }

    /**
     *  通过字典表查询类型
     * @param platFormDict
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/typeList", produces = "application/json; charset=utf-8")
    public BaseResultDto dictValueList(PlatFormDict platFormDict) {
        platFormDict.setType("lostFoundType");
        return platFormDictService.dictValueList(platFormDict);
    }

    /**
     * 意见删除
     * @param ids
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delete", produces = "application/json; charset=utf-8")
    public BaseResultDto deleteFeedBack(String ids) {
        return platFormFeedBackService.deleteFeedBack(ids);
    }

    /**
     *  意见状态 修改
     * @param feedBack
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/updateStatus", produces = "application/json; charset=utf-8")
    public BaseResultDto updateStatus(Feedback feedBack) {
        return platFormFeedBackService.updateStatus(feedBack);
    }

    /**
     * 云平台 新增意见
     * @param feedBack
     * @param images
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/add", produces = "application/json;charset=utf-8")
    public BaseResultDto add(Feedback feedBack,@RequestParam(value = "images", required = false) CommonsMultipartFile[] images){
        return platFormFeedBackService.addFeedBack(feedBack,images);
    }

    /**
     * 云平台 意见反馈列表
     * @param feedBack
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST,value = "/list", produces = "application/json;charset=utf-8")
    public BaseResultDto list(Feedback feedBack, HttpServletRequest request){
        return platFormFeedBackService.feedBackList(feedBack,request);
    }

    /**
     * 云平台 意见类型
     * @param platFormDict
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST,value = "/dictValueList", produces = "application/json;charset=utf-8")
    public BaseResultDto typeList(PlatFormDict platFormDict) {
        platFormDict.setType("lostFoundType");
        return platFormDictService.dictValueList(platFormDict);
    }

}
