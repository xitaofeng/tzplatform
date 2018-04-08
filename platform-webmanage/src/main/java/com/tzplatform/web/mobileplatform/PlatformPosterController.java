package com.tzplatform.web.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.mobileplatform.Poster;
import com.tzplatform.service.mobileplatform.impl.PlatformPosterServiceImpl;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 海报管理
 */
@RestController
@RequestMapping(value = "/platformposter")
public class PlatformPosterController {

    @Resource
    private PlatformPosterServiceImpl mobilePlatformPosterService;


    /**
     * 新增海报
     * @param poster
     * @param posterImage
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addPoster", produces = "application/json;charset=utf-8")
    public BaseResultDto addPoster(Poster poster,  @RequestParam(value = "posterImage", required = false) CommonsMultipartFile[] posterImage){

        return mobilePlatformPosterService.addPoster(poster,posterImage);
    }
    /**
     * 查询海报列表
     * @param
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/listPoster", produces = "application/json;charset=utf-8")
    public BaseResultDto listPoster(Poster poster, HttpServletRequest request){
        return mobilePlatformPosterService.listPoster(poster,request);
    }

    /**
     * 编辑
     * @param poster
     * @param posterImage
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/updatePoster", produces = "application/json;charset=utf-8")
    public BaseResultDto updatePoster(Poster poster,  @RequestParam(value = "posterImage", required = false) CommonsMultipartFile[] posterImage){
        return mobilePlatformPosterService.updatePoster(poster,posterImage);
    }
    /**
     * 删除
     * @param
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/deletePoster", produces = "application/json;charset=utf-8")
    public BaseResultDto deletePoster(Poster poster){
        return mobilePlatformPosterService.deletePoster(poster);
    }

    /**
     * 海报上架 下架
     * @param poster
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/updateStatus", produces = "application/json;charset=utf-8")
    public BaseResultDto updateStatus(Poster poster){
        return mobilePlatformPosterService.updateStatus(poster);
    }
}
