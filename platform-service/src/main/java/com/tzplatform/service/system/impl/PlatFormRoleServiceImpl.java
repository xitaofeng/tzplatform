package com.tzplatform.service.system.impl;

import com.tzplatform.dao.system.PlatFormMenuRoleDao;
import com.tzplatform.dao.system.PlatFormRoleDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.system.PlatFormRole;
import com.tzplatform.service.system.PlatFormRoleService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "platFormRoleService")
public class PlatFormRoleServiceImpl implements PlatFormRoleService {

    @Resource
    private PlatFormRoleDao platFormRoleDao;

    @Resource
    private DataSourceTransactionManager transactionManager;

    @Resource
    private PlatFormMenuRoleDao platFormMenuRoleDao;


    /**
     * 添加角色
     *
     * @param platFormRole
     * @return
     */
    @Override
    @Transactional
    @AopLog(menuname = "角色管理",description = "添加角色")
    public BaseResultDto addRole(PlatFormRole platFormRole,String menuids) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormRole.setId(UidUtils.getId());
        Integer result = platFormRoleDao.addRole(platFormRole);
        //添加角色与菜单关系
        addResourceToRole(platFormRole.getId(),menuids);

        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 修改角色
     *
     * @param platFormRole
     * @return
     */
    @Override
    @Transactional
    @AopLog(menuname = "角色管理",description = "修改角色")
    public BaseResultDto editRole(PlatFormRole platFormRole,String menuids) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormRoleDao.editRole(platFormRole);
        //添加角色与菜单关系
        addResourceToRole(platFormRole.getId(),menuids);

        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_UPDATE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_UPDATE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Override
    @AopLog(menuname = "角色管理",description = "删除角色")
    public BaseResultDto delRole(String id) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormRoleDao.delRole(id);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_DELETE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_DELETE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 角色列表
     *
     * @param platFormRole
     * @return
     */
    @Override
    public BaseResultDto listRole(PlatFormRole platFormRole) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormRole> resultList = platFormRoleDao.listRole(platFormRole);
        Integer resultCount = platFormRoleDao.listRoleCount(platFormRole);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 角色总数
     *
     * @param platFormRole
     * @return
     */
    @Override
    public Integer listRoleCount(PlatFormRole platFormRole) {
        return platFormRoleDao.listRoleCount(platFormRole);
    }

    /**
     * 角色和菜单进行关联
     *
     * @param roleid
     * @param menuids
     * @return
     */
    @Override
    @AopLog(menuname = "角色管理",description = "角色关联菜单")
    @Transactional
    public BaseResultDto addResourceToRole(String roleid, String menuids) {
        BaseResultDto baseResultDto = new BaseResultDto();

        Integer delResource = platFormMenuRoleDao.delRourceByRole(roleid);

        Integer addallresult = 0;

        String[] newids = menuids.split(",");
        for (int i = 0; i < newids.length; i++) {
            String id = UidUtils.getId();
            Integer addresult = platFormMenuRoleDao.addResourceByRole(id, newids[i], roleid);
            addallresult = addallresult + addresult;
        }
        if (addallresult == newids.length) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return  baseResultDto;
    }

    /**
     * 获取角色对应的资源
     * @param roleid
     * @return
     */
    @Override
    public BaseResultDto getResourceByRole(String roleid) {
        BaseResultDto baseResultDto = new BaseResultDto();
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(platFormMenuRoleDao.getResourceByRole(roleid));
        return baseResultDto;
    }
}
