package com.tzplatform.web.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.mobileplatform.CampusScenery;
import com.tzplatform.entity.mobileplatform.CampusSceneryType;
import com.tzplatform.service.mobileplatform.PlatformCampusSceneryService;
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
 * 校园风光
 */
@RestController
@RequestMapping(value = "/platformCampusScenery")
public class PlatformCampusSceneryController {

    @Resource
    private PlatformCampusSceneryService platformCampusSceneryService;

    @Value("${campusScenery.file.path}")
    private String campusSceneryPath;//上传图片路径
    /**
     * 校园风光 类型新增修改
     * @param campusSceneryType
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/campusSceneryType", produces = "application/json;charset=utf-8")
    public BaseResultDto campusSceneryType(CampusSceneryType campusSceneryType, @RequestParam(value = "images", required = false)CommonsMultipartFile[] images){
        return platformCampusSceneryService.campusSceneryType(campusSceneryType,images);
    }
    /***
     * 风光类型 列表
     * @param campusSceneryType
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/typeList", produces = "application/json;charset=utf-8")
    public BaseResultDto campusSceneryTypeList(CampusSceneryType campusSceneryType){
        return platformCampusSceneryService.campusSceneryTypeList(campusSceneryType);
    }
    /***
     * 风光新增
     * @param campusScenery
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addCampusScenery", produces = "application/json;charset=utf-8")
    public BaseResultDto addCampusScenery(CampusScenery campusScenery, @RequestParam(value = "images", required = false)CommonsMultipartFile[] images){
        return platformCampusSceneryService.addCampusScenery(campusScenery,images);
    }

    /**
     * 图片审核
     * @param campusScenery
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/approve", produces = "application/json;charset=utf-8")
    public BaseResultDto approve(CampusScenery campusScenery){
        return platformCampusSceneryService.approve(campusScenery);
    }

    /**
     * 风光列表
     * @param campusScenery
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/campusSceneryList", produces = "application/json;charset=utf-8")
    public BaseResultDto campusSceneryList(CampusScenery campusScenery,HttpServletRequest request){
        return platformCampusSceneryService.campusSceneryList(campusScenery,request);
    }

    /**
     * 风光列表
     * @param ids
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delete", produces = "application/json;charset=utf-8")
    public BaseResultDto delete(String  ids){
        return platformCampusSceneryService.delete(ids);
    }
}
