package com.tzplatform.service.mobileplatform.impl;

import com.tzplatform.dao.mobileplatform.PlatFormCommonDao;
import com.tzplatform.dao.mobileplatform.PlatformFeedBackDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.mobileplatform.FeedBackType;
import com.tzplatform.entity.mobileplatform.Feedback;
import com.tzplatform.entity.mobileplatform.Poster;
import com.tzplatform.service.helper.SambaService;
import com.tzplatform.service.mobileplatform.PlatFormFeedBackService;
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
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "platformFeedBackService")
public class PlatFormFeedBackServiceImpl  implements PlatFormFeedBackService{

    @Resource
    private PlatformFeedBackDao platformFeedBackDao;

    @Resource
    private PlatFormCommonDao platFormCommonDao;

    @Resource
    private CommonServiceImpl commonService;
    @Resource
    private SambaService sambaService;
    @Value("${feedback.file.path}")
    private String feedBackUrl;
    @Value("${image.path}")
    private String imagePath;

    /**
     * 意见类型 新增修改
     * @param feedBackType
     * @return
     */
    @Override
    @Transactional
    public BaseResultDto feedBackType(FeedBackType feedBackType) {
        BaseResultDto baseResultDto = new BaseResultDto();
        int resultCode=0;
        //新增
        if(feedBackType.getId()==null ||"".equals(feedBackType.getId())){
            feedBackType.setId(UidUtils.getId());
            resultCode = platformFeedBackDao.addfeedBackType(feedBackType);
        }else{
            resultCode = platformFeedBackDao.updateFeedBackType(feedBackType);
        }
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
     * 意见类型列表
     * @param feedBackType
     * @return
     */
    @Override
    public BaseResultDto feedBackTypeList(FeedBackType feedBackType) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<FeedBackType> list = platformFeedBackDao.feedBackTypeList(feedBackType);
        int count = platformFeedBackDao.feedBackTypeListCount(feedBackType);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(list);
        baseResultDto.setTotal(count);
        return baseResultDto;
    }

    /**
     * 新增意见反馈
     * @param feedBack
     * @return
     */
    @Override
    @Transactional
    @AopLog(description = "新增意见", menuname = "意见反馈")
    public BaseResultDto addFeedBack(Feedback feedBack, CommonsMultipartFile[] images) {
         BaseResultDto baseResultDto = new BaseResultDto();
          //获取当前登录账号
         String accountId ;
         //云平台 单独处理
         if(!"".equals(feedBack.getAccount()) && null != feedBack.getAccount()){
             accountId = feedBack.getAccount();
         }else{
             accountId = CommonUtils.getAccount();
         }

        //上传图片处理
        if(images!=null) {
            String imagePath="";//附件名称
            for (int i = 0; i < images.length; i++) {
                if (!images[i].isEmpty()) {
                    Map<String, Object> result = CommonUtils.upLoadFiles(images[i], feedBackUrl,CommonEnum.IMAGT_TYPE.意见反馈.getValue());
                    //文件服务器上传
                    String realpath = result.get("realpath").toString();
                    String serveraddr = result.get("filepath").toString();
                    sambaService.uploadFile(realpath, serveraddr);
                    imagePath += String.valueOf(result.get("filepath"))+",";
                }
            }
            feedBack.setImagePath(imagePath);
        }
         feedBack.setStatus("1");
         feedBack.setId(UidUtils.getId());
         feedBack.setOpinionTime(new Date());//反馈时间
         feedBack.setUserId(accountId);//反馈人id
        //反馈人所属学校标识
        String schoolId = commonService.getSchoolId(accountId);
        feedBack.setSchoolId(schoolId);
        int resultCode = platformFeedBackDao.addfeedBack(feedBack);
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
     * 意见反馈 列表
     * @param feedBack
     * @return
     */
    @Override
    public BaseResultDto feedBackList(Feedback feedBack, HttpServletRequest request) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //获取当前登录账号
        String accountId ;
        //云平台 单独处理
        if(!"".equals(feedBack.getAccount()) && null != feedBack.getAccount()){
            accountId = feedBack.getAccount();
        }else{
            accountId = CommonUtils.getAccount();
        }
//        String requestSource = request.getHeader(CommonEnum.HEAD_PARAM.REQUESTSOURCE.getValue());
        String requestSource = feedBack.getRequestSource();
        //用户角色 true 校管理 false 区管理
        String roleCode = commonService.checkUserRole(accountId,requestSource);

        // 移动端请求
        if(("app").equals(requestSource)){
            if(roleCode.equals(CommonEnum.ROLE_CODE.校管理员.getValue())){
                //如果是校管理 这设置学校标识
                String schoolId = commonService.getSchoolId(accountId);
                feedBack.setSchoolId(schoolId);
            }else if(roleCode.equals(CommonEnum.ROLE_CODE.学生.getValue())){
                //查询自己提交的
                feedBack.setUserId(accountId);
            }else{

            }
        }else{
            if(roleCode.equals(CommonEnum.ROLE_CODE.校管理员.getValue())){
                //如果是校管理 这设置学校标识
                String schoolId = commonService.getSchoolId(accountId);
                feedBack.setSchoolId(schoolId);
            }else if(roleCode.equals(CommonEnum.ROLE_CODE.学生.getValue())){
                //查询自己提交的
                feedBack.setUserId(accountId);
            }else{

            }
        }
        List<Feedback> list = platformFeedBackDao.feedBackList(feedBack).stream().map(pos -> {
            String image = pos.getImagePath();
            if(image!=null&&!"".equals(image)){
                String images[]=image.split(",");
                List<String> imageUrl = new ArrayList<String>();
                for(String im:images){
                    imageUrl.add(imagePath+im);
                }
                pos.setImageUrl(imageUrl);
            }
            return pos;
        }).collect(Collectors.toList());
        int count = platformFeedBackDao.feedBackCount(feedBack);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(list);
        baseResultDto.setTotal(count);
        return baseResultDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    @AopLog(description = "意见删除", menuname = "意见反馈")
    public BaseResultDto deleteFeedBack(String ids) {
        BaseResultDto baseResultDto = new BaseResultDto();
        int resultCode = platformFeedBackDao.deleteFeedBack(ids.split(","));
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
     * 意见状态修改
     * @param feedBack
     * @return
     */
    @Override
    @AopLog(description = "意见修改", menuname = "意见反馈")
    public BaseResultDto updateStatus(Feedback feedBack) {
        BaseResultDto baseResultDto = new BaseResultDto();
        int resultCode = platformFeedBackDao.updateStatus(feedBack);
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
