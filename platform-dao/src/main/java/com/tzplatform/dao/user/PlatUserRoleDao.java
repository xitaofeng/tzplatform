package com.tzplatform.dao.user;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlatUserRoleDao {

    Integer addUserToRole(@Param(value = "id") String id, @Param(value = "userid") String userid, @Param(value = "roleid") String roleid);

    Integer delUserToRole(@Param(value = "userid") String userid);

    List<String> getRoleByUser(@Param(value = "userid") String userid);
}
