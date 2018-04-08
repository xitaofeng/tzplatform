package com.tzplatform.service.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.system.PlatFormExceptionLog;
import com.tzplatform.entity.system.PlatFormNormalLog;

public interface PlatFormLogService {
    BaseResultDto queryexceptionLog(PlatFormExceptionLog exceptionLog);

    BaseResultDto querynormalLog(PlatFormNormalLog normalLog);

    BaseResultDto insertNormalLog(PlatFormNormalLog normalLog);

    BaseResultDto insertExceptionLog(PlatFormExceptionLog exceptionLog);

    BaseResultDto queryIdByAccount(String account);
}
