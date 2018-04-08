package com.tzplatform.service.system;

import com.tzplatform.entity.api.PlatFormApi;
import com.tzplatform.entity.common.BaseResultDto;

public interface PlatFormApiManagerService {

    BaseResultDto addApi(PlatFormApi platFormApi);

    BaseResultDto editApi(PlatFormApi platFormApi);

    BaseResultDto deleteApi(String id);

    BaseResultDto queryListApi(PlatFormApi platFormApi);

    BaseResultDto statisticsApi();

    BaseResultDto apiTree(PlatFormApi platFormApi);
}
