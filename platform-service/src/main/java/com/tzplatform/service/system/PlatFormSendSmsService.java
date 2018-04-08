package com.tzplatform.service.system;

import com.tzplatform.entity.common.BaseResultDto;

public interface PlatFormSendSmsService {

    BaseResultDto sendSms(String telphone);

    BaseResultDto checKSms(String telphone, String captcha);
}
