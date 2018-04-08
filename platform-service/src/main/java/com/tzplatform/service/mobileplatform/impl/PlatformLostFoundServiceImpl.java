package com.tzplatform.service.mobileplatform.impl;

import com.tzplatform.dao.mobileplatform.PlatFormCommonDao;
import com.tzplatform.dao.mobileplatform.PlatformLostFoundDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.mobileplatform.LostFound;
import com.tzplatform.entity.mobileplatform.LostFoundReview;
import com.tzplatform.service.helper.SambaService;
import com.tzplatform.service.mobileplatform.PlatformLostFoundService;
import com.tzplatform.service.tokenfilter.AccessToken;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 失物招领 寻物启事
 */
@Service(value = "platformLostFoundService")
public class PlatformLostFoundServiceImpl implements PlatformLostFoundService {

    @Resource
    private PlatformLostFoundDao platformLostFoundDao;

    @Resource
    private CommonServiceImpl commonService;

    @Value("${lostFound.file.path}")
    private String webPath;
    @Resource
    private SambaService sambaService;
    @Value("${image.path}")
    private String imagePath;

    /**
     * 失物招领 寻物启事 新增
     * @param lostFound
     * @param images
     * @return
     */
    @Override
    @Transactional
    @AopLog(description = "新增失物招领/寻物启事", menuname = "失物招领/寻物启事")
    public BaseResultDto addLostFound(LostFound lostFound, CommonsMultipartFile[] images) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //获取当前登录账号
        String accountId = CommonUtils.getAccount();
        //学校标识
        String schoolId = commonService.getSchoolId(accountId);
        lostFound.setSchoolId(schoolId);
        lostFound.setId(UidUtils.getId());//设置id
        //设置用户表示
        lostFound.setCreateId(accountId);
        lostFound.setCreateTime(new Date());
        //上传图片处理
        if(images!=null) {
            String imagePath="";//附件名称
            for (int i = 0; i < images.length; i++) {
                if (!images[i].isEmpty()) {
                    Map<String, Object> result = CommonUtils.upLoadFiles(images[i], webPath,CommonEnum.IMAGT_TYPE.失物招领.getValue());
                    //文件服务器上传
                    String realpath = result.get("realpath").toString();
                    String serveraddr = result.get("filepath").toString();
                    sambaService.uploadFile(realpath, serveraddr);
                    imagePath += String.valueOf(result.get("filepath"))+",";
                }
            }
            lostFound.setImage(imagePath);
        }
        int result =platformLostFoundDao.addLostFound(lostFound);
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
      * 失物招领 寻物启事 列表
      * @param lostFound
      * @return
     */
    @Override
    public BaseResultDto lostFoundList(LostFound lostFound,HttpServletRequest request) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //用户标识
        String accountId = CommonUtils.getAccount();
        //用户角色 true 校管理 false 区管理
//        String requestSource = request.getHeader(CommonEnum.HEAD_PARAM.REQUESTSOURCE.getValue());
        String requestSource = lostFound.getRequestSource();
        String roleCode = commonService.checkUserRole(accountId,requestSource);

        // 移动端请求
        if(("app").equals(requestSource)){
            if(roleCode.equals(CommonEnum.ROLE_CODE.校管理员.getValue())){
                //如果是校管理 这设置学校标识
                String schoolId = commonService.getSchoolId(accountId);
                lostFound.setSchoolId(schoolId);
            }else{
                //查询自己提交的
                lostFound.setCreateId(accountId);
            }
        }else{
            if(roleCode.equals(CommonEnum.ROLE_CODE.校管理员.getValue())){
                //如果是校管理 这设置学校标识
                String schoolId = commonService.getSchoolId(accountId);
                lostFound.setSchoolId(schoolId);
            }
        }
        List<LostFound> noticeList = platformLostFoundDao.lostFoundList(lostFound);
        int count = platformLostFoundDao.lostFoundListCount(lostFound);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(noticeList);
        baseResultDto.setTotal(count);
        return baseResultDto;
    }

    /**
     * 详情
     * @param lostFound
     * @return
     */
    @Override
    @AopLog(description = "失物招领/寻物启事查看详情", menuname = "失物招领/寻物启事")
    public BaseResultDto lostFoundDetail(LostFound lostFound,HttpServletRequest request) {
        BaseResultDto baseResultDto = new BaseResultDto();
        LostFound lostFoundDetail = platformLostFoundDao.lostFoundDetail(lostFound);
        String image =lostFoundDetail.getImage();
        if(image!=null&&!"".equals(image)){
            String images[]=image.split(",");
            List<String> imageUrl = new ArrayList<String>();
            for(String im:images){
               imageUrl.add(imagePath+im);
            }
            lostFoundDetail.setImageUrl(imageUrl);
        }
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(lostFoundDetail);
        return baseResultDto;
    }

    /**
     * 评论
     * @param lostFoundReview
     * @return
     */
    @Override
    @AopLog(description = "失物招领/寻物启事评论", menuname = "失物招领/寻物启事")
    public BaseResultDto review(LostFoundReview lostFoundReview) {
        BaseResultDto baseResultDto = new BaseResultDto();

        lostFoundReview.setId(UidUtils.getId());
        String accountId = CommonUtils.getAccount();
        lostFoundReview.setUserId(accountId);
        lostFoundReview.setReplyTime(new Date());
        int result =platformLostFoundDao.lostFoundReview(lostFoundReview);
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
     * 评论列表
     * @param lostFoundReview
     * @return
     */
    @Override
    public BaseResultDto reviewList(LostFoundReview lostFoundReview) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<LostFoundReview> noticeList = platformLostFoundDao.reviewList(lostFoundReview);
        int count = platformLostFoundDao.reviewListCount(lostFoundReview);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(noticeList);
        baseResultDto.setTotal(count);
        return baseResultDto;
    }

    /**
     * 失物招领/寻物启事删除
     * @param ids
     * @return
     */
    @Override
    @AopLog(description = "失物招领/寻物启事删除", menuname = "失物招领/寻物启事")
    public BaseResultDto deleteLostFound(String ids) {
        BaseResultDto baseResultDto = new BaseResultDto();
        int resultCode = platformLostFoundDao.deleteLostFound(ids.split(","));
        if (1 == resultCode) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 失物招领/寻物启事 评论删除
     * @param ids
     * @return
     */
    @Override
    @AopLog(description = "失物招领/寻物启事评论删除", menuname = "失物招领/寻物启事")
    public BaseResultDto deleteReview(String ids) {
        BaseResultDto baseResultDto = new BaseResultDto();
        int resultCode = platformLostFoundDao.deleteReview(ids.split(","));
        if (1 == resultCode) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }
}
