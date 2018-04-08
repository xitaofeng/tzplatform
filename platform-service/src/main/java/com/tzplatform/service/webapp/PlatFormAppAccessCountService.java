package com.tzplatform.service.webapp;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.webapp.PlatFormAppAccessCount;

public interface PlatFormAppAccessCountService {

    BaseResultDto updateAccessCount(PlatFormAppAccessCount platFormAppAccessCount,String appid);

    BaseResultDto updateCollectCount(String appid);

    void updateCollectCountCut(String appid);

    Integer updateUseCount(String appid, Long usecount);
}
