package com.tzplatform.web.webapp;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.webapp.PlatFormWebApp;
import com.tzplatform.service.tokenfilter.WebAccessToken;
import com.tzplatform.service.webapp.PlatFormWebAppService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/platformApp")
public class PlatFormWebAppController {

    @Resource
    private PlatFormWebAppService platFormWebAppService;

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
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/addWebApp", produces = "application/json;charset=utf-8")
    public BaseResultDto addWebApp(PlatFormWebApp platFormWebApp, HttpServletRequest request,
                                   @RequestParam(value = "applogosmall", required = false) CommonsMultipartFile applogosmall,
                                   @RequestParam(value = "applogo", required = false) CommonsMultipartFile applogo,
                                   @RequestParam(value = "regestinfofile", required = false) CommonsMultipartFile regestinfofile,
                                   @RequestParam(value = "apporcodeinfo", required = false) CommonsMultipartFile apporcodeinfo,
                                   @RequestParam(value = "appfileone", required = false) CommonsMultipartFile appfileone,
                                   @RequestParam(value = "appfiletwo", required = false) CommonsMultipartFile appfiletwo,
                                   @RequestParam(value = "appfilethree", required = false) CommonsMultipartFile appfilethree,
                                   @RequestParam(value = "appfile", required = false) CommonsMultipartFile appfile,
                                   @RequestParam(value = "appfilefour", required = false) CommonsMultipartFile appfilefour) {

        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormWebApp.setWebappdevuser(userid);
        return platFormWebAppService.addWebApp(platFormWebApp, applogosmall, applogo, regestinfofile, apporcodeinfo, appfileone, appfiletwo, appfilethree, appfilefour, appfile);
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
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/editWebApp", produces = "application/json;charset=utf-8")
    public BaseResultDto editWebApp(PlatFormWebApp platFormWebApp, HttpServletRequest request,
                                    @RequestParam(value = "applogosmall", required = false) CommonsMultipartFile applogosmall,
                                    @RequestParam(value = "applogo", required = false) CommonsMultipartFile applogo,
                                    @RequestParam(value = "regestinfofile", required = false) CommonsMultipartFile regestinfofile,
                                    @RequestParam(value = "apporcodeinfo", required = false) CommonsMultipartFile apporcodeinfo,
                                    @RequestParam(value = "appfileone", required = false) CommonsMultipartFile appfileone,
                                    @RequestParam(value = "appfiletwo", required = false) CommonsMultipartFile appfiletwo,
                                    @RequestParam(value = "appfilethree", required = false) CommonsMultipartFile appfilethree,
                                    @RequestParam(value = "appfilefour", required = false) CommonsMultipartFile appfilefour,
                                    @RequestParam(value = "appfile", required = false) CommonsMultipartFile appfile,
                                    @RequestParam(value = "deleteIdList", required = false) String[] deleteIdList) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormWebApp.setWebappdevuser(userid);
        return platFormWebAppService.editWebApp(platFormWebApp, applogosmall, applogo, regestinfofile, apporcodeinfo, appfileone, appfiletwo, appfilethree, appfilefour, deleteIdList, appfile);
    }

    /**
     * 查询应用列表
     *
     * @param platFormWebApp
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/listApp", produces = "application/json;charset=utf-8")
    public BaseResultDto listApp(PlatFormWebApp platFormWebApp, HttpServletRequest request) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormWebApp.setWebappdevuser(userid);
        return platFormWebAppService.webAppList(platFormWebApp);
    }

    /**
     * 应用详情
     *
     * @param platFormWebApp
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/webDetailInfo", produces = "application/json;charset=utf-8")
    public BaseResultDto webDetailInfo(PlatFormWebApp platFormWebApp, HttpServletRequest request) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormWebApp.setWebappdevuser(userid);
        return platFormWebAppService.webDetailInfo(platFormWebApp);
    }

    /*@WebAccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delWebApp",produces = "application/json;charset=utf-8")
    public BaseResultDto delWebApp(PlatFormWebApp platFormWebApp){
        return platFormWebAppService.delWebApp(platFormWebApp);
    }*/

    /**
     * 根据应用获取对应的接口
     *
     * @param platFormWebApp
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/getApiByApp", produces = "application/json;charset=utf-8")
    public BaseResultDto getApiByApp(PlatFormWebApp platFormWebApp, HttpServletRequest request) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormWebApp.setWebappdevuser(userid);
        return platFormWebAppService.getApiByApp(platFormWebApp);
    }

    /**
     * 应用统计--客户端
     *
     * @param sortname
     * @param request
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/webappStatistics", produces = "application/json;cahrset=utf-8")
    public BaseResultDto webappStatistics(String sortname, HttpServletRequest request, String pageNum, String pageSize) {
        String webappdevuser = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        return platFormWebAppService.webappStatistics(sortname, webappdevuser, pageNum, pageSize);
    }

    /**
     * 应用统计--客户端
     *
     * @param request
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/webapptypeListCus", produces = "application/json;cahrset=utf-8")
    public BaseResultDto webapptypeListCus(HttpServletRequest request) {
        String webappdevuser = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        return platFormWebAppService.webapptypeListCus(webappdevuser);
    }

    /**
     * 查询精品应用列表
     *
     * @param platFormWebApp
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/listNewApp", produces = "application/json;charset=utf-8")
    public BaseResultDto listNewApp(PlatFormWebApp platFormWebApp, HttpServletRequest request) {
        return platFormWebAppService.webAppList(platFormWebApp);
    }

    /**
     * 重新获取应用key
     *
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/resetSecret", produces = "application/json;charset=utf-8")
    public BaseResultDto resetSecret(PlatFormWebApp platFormWebApp) {
        return platFormWebAppService.resetSecret(platFormWebApp);
    }

    /**
     * 应用详情--显示应用接口
     *
     * @param webappid
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/queryApiTree", produces = "application/json;charset=utf-8")
    public BaseResultDto queryApiTree(String webappid) {
        return platFormWebAppService.queryApiTree(webappid);
    }

    /**
     * 应用详情--显示适用学校
     *
     * @param appid
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/getSchoolTree", produces = "application/json;charset=utf-8")
    public BaseResultDto getSchoolTree(String appid) {
        return platFormWebAppService.getSchoolTree(appid);
    }

}
