package com.tzplatform.dao.user;

import com.tzplatform.entity.mobileplatform.MobileUser;
import com.tzplatform.entity.user.PlatFormUser;
import org.apache.ibatis.annotations.Param;

public interface PlatFormSysUserLoginDao {

    PlatFormUser getUserByAccount(@Param(value = "account") String account, @Param(value = "usertype") String usertype);

    MobileUser getMobieUserByAccount(@Param(value = "account") String account);

    MobileUser getMobieUserByWeiXin(@Param(value = "weixin") String weixin);

    MobileUser getMobieUserByQQ(@Param(value = "qq") String qq);

    MobileUser getMobieUserByWeiBo(@Param(value = "weibo") String weibo);

    MobileUser getMobieUserByTelPhone(@Param(value = "telphone") String telphone);

}
