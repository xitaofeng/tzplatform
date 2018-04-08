package com.tzplatform.dao.system;

import com.tzplatform.entity.api.PlatFormApi;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface PlatFormApiManagerDao {
    Integer addApi(PlatFormApi api);

    Integer editApi(PlatFormApi api);

    Integer deleteApi(@Param(value = "id") String id);

    Integer queryListCount(PlatFormApi api);

    List<PlatFormApi> queryListApi(PlatFormApi api);

    List<LinkedHashMap> statisticsApi();

    List<PlatFormApi> queryApiManagerTree(@Param(value = "webappid") String webappid, @Param(value = "apittypeid") String apittypeid);
}
