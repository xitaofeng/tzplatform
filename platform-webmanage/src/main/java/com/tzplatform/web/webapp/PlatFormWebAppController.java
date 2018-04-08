package com.tzplatform.web.webapp;

import com.github.pagehelper.util.StringUtil;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.system.PlatFormDict;
import com.tzplatform.entity.webapp.PlatFormAppComment;
import com.tzplatform.entity.webapp.PlatFormWebApp;
import com.tzplatform.service.common.RedisClusterHelper;
import com.tzplatform.service.system.PlatFormDictService;
import com.tzplatform.service.tokenIpfilter.AccessTokenIp;
import com.tzplatform.service.tokenfilter.AccessToken;
import com.tzplatform.service.webapp.PlatFormWebAppService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformApp")
public class PlatFormWebAppController {

    @Resource
    private PlatFormWebAppService platFormWebAppService;

    @Resource
    private PlatFormDictService platFormDictService;

    @Resource
    private RedisClusterHelper redisClusterHelper;

    /**
     * 添加应用
     *
     * @param platFormWebApp
     * @param applogosmall
     * @param applogo
     * @param regestinfofile
     * @param apporcodeinfo
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/addWebApp", produces = "application/json;charset=utf-8")
    public BaseResultDto addWebApp(PlatFormWebApp platFormWebApp,
                                   @RequestParam(value = "applogosmall", required = false) CommonsMultipartFile applogosmall,
                                   @RequestParam(value = "applogo", required = false) CommonsMultipartFile applogo,
                                   @RequestParam(value = "regestinfofile", required = false) CommonsMultipartFile regestinfofile,
                                   @RequestParam(value = "apporcodeinfo", required = false) CommonsMultipartFile apporcodeinfo,
                                   @RequestParam(value = "appfileone", required = false) CommonsMultipartFile appfileone,
                                   @RequestParam(value = "appfiletwo", required = false) CommonsMultipartFile appfiletwo,
                                   @RequestParam(value = "appfilethree", required = false) CommonsMultipartFile appfilethree,
                                   @RequestParam(value = "appfilefour", required = false) CommonsMultipartFile appfilefour,
                                   @RequestParam(value = "appfile",required = false)CommonsMultipartFile appfile) {

        return platFormWebAppService.addWebApp(platFormWebApp, applogosmall, applogo, regestinfofile, apporcodeinfo, appfileone, appfiletwo, appfilethree, appfilefour,appfile);
    }

    /**
     * 重新获取应用key
     *
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/resetSecret", produces = "application/json;charset=utf-8")
    public BaseResultDto resetSecret(PlatFormWebApp platFormWebApp) {
        return platFormWebAppService.resetSecret(platFormWebApp);
    }

    /**
     * 应用详情--显示应用接口
     * @param webappid
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/queryApiTree",produces = "application/json;charset=utf-8")
    public BaseResultDto queryApiTree(String webappid){
        return  platFormWebAppService.queryApiTree(webappid);
    }

    /**
     * 应用详情--显示适用学校
     * @param appid
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/getSchoolTree",produces = "application/json;charset=utf-8")
    public BaseResultDto getSchoolTree(String appid){
        return platFormWebAppService.getSchoolTree(appid);
    }

    /**
     * 修改应用
     *
     * @param platFormWebApp
     * @param applogosmall
     * @param applogo
     * @param regestinfofile
     * @param apporcodeinfo
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/editWebApp", produces = "application/json;charset=utf-8")
    public BaseResultDto editWebApp(PlatFormWebApp platFormWebApp,
                                    @RequestParam(value = "applogosmall", required = false) CommonsMultipartFile applogosmall,
                                    @RequestParam(value = "applogo", required = false) CommonsMultipartFile applogo,
                                    @RequestParam(value = "regestinfofile", required = false) CommonsMultipartFile regestinfofile,
                                    @RequestParam(value = "apporcodeinfo", required = false) CommonsMultipartFile apporcodeinfo,
                                    @RequestParam(value = "appfileone", required = false) CommonsMultipartFile appfileone,
                                    @RequestParam(value = "appfiletwo", required = false) CommonsMultipartFile appfiletwo,
                                    @RequestParam(value = "appfilethree", required = false) CommonsMultipartFile appfilethree,
                                    @RequestParam(value = "appfilefour", required = false) CommonsMultipartFile appfilefour,
                                    @RequestParam(value = "appfile",required = false)CommonsMultipartFile appfile,
                                    @RequestParam(value = "deleteIdList", required = false) String[] deleteIdList) {

        return platFormWebAppService.editWebApp(platFormWebApp, applogosmall, applogo, regestinfofile, apporcodeinfo, appfileone, appfiletwo, appfilethree, appfilefour, deleteIdList,appfile);
    }


    /**
     * 删除应用
     * @param platFormWebApp
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/delWebApp", produces = "application/json;charset=utf-8")
    public BaseResultDto delWebApp(PlatFormWebApp platFormWebApp) {
        return platFormWebAppService.delWebApp(platFormWebApp);
    }

    /**
     * 查询应用列表
     *
     * @param platFormWebApp
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/listApp", produces = "application/json;charset=utf-8")
    public BaseResultDto listApp(PlatFormWebApp platFormWebApp) {
        return platFormWebAppService.webAppList(platFormWebApp);
    }

    /**
     * 应用详情
     *
     * @param platFormWebApp
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/webDetailInfo", produces = "application/json;charset=utf-8")
    public BaseResultDto webDetailInfo(PlatFormWebApp platFormWebApp) {
        return platFormWebAppService.webDetailInfo(platFormWebApp);
    }

    /**
     * 根据应用获取对应的接口
     *
     * @param platFormWebApp
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/getApiByApp", produces = "application/json;charset=utf-8")
    public BaseResultDto getApiByApp(PlatFormWebApp platFormWebApp) {
        return platFormWebAppService.getApiByApp(platFormWebApp);
    }

    /**
     * 应用类型列表
     *
     * @param platFormDict
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/webAppTypeList", produces = "application/json;charset=utf-8")
    public BaseResultDto webAppTypeList(PlatFormDict platFormDict) {
        //设置type为应用
        platFormDict.setType("webappkind");
        return platFormDictService.dictValueList(platFormDict);
    }

    /**
     * 教育云平台查询应用列表
     *
     * @param platFormWebApp
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/listAppByKind", produces = "application/json;charset=utf-8")
    public BaseResultDto listAppByKind(PlatFormWebApp platFormWebApp) {
        return platFormWebAppService.listAppByKind(platFormWebApp);
    }

    /**
     * 教育云平台推荐应用
     *
     * @param platFormWebApp
     * @return
     */
    /*@AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/listAppByKindHobby", produces = "application/json;charset=utf-8")
    public BaseResultDto listAppByKindHobby(PlatFormWebApp platFormWebApp,@RequestParam(value = "ids")String ids) {
        return platFormWebAppService.listAppByKindHobby(platFormWebApp, ids);
    }*/

    /**
     * 教育云平台查询应用评论
     *
     * @param platFormAppComment
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/listComment", produces = "application/json;charset=utf-8")
    public BaseResultDto listComment(PlatFormAppComment platFormAppComment) {
        return platFormWebAppService.listComment(platFormAppComment);
    }

    /**
     * 教育云平台增加评论
     *
     * @param platFormAppComment
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/addComment", produces = "application/json;charset=utf-8")
    public BaseResultDto addComment(PlatFormAppComment platFormAppComment) {
        return platFormWebAppService.addComment(platFormAppComment);
    }

    /**
     * 教育云平台应用收藏
     *
     * @param
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/appUseCollect", produces = "application/json;charset=utf-8")
    public BaseResultDto appUseCollect(String user, String appid, String type, String requestSource) {
        return platFormWebAppService.appUseCollect(user, appid, type, requestSource);
    }

    /**
     * 获取教育云平台收藏应用
     *
     * @param user
     * @param type
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/listAppCollect", produces = "application/json;charset=utf-8")
    public BaseResultDto listAppCollect(String user, String type, @RequestParam(value = "appid", required = false) String appid, String requestSource) {
        return platFormWebAppService.listAppCollect(user, type, appid, requestSource);
    }

    /**
     * 教育云平台取消收藏应用
     *
     * @param ids
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/deleteAppCollect", produces = "application/json;charset=utf-8")
    public BaseResultDto deleteAppCollect(String ids, String user, String requestSource) {
        return platFormWebAppService.deleteAppCollect(ids, user, requestSource);
    }

    /**
     * 增加使用人数到缓存中去
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/addRedisUseCount", produces = "application/json;charset=utf-8")
    public void addRedisUseCount(String appid) {
        //往redis添加set
        if (StringUtil.isNotEmpty(appid)) {
            //往redis添加set,重复appid则添加不进去
            redisClusterHelper.setSet(CommonEnum.APP_USECOUNT.应用名称.getValue(), appid);
            //以该id作为key值计数
            redisClusterHelper.setCount(appid);
        }
        /**
         * test
         */
        /*Set<String> appidSet = redisClusterHelper.getSet(CommonEnum.APP_USECOUNT.应用名称.getValue());
        for (String str:appidSet) {
            System.out.println(str+"==========="+redisClusterHelper.getCount(str));
            redisClusterHelper.setZero(str);
            System.out.println(str+"==========="+redisClusterHelper.getCount(str));
        }*/
    }

    /**
     * 消息平台验证
     *
     * @param webappkey
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/verifyMessage", produces = "application/json;charset=utf-8")
    public BaseResultDto verifyMessage(String webappkey, String messagetype, String webappip) {
        return platFormWebAppService.verifyMessage(webappkey, messagetype, webappip);

    }

    /**
     * 应用统计--后台管理
     *
     * @param sortname
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/webappStatisticsManager", produces = "application/json;cahrset=utf-8")
    public BaseResultDto webappStatisticsManager(String sortname,String pageNum,String pageSize) {
        return platFormWebAppService.webappStatisticsManager(sortname,pageNum,pageSize);
    }

    /**
     * 应用类型统计--后台管理
     *
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/webapptypeList", produces = "application/json;cahrset=utf-8")
    public BaseResultDto webapptypeList() {
        return platFormWebAppService.webapptypeList();
    }
}
