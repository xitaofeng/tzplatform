package com.tzplatform.dao.mobileplatform;

import com.tzplatform.entity.mobileplatform.FeedBackType;
import com.tzplatform.entity.mobileplatform.Feedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlatformFeedBackDao {

     int addfeedBackType(FeedBackType feedBackType);
     int updateFeedBackType(FeedBackType feedBackType);
     List<FeedBackType> feedBackTypeList (FeedBackType feedBackType);
     int feedBackTypeListCount(FeedBackType feedBackType);
     //意见反馈
     int addfeedBack(Feedback feedBack);
     List<Feedback> feedBackList (Feedback feedBack);
     int feedBackCount(Feedback feedBack);

     int deleteFeedBack(@Param(value = "ids") String ids[]);
     int updateStatus(Feedback feedBack);


}
