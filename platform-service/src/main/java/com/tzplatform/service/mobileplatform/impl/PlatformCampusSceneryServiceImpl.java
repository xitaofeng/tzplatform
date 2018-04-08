package com.tzplatform.service.mobileplatform.impl;

import com.tzplatform.dao.mobileplatform.PlatFormCommonDao;
import com.tzplatform.dao.mobileplatform.PlatformCampusSceneryDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.mobileplatform.CampusScenery;
import com.tzplatform.entity.mobileplatform.CampusSceneryType;
import com.tzplatform.service.helper.SambaService;
import com.tzplatform.service.mobileplatform.PlatformCampusSceneryService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.CommonUtils;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "platformCampusSceneryService")
public class PlatformCampusSceneryServiceImpl implements PlatformCampusSceneryService {

    @Resource
    private PlatformCampusSceneryDao platformCampusSceneryDao;

    @Resource
    private PlatFormCommonDao platFormCommonDao;

    @Resource
    private CommonServiceImpl commonService;

    @Value("${campusScenery.file.path}")
    private String campusSceneryPath;//上传图片路径
    @Resource
    private SambaService sambaService;
    @Value("${image.path}")
    private String imagePath;


    /**
     * 风光类型 新增修改
     * @param campusSceneryType
     * @param images
     * @return
     */
    @Override
    @Transactional
    public BaseResultDto campusSceneryType(CampusSceneryType campusSceneryType, CommonsMultipartFile[] images) {
        BaseResultDto baseResultDto = new BaseResultDto();
        campusSceneryType.setId(UidUtils.getId());//设置id
        //上传附件 处理
        if(images!=null) {
            String attentPath="";//附件路径
            for (int i = 0; i < images.length; i++) {
                if (!images[i].isEmpty()) {
                    Map<String, Object> result = CommonUtils.upLoadFiles(images[i], campusSceneryPath,CommonEnum.IMAGT_TYPE.校风光.getValue());
                    attentPath = String.valueOf(result.get("filepath"));
                }
            }
            campusSceneryType.setImage(attentPath);
        }
        int result =platformCampusSceneryDao.addCampusSceneryType(campusSceneryType);
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
     * 类型 列表
     * @param campusSceneryType
     * @return
     */
    @Override
    public BaseResultDto campusSceneryTypeList(CampusSceneryType campusSceneryType) {
        BaseResultDto baseResultDto = new BaseResultDto();
        String baseUrl="/common/type="+ CommonEnum.IMAGT_TYPE.校风光.getValue()+"&showImage?imageUrl=";
        List<CampusSceneryType> resultList = platformCampusSceneryDao.typeList(campusSceneryType).stream().map(pos -> {
            String image = pos.getImage();
            pos.setImageUrl(baseUrl+image);
            return pos;
        }).collect(Collectors.toList());
        Integer resultCount = platformCampusSceneryDao.typeListCount(campusSceneryType);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 上传风光
     * @param campusScenery
     * @param images
     * @return
     */
    @Override
    @Transactional
    @AopLog(description = "校园风光新增",menuname = "校园风光")
    public BaseResultDto addCampusScenery(CampusScenery campusScenery,CommonsMultipartFile[] images) {
        BaseResultDto baseResultDto = new BaseResultDto();

        //获取当前登录账号
        String accountId = CommonUtils.getAccount();
        //学校标识
        String schoolId = commonService.getSchoolId(accountId);
        campusScenery.setSchoolId(schoolId);
        campusScenery.setSchoolId(schoolId);//学校标识
        campusScenery.setApprove(CommonEnum.APPROVAL_STATUS.未审核.getValue());//默认未审核
        campusScenery.setCreateId(accountId);     //上传者
        campusScenery.setCreateTime(new Date());//上传时间
        //上传附件 处理
        if(images!=null) {
            String imagePaht="";//附件路径
            for (int i = 0; i < images.length; i++) {
                campusScenery.setId(UidUtils.getId());//设置id
                if (!images[i].isEmpty()) {
                    Map<String, Object> result = CommonUtils.upLoadFiles(images[i], campusSceneryPath,CommonEnum.IMAGT_TYPE.校风光.getValue());
                    imagePaht = String.valueOf(result.get("filepath"));
                    //文件服务器上传
                    String realpath = result.get("realpath").toString();
                    String serveraddr = result.get("filepath").toString();
                    sambaService.uploadFile(realpath, serveraddr);
                    campusScenery.setImage(imagePaht);
                    platformCampusSceneryDao.addCampusScenery(campusScenery);
                }
            }

        }
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        return baseResultDto;
    }

    /**
     * 风光列表
     * @param campusScenery
     * @return
     */
    @Override
    public BaseResultDto campusSceneryList(CampusScenery campusScenery,HttpServletRequest request) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //用户标识
        String accountId = CommonUtils.getAccount();
        //用户角色 true 校管理 false 区管理
        String requestSource = campusScenery.getRequestSource();
        String roleCode = commonService.checkUserRole(accountId,requestSource);
        // 移动端请求
        if(("app").equals(requestSource)){
            campusScenery.setApprove(CommonEnum.APPROVAL_STATUS.审核通过.getValue());
            if(roleCode.equals(CommonEnum.ROLE_CODE.校管理员.getValue())||roleCode.equals(CommonEnum.ROLE_CODE.学生.getValue())){
                //如果是校管理,或学生 这设置学校标识
                String schoolId = commonService.getSchoolId(accountId);
                campusScenery.setSchoolId(schoolId);
            }
        }else{
            if(roleCode.equals(CommonEnum.ROLE_CODE.校管理员.getValue())){
                //如果是校管理 这设置学校标识
                String schoolId = commonService.getSchoolId(accountId);
                campusScenery.setSchoolId(schoolId);
            }
        }
        List<CampusScenery> resultList = platformCampusSceneryDao.campusSceneryList(campusScenery).stream().map(pos -> {
            String image = pos.getImage();
            pos.setImageUrl(imagePath+image);
            return pos;
        }).collect(Collectors.toList());
        Integer resultCount = platformCampusSceneryDao.campusSceneryListCount(campusScenery);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 校园风光审核
     * @param campusScenery
     * @return
     */

    @Override
    @AopLog(description = "校园风光审核",menuname = "校园风光")
    public BaseResultDto approve(CampusScenery campusScenery) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //用户标识
        String accountId = CommonUtils.getAccount();
        campusScenery.setUpdateId(accountId);//审核人
        campusScenery.setUpdateTime(new Date());//审核时间
        int result = platformCampusSceneryDao.approve(campusScenery);
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
     * 校园风光删除
     * @param ids
     * @return
     */
    @Override
    @AopLog(description = "校园风光删除",menuname = "校园风光")
    public BaseResultDto delete(String ids) {
        BaseResultDto baseResultDto = new BaseResultDto();
        int result =platformCampusSceneryDao.delete(ids.split(","));
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }


}
