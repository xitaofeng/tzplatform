package com.tzplatform.dao.tzuum;

import org.apache.ibatis.annotations.Param;

public interface PlatFormUumUserDao {

    Integer updateUserPwd(@Param(value="account") String account, @Param(value="passwd")String passwd);
}
