package com.tzplatform.service.webapp;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.webapp.PlatFormAppComment;
import com.tzplatform.entity.webapp.PlatFormWebApp;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface PlatFormWebAppService {

    BaseResultDto delWebApp(PlatFormWebApp platFormWebApp);

    BaseResultDto webAppList(PlatFormWebApp platFormWebApp);

    BaseResultDto webDetailInfo(PlatFormWebApp platFormWebApp);

    BaseResultDto listAppByKind(PlatFormWebApp platFormWebApp);

    BaseResultDto getApiByApp(PlatFormWebApp platFormWebApp);

    BaseResultDto listComment(PlatFormAppComment platFormAppComment);

    BaseResultDto addComment(PlatFormAppComment platFormAppComment);

    BaseResultDto listAppByKindHobby(PlatFormWebApp platFormWebApp, String ids);

    BaseResultDto appUseCollect(String user, String appid, String type, String requestSource);

    BaseResultDto listAppCollect(String user, String type, String appid, String webappos);

    BaseResultDto listMobileAppCollect(String user, String type, String myApp, String webappos);

    BaseResultDto listThirdAppCollect(PlatFormWebApp platFormWebApp);

    BaseResultDto deleteAppCollect(String ids, String user, String requestSource);

    void getFileList(String id, HttpServletResponse response);

    BaseResultDto addWebApp(PlatFormWebApp platFormWebApp, CommonsMultipartFile applogosmall, CommonsMultipartFile applogo, CommonsMultipartFile regestinfofile, CommonsMultipartFile apporcodeinfo, CommonsMultipartFile appfileone, CommonsMultipartFile appfiletwo, CommonsMultipartFile appfilethree, CommonsMultipartFile appfilefour, CommonsMultipartFile appfile);

    BaseResultDto editWebApp(PlatFormWebApp platFormWebApp, CommonsMultipartFile applogosmall, CommonsMultipartFile applogo, CommonsMultipartFile regestinfofile, CommonsMultipartFile apporcodeinfo, CommonsMultipartFile appfileone, CommonsMultipartFile appfiletwo, CommonsMultipartFile appfilethree, CommonsMultipartFile appfilefour, String[] deleteIdList, CommonsMultipartFile appfile);

    BaseResultDto verifyMessage(String platFormWebAppm, String messagetype, String webappip);

    BaseResultDto orderAppCollect(String ids, String user, String type, String requestSource);

    BaseResultDto webappStatistics(String sortname, String webappdevuser,String pageNum,String pageSize);

    BaseResultDto webappStatisticsManager(String sortname, String pageNum, String pageSize);

    BaseResultDto webapptypeList();

    BaseResultDto webapptypeListCus(String webappdevuser);

    BaseResultDto resetSecret(PlatFormWebApp platFormWebApp);

    BaseResultDto queryApiTree(String webappid);

    BaseResultDto getSchoolTree(String appid);

    void getAppFileList(String id, HttpServletResponse response);

    void getFileByPath(String path, String filename,HttpServletResponse response);
}
