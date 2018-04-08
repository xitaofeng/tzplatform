package com.tzplatform.service.system;

import com.tzplatform.entity.api.PlatFormNotice;
import com.tzplatform.entity.common.BaseResultDto;

public interface PlatFormNoticeService {
    BaseResultDto addNotice(PlatFormNotice platFormNotice);

    BaseResultDto deleteNotice(String id);

    BaseResultDto queryNotice(PlatFormNotice platFormNotice);

    BaseResultDto updateNotice(PlatFormNotice platFormNotice);

    BaseResultDto noticeDetail(PlatFormNotice platFormNotice);
}
