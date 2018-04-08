package com.tzplatform.dao.webapp;

import com.tzplatform.entity.webapp.PlatFormAppComment;
import com.tzplatform.entity.webapp.PlatFormWebApp;
import com.tzplatform.entity.webapp.PlatFormWepAppFile;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface PlatFormWebAppDao {

    Integer addWebApp(PlatFormWebApp platFormWebApp);

    Integer editWebApp(PlatFormWebApp platFormWebApp);

    Integer delWebApp(PlatFormWebApp platFormWebApp);

    List<PlatFormWebApp> webAppList(PlatFormWebApp platFormWebApp);

    List<PlatFormWebApp> listAppByKind(PlatFormWebApp platFormWebApp);

    Integer webAppListCount(PlatFormWebApp platFormWebApp);

    Integer listAppByKindCount(PlatFormWebApp platFormWebApp);

    Integer resetAppSecret(@Param(value = "id") String id, @Param(value = "webappsecret") String webappsecret);

    Integer updateAppAccount();

    List<PlatFormAppComment> listComment(PlatFormAppComment platFormAppComment);

    Integer addComment(PlatFormAppComment platFormAppComment);

    Integer listCommentCount(PlatFormAppComment platFormAppComment);

    HashMap<String, String> avgComment(PlatFormAppComment platFormAppComment);

    List<PlatFormWebApp> listAppByKindHobby(PlatFormWebApp platFormWebApp);

    Integer listAppByKindHobbyCount(PlatFormWebApp platFormWebApp);

    Integer appUseCollect(@Param(value = "id") String id, @Param(value = "user") String user, @Param(value = "appid") String appid, @Param(value = "type") String type, @Param(value = "usedate") Date usedate, @Param(value = "requestSource") String requestSource, @Param(value = "appsort") String appsort);

    List<PlatFormWebApp> listAppCollect(@Param(value = "user") String user, @Param(value = "type") String type,@Param(value = "appid") String appid,@Param(value = "webappos") String webappos);

    Integer listAppCollectCount(@Param(value = "user") String user, @Param(value = "type") String type,@Param(value = "appid") String appid,@Param(value = "webappos") String webappos);

    Integer deleteAppCollect(@Param(value = "idArray") String[] idArray, @Param(value = "user") String user, @Param(value = "requestSource") String requestSource);

    List<PlatFormWepAppFile> queryFileList(@Param(value = "id") String id);

    List<PlatFormWepAppFile> queryFile(@Param(value = "id") String id);

    void addAppFile(PlatFormWepAppFile platFormWepAppFile);

    PlatFormWebApp webApp(@Param(value = "webappkey") String webappkey);

    void deleteFile(@Param(value = "id") String id, @Param(value = "fileorder") String fileorder);

    List<PlatFormWebApp> listThirdAppCollect(PlatFormWebApp platFormWebApp);

    Integer listThirdAppCollectCount(PlatFormWebApp platFormWebApp);

    void deleteFileList(@Param(value = "deleteIdList") String[] deleteIdList);
}
