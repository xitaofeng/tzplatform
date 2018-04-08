package com.tzplatform.service.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.mobileplatform.FeedBackType;
import com.tzplatform.entity.mobileplatform.Feedback;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface PlatFormFeedBackService {
    //意见类型
    BaseResultDto feedBackType(FeedBackType feedBackType);
    BaseResultDto feedBackTypeList(FeedBackType feedBackType);
    //意见反馈
    BaseResultDto addFeedBack(Feedback feedBack, CommonsMultipartFile[] images);
    BaseResultDto feedBackList(Feedback feedBack, HttpServletRequest request);
    BaseResultDto deleteFeedBack(String ids);
    BaseResultDto updateStatus(Feedback feedBack);
}
