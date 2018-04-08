package com.tzplatform.service.user;

import com.tzplatform.entity.common.BaseResultDto;

public interface PlatFormMobileService {

    BaseResultDto updateUserPassword(String account, String passwordOld, String passwordNew);

    BaseResultDto updateTzjyyUserPassword(String account, String passwordOld, String passwordNew);

    BaseResultDto checkUserPassword(String account, String passwordOld, String passwordNew);

    BaseResultDto checkTokenResult(String token);
}
