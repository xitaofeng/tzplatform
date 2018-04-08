package com.tzplatform.service.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.system.PlatFormRole;

import java.util.List;

public interface PlatFormRoleService {

    BaseResultDto addRole(PlatFormRole platFormRole,String menuids);

    BaseResultDto editRole(PlatFormRole platFormRole,String menuids);

    BaseResultDto delRole(String id);

    BaseResultDto listRole(PlatFormRole platFormRole);

    Integer listRoleCount(PlatFormRole platFormRole);

    BaseResultDto addResourceToRole(String roleid, String menuids);

    BaseResultDto getResourceByRole(String roleid);
}
