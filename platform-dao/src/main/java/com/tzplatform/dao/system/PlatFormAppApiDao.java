package com.tzplatform.dao.system;

import com.tzplatform.entity.webapp.PlatFormWepAppFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlatFormAppApiDao {

    Integer addApiToApp(@Param(value = "id") String id, @Param(value = "apiid") String apiid, @Param(value = "webappid") String webappid);

    Integer delApiToApp(@Param(value = "webappid") String webappid);

    List<String> getApiByApp(@Param(value = "webappid") String webappid);

    void deleteFileList(@Param(value="deleteIdList") String[] deleteIdList);

}
