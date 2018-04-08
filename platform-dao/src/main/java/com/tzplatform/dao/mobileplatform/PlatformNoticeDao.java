package com.tzplatform.dao.mobileplatform;

import com.tzplatform.entity.mobileplatform.NoticeModel;

import java.util.List;

public interface PlatformNoticeDao {

     int addNotice(NoticeModel noticeModel);
     NoticeModel noticeDetail(NoticeModel noticeModel);
     int release(NoticeModel noticeModel);
     int noticeUpdate(NoticeModel noticeModel);
     List<NoticeModel> noticeList(NoticeModel noticeModel);
     int noticeListCount(NoticeModel noticeModel);

}
