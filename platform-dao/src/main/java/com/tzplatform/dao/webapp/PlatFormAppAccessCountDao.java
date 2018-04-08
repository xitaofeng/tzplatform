package com.tzplatform.dao.webapp;

import com.tzplatform.entity.webapp.PlatFormAppAccessCount;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface PlatFormAppAccessCountDao {

    Integer addAccessCount(PlatFormAppAccessCount platFormAppAccessCount);

    Integer updateAccessCount(PlatFormAppAccessCount platFormAppAccessCount);

    List<PlatFormAppAccessCount> accessList(@Param(value = "appid") String appid);

    Integer updateCollectCount(@Param(value = "appid") String appid);

    void updateCollectCountCut(@Param(value = "appid") String appid);

    Integer updateUseCount(@Param(value = "appid") String appid, @Param(value = "usecount") Long usecount);

    List<LinkedHashMap<String,Object>> webappStatistics(@Param(value = "sortname") String sortname, @Param(value = "webappdevuser") String webappdevuser,@Param(value = "pageNum") String pageNum,@Param(value = "pageSize") String pageSize);

    List<LinkedHashMap<String,Object>> webapptypeList(@Param(value = "webappdevuser") String webappdevuser);

    Integer webappStatisticsCount(@Param(value = "sortname") String sortname,@Param(value = "webappdevuser") String webappdevuser,@Param(value = "pageNum") String pageNum,@Param(value = "pageSize") String pageSize);
}
