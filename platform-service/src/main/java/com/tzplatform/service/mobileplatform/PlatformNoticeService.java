package com.tzplatform.service.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.mobileplatform.NoticeModel;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface PlatformNoticeService {

     BaseResultDto addNotice(NoticeModel noticeModel,CommonsMultipartFile[] attachments);
     BaseResultDto noticeList(NoticeModel noticeModel);
     BaseResultDto noticeDetail(NoticeModel noticeModel);
     BaseResultDto release(NoticeModel noticeModel);
     BaseResultDto noticeUpdate(NoticeModel noticeModel);



}
