package com.tzplatform.web.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.mobileplatform.LostFound;
import com.tzplatform.entity.mobileplatform.LostFoundReview;
import com.tzplatform.service.mobileplatform.PlatformLostFoundService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 失物招领 寻物启事
 */
@RestController
@RequestMapping(value = "/platformLostFound")
public class PlatformLostFoundController {

    @Resource
    private PlatformLostFoundService platformLostFoundService;

    @Value("${attachment.file.path}")
    private String webPath;

    /**
     * 失物招领
     * 寻物启事 新增
     * @param lostFound
     * @param images
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addLostFound", produces = "application/json;charset=utf-8")
    public BaseResultDto addLostFound(LostFound lostFound, @RequestParam(value = "images", required = false)CommonsMultipartFile[] images){
        return platformLostFoundService.addLostFound(lostFound,images);
    }
    /**
     * 失物招领 寻物启事 列表
     * @param lostFound
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/lostFoundList", produces = "application/json;charset=utf-8")
    public BaseResultDto lostFoundList(LostFound lostFound, HttpServletRequest request){
        return platformLostFoundService.lostFoundList(lostFound,request);
    }

    /**
     * 详情
     * @param lostFound
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/lostFoundDetail", produces = "application/json;charset=utf-8")
    public BaseResultDto noticeDetail(LostFound lostFound,HttpServletRequest request){
        return platformLostFoundService.lostFoundDetail(lostFound,request);
    }

    /**
     * 评论
     * @param lostFoundReview
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/review", produces = "application/json;charset=utf-8")
    public BaseResultDto noticeDetail(LostFoundReview lostFoundReview){
        return platformLostFoundService.review(lostFoundReview);
    }
    /**
     * 评论列表
     * @param lostFoundReview
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/reviewList", produces = "application/json;charset=utf-8")
    public BaseResultDto reviewList(LostFoundReview lostFoundReview){
        return platformLostFoundService.reviewList(lostFoundReview);
    }

    /**
     * 失物招领/寻物启事删除
     * @param ids
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delete", produces = "application/json;charset=utf-8")
    public BaseResultDto reviewList(String ids){
        return platformLostFoundService.deleteLostFound(ids);
    }

    /**
     * 失物招领/寻物启事 评论删除
     * @param ids
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/deleteReview", produces = "application/json;charset=utf-8")
    public BaseResultDto deleteReview(String ids){
        return platformLostFoundService.deleteReview(ids);
    }

}
