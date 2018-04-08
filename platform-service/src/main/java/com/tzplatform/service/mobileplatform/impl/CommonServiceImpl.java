package com.tzplatform.service.mobileplatform.impl;

import com.tzplatform.dao.mobileplatform.PlatFormCommonDao;
import com.tzplatform.dao.mobileplatform.PlatformPosterDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.mobileplatform.Poster;
import com.tzplatform.service.mobileplatform.PlatformPosterService;
import com.tzplatform.utils.common.ByteObjUtils;
import com.tzplatform.utils.common.CommonUtils;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "commonService")
public class CommonServiceImpl  {
    @Resource
    private PlatFormCommonDao platFormCommonDao;
    @Value("${checked.image.path}")
    private String imageCheckPath;

    /**
     * 判断用户角色
     * @param accountId
     * @return
     */
    public String   checkUserRole(String accountId,String requestSource){
        String userRole = platFormCommonDao.queryUserRole(accountId,requestSource);
        //校管理角色
        if(null == userRole || "".equals(userRole)){
            return  CommonEnum.ROLE_CODE.学生.getValue();
        } else if(userRole.contains(CommonEnum.ROLE_CODE.超级管理员.getValue())){
            return CommonEnum.ROLE_CODE.超级管理员.getValue();
        }else if(userRole.contains(CommonEnum.ROLE_CODE.区管理员.getValue())){
            return CommonEnum.ROLE_CODE.区管理员.getValue();
        }else if(userRole.contains(CommonEnum.ROLE_CODE.校管理员.getValue())){
            return CommonEnum.ROLE_CODE.校管理员.getValue();
        }else{
          return  CommonEnum.ROLE_CODE.学生.getValue();
        }
    }

    /**
     * 用户学校标
     * @param accountId
     * @return
     */
    public String  getSchoolId(String accountId){
            return platFormCommonDao.userSchoolId(accountId);
    }
    public String checkImage(){
        return imageCheckPath;
    }
}
