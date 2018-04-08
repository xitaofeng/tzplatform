package com.tzplatform.dao.user;

import com.tzplatform.entity.user.PlatFormSendMail;
import com.tzplatform.entity.user.PlatFormUser;
import org.apache.ibatis.annotations.Param;

public interface PlatFormUserRegisterDao {

    PlatFormUser getUserByUserId(@Param(value = "userid") String userid, @Param(value = "usertype")String usertype);

    Integer addSendMail(PlatFormSendMail sendMail);
}
