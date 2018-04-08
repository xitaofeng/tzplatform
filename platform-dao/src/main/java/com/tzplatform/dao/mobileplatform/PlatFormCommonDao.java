package com.tzplatform.dao.mobileplatform;

import org.apache.ibatis.annotations.Param;

/**
 * 用户基本信息查询
 */
public interface PlatFormCommonDao {
    String queryUserRole(@Param(value = "accountId") String accountId,@Param(value = "requestSource")String requestSource);
    String userSchoolId(@Param(value = "accountId") String accountId);
}
