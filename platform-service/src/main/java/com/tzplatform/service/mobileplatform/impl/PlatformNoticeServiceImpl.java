package com.tzplatform.service.mobileplatform.impl;

import com.tzplatform.dao.mobileplatform.PlatFormCommonDao;
import com.tzplatform.dao.mobileplatform.PlatformNoticeDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.mobileplatform.NoticeModel;
import com.tzplatform.service.mobileplatform.PlatformNoticeService;
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

@Service(value = "platformNoticeService")
public class PlatformNoticeServiceImpl implements PlatformNoticeService {

    @Resource
    private PlatformNoticeDao platformNoticeDao;

    @Resource
    private PlatFormCommonDao platFormCommonDao;

    @Value("${attachment.file.path}")
    private String webPath;



    /**
     * 新增 通知公告
     * @param noticeModel
     * @return
     */
    @Override
    @Transactional
    public BaseResultDto addNotice(NoticeModel noticeModel,CommonsMultipartFile[] attachments) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //获取当前登录账号
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accountId = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());

        noticeModel.setId(UidUtils.getId());//设置id
        String schoolId = noticeModel.getSchoolId();//获取学校标识

        //如果为空并且当前用户为校管理员，则设置该条通知公告为当前登录用户属学校
//         boolean flag = this.checkUserRole(accountId);
//        if((schoolId==null || "".equals(schoolId))&&!flag){
//             schoolId =platFormCommonDao.userSchoolId(accountId);
//             noticeModel.setSchoolId(schoolId);
//        }
        //上传附件 处理
        if(attachments!=null) {
            String attenName="";//附件名称
            String attentPath="";//附件路径
            for (int i = 0; i < attachments.length; i++) {
                if (!attachments[i].isEmpty()) {
                    Map<String, Object> result = CommonUtils.upLoadFiles(attachments[i], webPath,"");
                    attenName = String.valueOf(result.get("filename"));
                    attentPath = String.valueOf(result.get("filepath"));
                }
            }
            noticeModel.setAttachment(attenName);
            noticeModel.setAttachmentPath(attentPath);
        }
        int result =platformNoticeDao.addNotice(noticeModel);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }


   /* *
     * 判断用户角色
     * @param accountId
     * @return
    public boolean checkUserRole(String accountId){
        String userRole = platFormCommonDao.queryUserRole(accountId);
        //只有校管理角色
         if(null!=userRole&&userRole.equals(CommonEnum.ROLE_CODE.校管理员)){
             return false;
         }
        return true;
    }*/

    /**
     * 通知公告列表
     * @param noticeModel
     * @return
     */
    @Override
    public BaseResultDto noticeList(NoticeModel noticeModel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        // 需要根据角色判决 查询所有还是个人
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accountId = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        //获取用户角色
        String userRole = platFormCommonDao.queryUserRole(accountId,"");
        //校管理只查询该学校信息，其他管理查询所有
        if(userRole!=null&&userRole.equals(CommonEnum.ROLE_CODE.校管理员)){
            String schoolId = platFormCommonDao.userSchoolId(accountId);
            noticeModel.setSchoolId(schoolId);
        }
        List<NoticeModel> noticeList = platformNoticeDao.noticeList(noticeModel);
        int count = platformNoticeDao.noticeListCount(noticeModel);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(noticeList);
        baseResultDto.setTotal(count);
        return baseResultDto;
    }

    /**
     * 通知公告详情
     * @param noticeModel
     * @return
     */
    @Override
    public BaseResultDto noticeDetail(NoticeModel noticeModel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        NoticeModel notice =platformNoticeDao.noticeDetail(noticeModel);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(notice);
        return baseResultDto;
    }
    /**
     * 修改 通知公告
     * @param noticeModel
     * @return
     */
    @Override
    @Transactional
    public BaseResultDto noticeUpdate(NoticeModel noticeModel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //获取当前登录账号
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accountId = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());

        String schoolId = noticeModel.getSchoolId();//获取学校标识

//        //如果为空并且当前用户为校管理员，则设置该条通知公告为当前登录用户属学校
//        boolean flag = this.checkUserRole(accountId);
//        if((schoolId==null || "".equals(schoolId))&&!flag){
//            schoolId =platFormCommonDao.userSchoolId(accountId);
//            noticeModel.setSchoolId(schoolId);
//        }
        int result =platformNoticeDao.noticeUpdate(noticeModel);
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
     *  通知公告发布 取消
     * @param noticeModel
     * @return
     */
    @Override
    public BaseResultDto release(NoticeModel noticeModel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        noticeModel.setPublishDate(new Date());
        int result =platformNoticeDao.release(noticeModel);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_UPDATE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }
}
