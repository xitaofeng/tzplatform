package com.tzplatform.dao.system;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlatFormMenuRoleDao {

    Integer delRourceByRole(@Param(value = "roleid") String roleid);

    Integer addResourceByRole(@Param(value = "id") String id, @Param(value = "menuid") String menuid, @Param(value = "roleid") String roleid);

    List<String> getResourceByRole(@Param(value = "roleid") String roleid);

    List<String> getResourceByRoleIds(@Param(value = "list") List<String> roleids);

}
