package com.tzplatform.web.webapp;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.PageDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.system.PlatFormDict;
import com.tzplatform.entity.webapp.PlatFormWebApp;
import com.tzplatform.service.system.PlatFormDictService;
import com.tzplatform.service.tokenfilter.ThirdMobileAccessToken;
import com.tzplatform.service.webapp.PlatFormWebAppService;
import com.tzplatform.utils.jwtauth.JwtUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/platfromMobileThirdApp")
public class PlatFormMobileThirdController {

    private Logger logger = Logger.getLogger(PlatFormMobileThirdController.class);

    @Resource
    private PlatFormWebAppService platFormWebAppService;
    @Resource
    private PlatFormDictService platFormDictService;

    /**
     * 应用类型列表-移动端
     *
     * @param platFormDict
     * @return
     */
    @ThirdMobileAccessToken
    @RequestMapping(value = "/webAppTypeList", produces = "application/json;charset=utf-8")
    public BaseResultDto webAppTypeList(PlatFormDict platFormDict, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormDict.setType("webappkind");
        List<PlatFormDict> dictvalueList = (List<PlatFormDict>) platFormDictService.dictValueList(platFormDict).getData();
        if (dictvalueList.size() > 0) {
            List<Map<String, String>> resultList = dictvalueList.stream().map(dict -> {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("id", dict.getName());
                resultMap.put("description", dict.getName());
                resultMap.put("typename", dict.getValue());
                resultMap.put("status", "0");
                return resultMap;
            }).collect(Collectors.toList());
            baseResultDto.setData(resultList);
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询应用列表-移动端
     *
     * @param platFormWebApp
     * @return
     */
    @ThirdMobileAccessToken
    @RequestMapping(value = "/listAppByKind", produces = "application/json;charset=utf-8")
    public BaseResultDto listAppByKind(PlatFormWebApp platFormWebApp, HttpServletRequest request,
                                       String first, String last, String appType, String requestSource, String description,
                                       HttpServletResponse response, String access_token,
                                       @RequestParam(value = "myApp", required = false) String myApp,
                                       @RequestParam(value = "ids", required = false) String ids) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String user = JwtUtils.veriftToken(access_token);
        platFormWebApp.setPageNum(PageDto.getPageNum(Integer.parseInt(first), Integer.parseInt(last)));

        logger.debug("first-----------listAppByKind---------------"+first);
        logger.debug("last------------listAppByKind-------------"+last);
        logger.debug("pagenum------------listAppByKind-------------"+platFormWebApp.getPageNum());

        platFormWebApp.setPageSize(100);
        String sysOs = "";
        if ("2".equals(requestSource)) {
            sysOs = "Android";
        } else if ("3".equals(requestSource)) {
            sysOs = "IOS";
        }
        platFormWebApp.setWebappos(sysOs);
        platFormWebApp.setWebapptype("mobile");
        platFormWebApp.setWebappkind(appType);
        platFormWebApp.setWebappname(description);
        platFormWebApp.setUser(user);
        return platFormWebAppService.listThirdAppCollect(platFormWebApp);
    }

    /**
     * 应用收藏--移动端
     *
     * @param
     * @return
     */
    @ThirdMobileAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/appUseCollect", produces = "application/json;charset=utf-8")
    public BaseResultDto appUseCollect(String appId, String access_token, String type, String requestSource, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String user = JwtUtils.veriftToken(access_token);

        String sysOs = "";
        if ("2".equals(requestSource)) {
            sysOs = "Android";
        } else if ("3".equals(requestSource)) {
            sysOs = "IOS";
        }
        type = "collect";
        return platFormWebAppService.appUseCollect(user, appId, type, sysOs);
    }

    /**
     * 取消收藏应用--移动端
     *
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/deleteAppCollect", produces = "application/json;charset=utf-8")
    public BaseResultDto deleteAppCollect(String ids, String access_token, String requestSource, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String user = JwtUtils.veriftToken(access_token);
        String sysOs = "";
        if ("2".equals(requestSource)) {
            sysOs = "Android";
        } else if ("3".equals(requestSource)) {
            sysOs = "IOS";
        }
        return platFormWebAppService.deleteAppCollect(ids, user, sysOs);
    }
}
