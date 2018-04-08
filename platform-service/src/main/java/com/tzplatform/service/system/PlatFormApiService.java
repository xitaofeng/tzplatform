package com.tzplatform.service.system;

import com.tzplatform.entity.api.PlatFormApiType;
import com.tzplatform.entity.common.BaseResultDto;

public interface PlatFormApiService {
    BaseResultDto addApi(PlatFormApiType apiType);
    BaseResultDto editApi(PlatFormApiType apiType);
    BaseResultDto deleteApi(String id);
    BaseResultDto queryListApi(PlatFormApiType apiType);
}
