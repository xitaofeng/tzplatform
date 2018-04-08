package com.tzplatform.service.webapp.impl;

import com.tzplatform.dao.system.PlatFormApiDao;
import com.tzplatform.dao.system.PlatFormApiManagerDao;
import com.tzplatform.dao.system.PlatFormAppApiDao;
import com.tzplatform.dao.webapp.PlatFormAppAccessCountDao;
import com.tzplatform.dao.webapp.PlatFormWebAppDao;
import com.tzplatform.dao.webapp.PlatFormWebSchoolDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.common.TreeDto;
import com.tzplatform.entity.webapp.PlatFormAppAccessCount;
import com.tzplatform.entity.webapp.PlatFormAppComment;
import com.tzplatform.entity.webapp.PlatFormWebApp;
import com.tzplatform.entity.webapp.PlatFormWepAppFile;
import com.tzplatform.service.helper.SambaService;
import com.tzplatform.service.webapp.PlatFormAppAccessCountService;
import com.tzplatform.service.webapp.PlatFormWebAppService;
import com.tzplatform.utils.aoplog.AccountAopLog;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.ByteObjUtils;
import com.tzplatform.utils.common.CommonUtils;
import com.tzplatform.utils.common.IpUtil;
import com.tzplatform.utils.common.UidUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "platFormWebAppService")
public class PlatFormWebAppServiceImpl implements PlatFormWebAppService {

    @Resource
    private PlatFormWebAppDao platFormWebAppDao;

    @Resource
    private PlatFormAppApiDao platFormAppApiDao;

    @Resource
    private PlatFormAppAccessCountDao platFormAppAccessCountDao;

    @Resource
    private PlatFormWebSchoolDao platFormWebSchoolDao;

    @Resource
    private PlatFormApiManagerDao platFormApiManagerDao;

    @Resource
    private PlatFormApiDao platFormApiDao;

    @Resource
    private PlatFormAppAccessCountService platFormAppAccessCountService;

    @Value("${tmp.file.path}")
    private String tmppath;

    @Value("${appkey.prefix}")
    private String keyprefix;

    @Value("${appIp}")
    private String appIp;

    @Value("${platform.app.filepath}")
    private String appfilepath;

    @Value("${platform.appoperation.filepath}")
    private String appoperation;

    @Value("${platformMobile.app.filepath}")
    private String appMobilefilepath;

    @Resource
    private SambaService sambaService;


    /**
     * 添加应用
     *
     * @param platFormWebApp
     * @param applogosmall
     * @param applogo
     * @param regestinfofile
     * @param apporcodeinfo
     * @param appfileone
     * @param appfiletwo
     * @param appfilethree
     * @param appfilefour
     * @param appfile
     * @return
     */
    @Override
    @Transactional
    @AopLog(description = "添加应用", menuname = "应用管理")
    public BaseResultDto addWebApp(PlatFormWebApp platFormWebApp, CommonsMultipartFile applogosmall, CommonsMultipartFile applogo, CommonsMultipartFile regestinfofile, CommonsMultipartFile apporcodeinfo, CommonsMultipartFile appfileone, CommonsMultipartFile appfiletwo, CommonsMultipartFile appfilethree, CommonsMultipartFile appfilefour, CommonsMultipartFile appfile) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //设置应用主键
        platFormWebApp.setId(UidUtils.getId());
        //获取webappkey
        platFormWebApp.setWebappid(keyprefix + CommonUtils.getRandomCharAndNumr(16));
        platFormWebApp.setWebappsecret(UidUtils.getId());

        //判断app小图标是否为空
        if (null != applogosmall && !applogosmall.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(applogosmall, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);


            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] webapppicsmall = ByteObjUtils.fileToBytes(file);
                platFormWebApp.setWebapppicsmall(webapppicsmall);
                platFormWebApp.setPicsmallpath(filePath);
            }
        }
        //判断app图标是否为空
        if (null != applogo && !applogo.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(applogo, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] webapppic = ByteObjUtils.fileToBytes(file);
                platFormWebApp.setWebapppic(webapppic);
                platFormWebApp.setPicpath(filePath);
            }
        }
        //判断注册信息表是否为空
        if (null != regestinfofile && !regestinfofile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(regestinfofile, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] webappregisterinfo = ByteObjUtils.fileToBytes(file);
                platFormWebApp.setWebappregisterinfo(webappregisterinfo);
                platFormWebApp.setRegestinfopath(filePath);
            }
        }
        //判断二维码是否为空
        if (null != apporcodeinfo && !apporcodeinfo.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(apporcodeinfo, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] webappinstallorcode = ByteObjUtils.fileToBytes(file);
                platFormWebApp.setWebappinstallorcode(webappinstallorcode);
                platFormWebApp.setOrcodepath(filePath);
            }
        }
        //上传操作手册
        if (appfile != null && !appfile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfile, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            String filepath = (String) resultMap.get("filepath");
            platFormWebApp.setAppOperationUrl(filepath);
            platFormWebApp.setAppOperationName(appfile.getFileItem().getName());
        }

        //上传应用附件1
        if (appfileone != null && !appfileone.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfileone, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            File file = (File) resultMap.get("file");
            String filepath = (String) resultMap.get("filepath");
            PlatFormWepAppFile platFormWepAppFile = new PlatFormWepAppFile();
            platFormWepAppFile.setId(UidUtils.getId());
            platFormWepAppFile.setAppid(platFormWebApp.getId());
            platFormWepAppFile.setFilename(appfileone.getFileItem().getName());
            platFormWepAppFile.setFilepath(filepath);
            platFormWepAppFile.setFileorder("1");
            platFormWebAppDao.addAppFile(platFormWepAppFile);
        }
        //上传应用附件2
        if (appfiletwo != null && !appfiletwo.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfiletwo, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            File file = (File) resultMap.get("file");
            String filepath = (String) resultMap.get("filepath");
            PlatFormWepAppFile platFormWepAppFile = new PlatFormWepAppFile();
            platFormWepAppFile.setId(UidUtils.getId());
            platFormWepAppFile.setAppid(platFormWebApp.getId());
            platFormWepAppFile.setFilename(appfiletwo.getFileItem().getName());
            platFormWepAppFile.setFilepath(filepath);
            platFormWepAppFile.setFileorder("2");
            platFormWebAppDao.addAppFile(platFormWepAppFile);
        }
        //上传应用附件3
        if (appfilethree != null && !appfilethree.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfilethree, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            File file = (File) resultMap.get("file");
            String filepath = (String) resultMap.get("filepath");
            PlatFormWepAppFile platFormWepAppFile = new PlatFormWepAppFile();
            platFormWepAppFile.setId(UidUtils.getId());
            platFormWepAppFile.setAppid(platFormWebApp.getId());
            platFormWepAppFile.setFilename(appfilethree.getFileItem().getName());
            platFormWepAppFile.setFilepath(filepath);
            platFormWepAppFile.setFileorder("3");
            platFormWebAppDao.addAppFile(platFormWepAppFile);
        }

        //上传应用附件4
        if (appfilefour != null && !appfilefour.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfilefour, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            File file = (File) resultMap.get("file");
            String filepath = (String) resultMap.get("filepath");
            PlatFormWepAppFile platFormWepAppFile = new PlatFormWepAppFile();
            platFormWepAppFile.setId(UidUtils.getId());
            platFormWepAppFile.setAppid(platFormWebApp.getId());
            platFormWepAppFile.setFilename(appfilefour.getFileItem().getName());
            platFormWepAppFile.setFilepath(filepath);
            platFormWepAppFile.setFileorder("4");
            platFormWebAppDao.addAppFile(platFormWepAppFile);
        }
        //添加接口授权
        String appids = platFormWebApp.getApiids();
        platFormAppApiDao.delApiToApp(platFormWebApp.getId());
        if (StringUtils.isNotBlank(appids)) {
            String[] newids = appids.split(",");
            for (int i = 0; i < newids.length; i++) {
                String apiid = newids[i];
                if (StringUtils.isNotBlank(apiid)) {
                    platFormAppApiDao.addApiToApp(UidUtils.getId(), apiid, platFormWebApp.getId());
                }
            }
        }

        //判断应用适用范围 如果是校级需要与学校进行关联
        String schoolids = platFormWebApp.getSchoolids();
        platFormWebSchoolDao.delWebInSchool(platFormWebApp.getId());
        if (StringUtils.isNotBlank(schoolids) && CommonEnum.APP_SCOPE.校级.getValue().equals(platFormWebApp.getWebappscope())) {
            String[] newids = schoolids.split(",");
            for (int i = 0; i < newids.length; i++) {
                String schoolid = newids[i];
                if (StringUtils.isNotBlank(schoolid)) {
                    platFormWebSchoolDao.addWebToSchool(UidUtils.getId(), platFormWebApp.getId(), schoolid);
                }
            }
        }

        //更新应用统计表
        PlatFormAppAccessCount platFormAppAccessCount = new PlatFormAppAccessCount();
        platFormAppAccessCount.setAppid(platFormWebApp.getId());
        platFormAppAccessCount.setIntroduce(platFormWebApp.getDesciption());
        platFormAppAccessCount.setAppsuitobj(platFormWebApp.getWebappsuitobj());
        //添加一个默认应用标示1:默认应用0非默认应用
        platFormAppAccessCount.setDefaultflag(CommonEnum.APP_DEFAULT.非默认应用.getValue());
        platFormAppAccessCountService.updateAccessCount(platFormAppAccessCount, platFormWebApp.getId());


        platFormWebApp.setCreatetime(new Date());
        Integer result = platFormWebAppDao.addWebApp(platFormWebApp);
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
     * 修改应用
     *
     * @param platFormWebApp
     * @param applogosmall
     * @param applogo
     * @param regestinfofile
     * @param apporcodeinfo
     * @param appfileone
     * @param appfiletwo
     * @param appfilethree
     * @param appfilefour
     * @param deleteIdList
     * @param appfile
     * @return
     */
    @Override
    @Transactional
    @AopLog(description = "修改应用", menuname = "应用管理")
    public BaseResultDto editWebApp(PlatFormWebApp platFormWebApp, CommonsMultipartFile applogosmall, CommonsMultipartFile applogo, CommonsMultipartFile regestinfofile, CommonsMultipartFile apporcodeinfo, CommonsMultipartFile appfileone, CommonsMultipartFile appfiletwo, CommonsMultipartFile appfilethree, CommonsMultipartFile appfilefour, String[] deleteIdList, CommonsMultipartFile appfile) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //判断app小图标是否为空
        if (null != applogosmall && !applogosmall.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(applogosmall, tmppath);

            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);

            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] webapppicsmall = ByteObjUtils.fileToBytes(file);
                platFormWebApp.setWebapppicsmall(webapppicsmall);
                platFormWebApp.setPicsmallpath(filePath);
            }
        }
        //判断app图标是否为空
        if (null != applogo && !applogo.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(applogo, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] webapppic = ByteObjUtils.fileToBytes(file);
                platFormWebApp.setWebapppic(webapppic);
                platFormWebApp.setPicpath(filePath);
            }
        }
        //判断注册信息表是否为空
        if (null != regestinfofile && !regestinfofile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(regestinfofile, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] webappregisterinfo = ByteObjUtils.fileToBytes(file);
                platFormWebApp.setWebappregisterinfo(webappregisterinfo);
                platFormWebApp.setRegestinfopath(filePath);
            }
        }
        //判断二维码是否为空
        if (null != apporcodeinfo && !apporcodeinfo.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(apporcodeinfo, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] webappinstallorcode = ByteObjUtils.fileToBytes(file);
                platFormWebApp.setWebappinstallorcode(webappinstallorcode);
                platFormWebApp.setOrcodepath(filePath);
            }
        }
        //上传操作手册
        if (appfile != null && !appfile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfile, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            String filepath = (String) resultMap.get("filepath");
            platFormWebApp.setAppOperationName(appfile.getFileItem().getName());
            platFormWebApp.setAppOperationUrl(filepath);
        } else {
            platFormWebApp.setAppOperationUrl(null);
            platFormWebApp.setAppOperationName(null);
        }

        //上传应用附件1
        if (appfileone != null && !appfileone.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfileone, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            //先删除原有附件关系
            platFormWebAppDao.deleteFile(platFormWebApp.getId(), "1");

            File file = (File) resultMap.get("file");
            String filepath = (String) resultMap.get("filepath");
            PlatFormWepAppFile platFormWepAppFile = new PlatFormWepAppFile();
            platFormWepAppFile.setId(UidUtils.getId());
            platFormWepAppFile.setAppid(platFormWebApp.getId());
            platFormWepAppFile.setFilename(appfileone.getFileItem().getName());
            platFormWepAppFile.setFilepath(filepath);
            platFormWepAppFile.setFileorder("1");
            platFormWebAppDao.addAppFile(platFormWepAppFile);
        }
        //上传应用附件2
        if (appfiletwo != null && !appfiletwo.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfiletwo, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            //先删除原有附件关系
            platFormWebAppDao.deleteFile(platFormWebApp.getId(), "2");

            File file = (File) resultMap.get("file");
            String filepath = (String) resultMap.get("filepath");
            PlatFormWepAppFile platFormWepAppFile = new PlatFormWepAppFile();
            platFormWepAppFile.setId(UidUtils.getId());
            platFormWepAppFile.setAppid(platFormWebApp.getId());
            platFormWepAppFile.setFilename(appfiletwo.getFileItem().getName());
            platFormWepAppFile.setFilepath(filepath);
            platFormWepAppFile.setFileorder("2");
            platFormWebAppDao.addAppFile(platFormWepAppFile);
        }
        //上传应用附件3
        if (appfilethree != null && !appfilethree.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfilethree, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            //先删除原有附件关系
            platFormWebAppDao.deleteFile(platFormWebApp.getId(), "3");

            File file = (File) resultMap.get("file");
            String filepath = (String) resultMap.get("filepath");
            PlatFormWepAppFile platFormWepAppFile = new PlatFormWepAppFile();
            platFormWepAppFile.setId(UidUtils.getId());
            platFormWepAppFile.setAppid(platFormWebApp.getId());
            platFormWepAppFile.setFilename(appfilethree.getFileItem().getName());
            platFormWepAppFile.setFilepath(filepath);
            platFormWepAppFile.setFileorder("3");
            platFormWebAppDao.addAppFile(platFormWepAppFile);
        }

        //上传应用附件4
        if (appfilefour != null && !appfilefour.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(appfilefour, tmppath);
            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);
            //先删除原有附件关系
            platFormWebAppDao.deleteFile(platFormWebApp.getId(), "4");

            File file = (File) resultMap.get("file");
            String filepath = (String) resultMap.get("filepath");
            PlatFormWepAppFile platFormWepAppFile = new PlatFormWepAppFile();
            platFormWepAppFile.setId(UidUtils.getId());
            platFormWepAppFile.setAppid(platFormWebApp.getId());
            platFormWepAppFile.setFilename(appfilefour.getFileItem().getName());
            platFormWepAppFile.setFilepath(filepath);
            platFormWepAppFile.setFileorder("4");
            platFormWebAppDao.addAppFile(platFormWepAppFile);
        }
        //删除除去的附件只是数据库删除,附件并没有删除
        if (deleteIdList != null && deleteIdList.length > 0) {
            platFormWebAppDao.deleteFileList(deleteIdList);
        }

        //添加接口授权
        String appids = platFormWebApp.getApiids();
        if (StringUtils.isNotBlank(appids)) {
            platFormAppApiDao.delApiToApp(platFormWebApp.getId());
            String[] newids = appids.split(",");
            for (int i = 0; i < newids.length; i++) {
                String apiid = newids[i];
                if (StringUtils.isNotBlank(apiid)) {
                    platFormAppApiDao.addApiToApp(UidUtils.getId(), apiid, platFormWebApp.getId());
                }
            }
        }

        //判断应用适用范围 如果是校级需要与学校进行关联
        String schoolids = platFormWebApp.getSchoolids();
        if (StringUtils.isNotBlank(schoolids) && CommonEnum.APP_SCOPE.校级.getValue().equals(platFormWebApp.getWebappscope())) {
            platFormWebSchoolDao.delWebInSchool(platFormWebApp.getId());
            String[] newids = schoolids.split(",");
            for (int i = 0; i < newids.length; i++) {
                String schoolid = newids[i];
                if (StringUtils.isNotBlank(schoolid)) {
                    platFormWebSchoolDao.addWebToSchool(UidUtils.getId(), platFormWebApp.getId(), schoolid);
                }
            }
        }

        //更新应用统计表
        PlatFormAppAccessCount platFormAppAccessCount = new PlatFormAppAccessCount();
        platFormAppAccessCount.setAppid(platFormWebApp.getId());
        platFormAppAccessCount.setIntroduce(platFormWebApp.getDesciption());
        platFormAppAccessCount.setAppsuitobj(platFormWebApp.getWebappsuitobj());
        platFormAppAccessCount.setDefaultflag(platFormWebApp.getDefaultflag());
        platFormAppAccessCountService.updateAccessCount(platFormAppAccessCount, platFormWebApp.getId());
        //添加更新时间
        platFormWebApp.setUpdatetime(new Date());
        Integer result = platFormWebAppDao.editWebApp(platFormWebApp);
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
     * 消息平台验证
     *
     * @param webappkey
     * @param webappkey
     * @return
     */
    @Override
    public BaseResultDto verifyMessage(String webappkey, String messagetype, String webappip) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(webappip)) {
            if (StringUtils.isNotBlank(webappkey)) {
                PlatFormWebApp webApp = platFormWebAppDao.webApp(webappkey);
                HashMap<String, String> hashMap = new HashMap<>();
                if (webApp != null) {
                    String appip = webApp.getWebappip();
                    if (StringUtils.isNotBlank(appip)) {
                        String[] split = appip.split(";");
                        List<String> list = Arrays.asList(split);
                        boolean flag = list.contains(webappip);
                        if (flag) {
                            hashMap.put("webappsecret", webApp.getWebappsecret());
                            hashMap.put("messagetype", messagetype);
                            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                            baseResultDto.setData(hashMap);
                        } else {
                            baseResultDto.setCode(ResultMessage.FAILED_CODE);
                            baseResultDto.setMsg(ResultMessage.FAILED_APP_ACCESSTOKENIP);
                        }
                    } else {
                        baseResultDto.setCode(ResultMessage.FAILED_CODE);
                        baseResultDto.setMsg(ResultMessage.FAILED_WEBAPPIP_NULL_MESSAGE);
                    }
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_WEBAPPSECRET_NULL_MESSAGE);
                }
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_WEBAPPKEY_NULL_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 位置调整--移动端
     *
     * @param ids
     * @param user
     * @param requestSource
     * @param type
     * @return
     */
    @Override
    @AccountAopLog(description = "应用位置调整", menuname = "应用对外接口")
    public BaseResultDto orderAppCollect(String ids, String user, String type, String requestSource) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //先删除用户所对应的收藏应用
        String[] idArray = null;
        if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(type) && StringUtils.isNotBlank(requestSource)) {
            idArray = ids.split(",");
            Integer count = platFormWebAppDao.deleteAppCollect(idArray, user, requestSource);
            if (count > 0) {
                //在按ids顺序添加到收藏应用中
                for (int i = 0; i < idArray.length; i++) {
                    platFormWebAppDao.appUseCollect(UidUtils.getId(), user, idArray[i], type, new Date(), requestSource, Integer.toString(i + 1));
                }
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 应用统计--客户端
     *
     * @param sortname
     * @param webappdevuser
     * @return
     */
    @Override
    public BaseResultDto webappStatistics(String sortname, String webappdevuser, String pageNum, String pageSize) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(sortname) && StringUtils.isNotBlank(webappdevuser)) {
            List<LinkedHashMap<String, Object>> countList = platFormAppAccessCountDao.
                    webappStatistics(sortname, webappdevuser, pageNum, pageSize);
            Integer count = platFormAppAccessCountDao.webappStatisticsCount(sortname, webappdevuser, pageNum, pageSize);
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            baseResultDto.setData(countList);
            baseResultDto.setTotal(count);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 应用统计--后台管理
     *
     * @param sortname
     * @return
     */
    @Override
    public BaseResultDto webappStatisticsManager(String sortname, String pageNum, String pageSize) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(sortname)) {
            List<LinkedHashMap<String, Object>> countList = platFormAppAccessCountDao.
                    webappStatistics(sortname, null, pageNum, pageSize);
            Integer count = platFormAppAccessCountDao.webappStatisticsCount(sortname, null, pageNum, pageSize);
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            baseResultDto.setData(countList);
            baseResultDto.setTotal(count);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 应用类型统计--后台管理
     *
     * @return
     */
    @Override
    public BaseResultDto webapptypeList() {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<LinkedHashMap<String, Object>> apptypeList = platFormAppAccessCountDao.webapptypeList(null);
        if (apptypeList != null && apptypeList.size() > 0) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            baseResultDto.setData(apptypeList);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 应用类型统计--客户端
     *
     * @return
     */
    @Override
    public BaseResultDto webapptypeListCus(String webappdevuser) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(webappdevuser)) {
            List<LinkedHashMap<String, Object>> apptypeCusList = platFormAppAccessCountDao.webapptypeList(webappdevuser);
            if (apptypeCusList != null && apptypeCusList.size() > 0) {
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                baseResultDto.setData(apptypeCusList);
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 重新获取应用key
     *
     * @return
     */
    @Override
    public BaseResultDto resetSecret(PlatFormWebApp platFormWebApp) {
        BaseResultDto baseResultDto = new BaseResultDto();
        String appsecret = UidUtils.getId();
        if (StringUtils.isNotBlank(platFormWebApp.getId())) {
            platFormWebApp.setWebappsecret(appsecret);
            Integer result = platFormWebAppDao.editWebApp(platFormWebApp);
            if (result > 0) {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                baseResultDto.setData(appsecret);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 应用详情--显示应用接口
     *
     * @param webappid
     * @return
     */
    @Override
    public BaseResultDto queryApiTree(String webappid) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<TreeDto> apiTypeList = platFormApiDao.queryApiTree(webappid).stream().map(resultDto -> {
            TreeDto treeDto = new TreeDto();
            treeDto.setLabel(resultDto.getTypename());
            treeDto.setId(resultDto.getId());
            List<TreeDto> apiDtoList = platFormApiManagerDao.queryApiManagerTree(webappid, resultDto.getId()).stream().map(apiDto -> {
                TreeDto childDto = new TreeDto();
                childDto.setId(apiDto.getId());
                childDto.setLabel(apiDto.getApiname());
                return childDto;
            }).collect(Collectors.toList());
            treeDto.setChildren(apiDtoList);
            return treeDto;
        }).collect(Collectors.toList());
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(apiTypeList);
        return baseResultDto;

    }

    /**
     * 详情查看---适用学校
     *
     * @param appid
     * @return
     */
    @Override
    public BaseResultDto getSchoolTree(String appid) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<TreeDto> treeList = platFormWebSchoolDao.getSchoolTree(appid).stream().map(school -> {
            TreeDto treeDto = new TreeDto();
            treeDto.setId(school.getId());
            treeDto.setLabel(school.getXxmc());
            return treeDto;
        }).collect(Collectors.toList());
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        baseResultDto.setData(treeList);
        return baseResultDto;
    }

    /**
     * 删除app
     *
     * @param platFormWebApp
     * @return
     */
    @Override
    public BaseResultDto delWebApp(PlatFormWebApp platFormWebApp) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormWebAppDao.delWebApp(platFormWebApp);
        if (1 == result) {
            baseResultDto.setMsg(ResultMessage.SUCCESS_DELETE_MESSAGE);
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_DELETE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询应用列表
     *
     * @param platFormWebApp
     * @return baseResultDto
     */
    @Override
    public BaseResultDto webAppList(PlatFormWebApp platFormWebApp) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormWebApp> resultList = platFormWebAppDao.webAppList(platFormWebApp).stream().map(webApp -> {
            byte[] applogo = webApp.getWebapppicsmall();
            //转换成base64编码
            webApp.setWebapppicsmallbase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(applogo));
            return webApp;
        }).collect(Collectors.toList());
        Integer resultCount = platFormWebAppDao.webAppListCount(platFormWebApp);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 查询单个应用详情
     *
     * @param platFormWebApp
     * @return
     */
    @Override
    public BaseResultDto webDetailInfo(PlatFormWebApp platFormWebApp) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormWebApp> resultList = platFormWebAppDao.webAppList(platFormWebApp);
        if (resultList.size() > 0) {
            PlatFormWebApp resultApp = resultList.get(0);
            byte[] appsmalllogo = resultApp.getWebapppicsmall();
            //转换成base64编码小图标
            resultApp.setWebapppicsmallbase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(appsmalllogo));

            //注册表登记信息
            byte[] regetinfo = resultApp.getWebappregisterinfo();
            resultApp.setRegestinfobase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(regetinfo));

            //应用大图标
            byte[] applogo = resultApp.getWebapppic();
            resultApp.setWebapppicbase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(applogo));

            //应用大图标
            byte[] orcode = resultApp.getWebappinstallorcode();
            resultApp.setOrcodebase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(orcode));

            //查询校级应用对应的学校列表
            if (CommonEnum.APP_SCOPE.校级.getValue().equals(resultApp.getWebappscope())) {
                List<String> schoolist = platFormWebSchoolDao.getSchoolByWeb(platFormWebApp.getId());
                resultApp.setSchoolList(schoolist);
            }


            //查询应用附件表
            List<HashMap<String, String>> filepathList = new ArrayList<>();
            //如果主键不为空,则去查询附件
            if (StringUtils.isNotBlank(platFormWebApp.getId())) {
                List<PlatFormWepAppFile> fileList = platFormWebAppDao.queryFileList(platFormWebApp.getId());
                if (fileList != null && fileList.size() > 0) {
                    for (PlatFormWepAppFile webAppFile : fileList) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("filepath", appfilepath + webAppFile.getId());
                        hashMap.put("filename", webAppFile.getFilename());
                        hashMap.put("fileorder", webAppFile.getFileorder());
                        hashMap.put("id", webAppFile.getId());
                        filepathList.add(hashMap);
                    }
                }
                resultApp.setAppfilepath(filepathList);
            }

            List<String> apilist = platFormAppApiDao.getApiByApp(platFormWebApp.getId());
            resultApp.setApiList(apilist);

            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setData(resultApp);
        } else {
            baseResultDto.setData(new PlatFormWebApp());
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
        }
        return baseResultDto;
    }

    /**
     * 根据应用获取对应的接口与对应的学校
     *
     * @param platFormWebApp
     * @return
     */
    @Override
    public BaseResultDto getApiByApp(PlatFormWebApp platFormWebApp) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<String> apilist = platFormAppApiDao.getApiByApp(platFormWebApp.getId());
        List<String> schoolist = platFormWebSchoolDao.getSchoolByWeb(platFormWebApp.getId());
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("apilist", apilist);
        resultMap.put("schoolist", schoolist);
        baseResultDto.setData(resultMap);
        return baseResultDto;
    }

    /**
     * 教育云平台查询评论列表
     *
     * @param platFormAppComment
     * @return
     */
    @Override
    public BaseResultDto listComment(PlatFormAppComment platFormAppComment) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormAppComment> appCommentList = platFormWebAppDao.listComment(platFormAppComment);
        Integer count = platFormWebAppDao.listCommentCount(platFormAppComment);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(appCommentList);
        baseResultDto.setTotal(count);
        return baseResultDto;
    }

    /**
     * 教育云平台添加评论
     *
     * @param platFormAppComment
     * @return
     */
    @Override
    @Transactional
    @AccountAopLog(description = "用户评分", menuname = "应用对外接口")
    public BaseResultDto addComment(PlatFormAppComment platFormAppComment) {
        platFormAppComment.setId(UidUtils.getId());
        platFormAppComment.setCreatetime(new Date());
        float commentstart = Float.parseFloat(platFormAppComment.getCommentstart());
        //判断区别(好评,中评,差评)
        if (commentstart > 3.0 && commentstart <= 5.0) {
            platFormAppComment.setCommenttype(CommonEnum.APP_COMMENT.好评.getValue());
        } else if (commentstart > 1.0 && commentstart <= 3.0) {
            platFormAppComment.setCommenttype(CommonEnum.APP_COMMENT.中评.getValue());
        } else if (commentstart >= 0 && commentstart <= 1.0) {
            platFormAppComment.setCommenttype(CommonEnum.APP_COMMENT.差评.getValue());
        } else {
            platFormAppComment.setCommenttype(null);
        }
        Integer count = platFormWebAppDao.addComment(platFormAppComment);
        BaseResultDto baseResultDto = new BaseResultDto();
        if (1 == count) {
            //添加评论成功过后,更新评分表
            HashMap<String, String> commentMap = platFormWebAppDao.avgComment(platFormAppComment);
            //获取添加后评论的平均分
            String avgcommentstart = String.format("%.2f", commentMap.get("AVGCOMMENTSTART"));
            PlatFormAppAccessCount platFormAppAccessCount = new PlatFormAppAccessCount();
            platFormAppAccessCount.setAppstart(avgcommentstart);
            baseResultDto = platFormAppAccessCountService.updateAccessCount(platFormAppAccessCount, platFormAppComment.getAppid());
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询应用--移动端
     *
     * @param platFormWebApp
     * @param ids
     * @return
     */
    @Override
    public BaseResultDto listAppByKindHobby(PlatFormWebApp platFormWebApp, String ids) {
        BaseResultDto baseResultDto = new BaseResultDto();
        String[] idArray = null;
        if (StringUtils.isNotBlank(ids)) {
            idArray = ids.split(",");
            platFormWebApp.setIdArray(idArray);
        }
        if (StringUtils.isNotBlank(platFormWebApp.getWebappos()) && StringUtils.isNotBlank(platFormWebApp.getWebapptype())) {
            //add by qxk 17/11/01增加应用状态表示----只查出没有下架的应用
            platFormWebApp.setWebappstatus(CommonEnum.APP_STATUS.已下架.getValue());
            List<PlatFormWebApp> resultList = platFormWebAppDao.listAppByKindHobby(platFormWebApp).stream().map(webApp -> {
                byte[] applogo = webApp.getWebapppicsmall();
                //转换成base64编码
                webApp.setWebapppicsmallbase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(applogo));
                String appsuitobj = webApp.getAppsuitobj();
                if ("all".equals(appsuitobj)) {
                    webApp.setAppsuitobj("全部");
                } else if ("teacher".equals(appsuitobj)) {
                    webApp.setAppsuitobj("教师");
                } else if ("student".equals(appsuitobj)) {
                    webApp.setAppsuitobj("学生");
                } else {
                    webApp.setAppsuitobj("无");
                }
                return webApp;
            }).collect(Collectors.toList());
            Integer resultCount = platFormWebAppDao.listAppByKindHobbyCount(platFormWebApp);
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setData(resultList);
            baseResultDto.setTotal(resultCount);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 教育云平台收藏使用
     *
     * @param user
     * @param appid
     * @param type
     * @param requestSource
     * @return
     */
    @Override
    @Transactional
    @AccountAopLog(description = "收藏应用", menuname = "应用对外接口")
    public BaseResultDto appUseCollect(String user, String appid, String type, String requestSource) {
        String appsort = null;
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(type) && StringUtils.isNotBlank(appid) && StringUtils.isNotBlank(requestSource)) {
            List<PlatFormWebApp> webAppList = platFormWebAppDao.listAppCollect(user, type, appid, requestSource);
            if (webAppList != null && webAppList.size() > 0) {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_USER_COLLECT_MESSAGE);
            } else {
                Integer count = platFormWebAppDao.appUseCollect(UidUtils.getId(), user, appid, type, new Date(), requestSource, appsort);
                if (1 == count) {
                    //收藏成功后,更新收藏人数
                    baseResultDto = platFormAppAccessCountService.updateCollectCount(appid);
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
                }
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 获取收藏应用----教育云平台
     *
     * @param user
     * @param type
     * @param webappos
     * @return
     */
    @Override
    public BaseResultDto listAppCollect(String user, String type, String appid, String webappos) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(webappos)) {
            List<HashMap<String, Object>> webAppMap = platFormWebAppDao.listAppCollect(user, type, appid, webappos).stream().map(webApp -> {
                HashMap<String, Object> app = new HashMap<>();
                byte[] applogo = webApp.getWebapppicsmall();
                app.put("webapppicsmallbase", "data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(applogo));
                app.put("webappname", webApp.getWebappname());
                app.put("webappenname", webApp.getWebappenname());
                app.put("freeuse", webApp.getFreeuse());
                app.put("freestatus", webApp.getFreestatus());
                app.put("webappkind", webApp.getWebappkind());
                app.put("kindvalue", webApp.getKindvalue());
                app.put("webappwebsite", webApp.getWebappwebsite());
                app.put("webappstatus", webApp.getWebappstatus());
                app.put("webappshowtype", webApp.getWebappshowtype());
                app.put("webappinstallurl", webApp.getWebappinstallurl());
                app.put("webappos", webApp.getWebappos());
                app.put("id", webApp.getId());
                return app;
            }).collect(Collectors.toList());
            Integer count = platFormWebAppDao.listAppCollectCount(user, type, appid, webappos);
            if (webAppMap.size() > 0) {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                baseResultDto.setData(webAppMap);
                baseResultDto.setTotal(count);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 获取收藏应用----移动端
     *
     * @param user
     * @param type
     * @param webappos
     * @return
     */
    @Override
    public BaseResultDto listMobileAppCollect(String user, String type, String myApp, String webappos) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(webappos) && StringUtils.isNotBlank(myApp)) {
            List<HashMap<String, Object>> webAppMap = platFormWebAppDao.listAppCollect(user, type, null, webappos).stream().map(webApp -> {
                HashMap<String, Object> app = new HashMap<>();
                app.put("webapppicsmallbase", appMobilefilepath + webApp.getId());
                app.put("webappname", webApp.getWebappname());
                app.put("webappenname", webApp.getWebappenname());
                app.put("freeuse", webApp.getFreeuse());
                app.put("freestatus", webApp.getFreestatus());
                app.put("webappkind", webApp.getWebappkind());
                app.put("kindvalue", webApp.getKindvalue());
                app.put("webappwebsite", webApp.getWebappwebsite());
                app.put("webappstatus", webApp.getWebappstatus());
                app.put("webappshowtype", webApp.getWebappshowtype());
                app.put("webappinstallurl", webApp.getWebappinstallurl());
                app.put("webappos", webApp.getWebappos());
                app.put("id", webApp.getId());
                return app;
            }).collect(Collectors.toList());
            Integer count = platFormWebAppDao.listAppCollectCount(user, type, null, webappos);
            if (webAppMap.size() > 0) {
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setData(webAppMap);
                baseResultDto.setTotal(count);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询应用列表---H5页面
     *
     * @param platFormWebApp
     * @return
     */
    @Override
    public BaseResultDto listThirdAppCollect(PlatFormWebApp platFormWebApp) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(platFormWebApp.getUser()) && StringUtils.isNotBlank(platFormWebApp.getWebappos())) {
            List<HashMap<String, Object>> webAppList = platFormWebAppDao.listThirdAppCollect(platFormWebApp).stream().map(appMap -> {
                HashMap<String, Object> webHashMap = new HashMap<>();
                byte[] applogo = appMap.getWebapppicsmall();
                webHashMap.put("appLogo", "data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(applogo));
                webHashMap.put("id", appMap.getId());
                webHashMap.put("webappkind", appMap.getWebappkind());
                webHashMap.put("appName", appMap.getWebappname());
                webHashMap.put("added", appMap.getAdded());
                return webHashMap;
            }).collect(Collectors.toList());
            Integer count = platFormWebAppDao.listThirdAppCollectCount(platFormWebApp);
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            baseResultDto.setData(webAppList);
            baseResultDto.setTotal(count);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 教育云平台批量取消收藏应用
     *
     * @param ids
     * @param requestSource
     * @return
     */
    @Override
    @Transactional
    @AccountAopLog(description = "取消应用", menuname = "应用对外接口")
    public BaseResultDto deleteAppCollect(String ids, String user, String requestSource) {
        BaseResultDto baseResultDto = new BaseResultDto();
        String[] idArray = null;
        if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(requestSource)) {
            idArray = ids.split(",");
            Integer count = platFormWebAppDao.deleteAppCollect(idArray, user, requestSource);
            if (count > 0) {
                //取消收藏应用,更新收藏人数
                for (int i = 0; i < idArray.length; i++) {
                    platFormAppAccessCountService.updateCollectCountCut(idArray[i]);
                }
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                baseResultDto.setTotal(count);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_PARAMS_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 根据id以流的形式查看图片
     *
     * @param id
     * @param response
     */
    @Override
    public void getFileList(String id, HttpServletResponse response) {
        List<PlatFormWepAppFile> fileList = platFormWebAppDao.queryFile(id);
        if (fileList != null && fileList.size() > 0) {
            for (PlatFormWepAppFile file : fileList) {
                sambaService.getSambaFileOutOpen(file.getFilepath(), response);
            }
        }
    }

    /**
     * 根据id以流的形式查看应用图标
     *
     * @param id
     * @param response
     */
    @Override
    public void getAppFileList(String id, HttpServletResponse response) {
        PlatFormWebApp platFormWebApp = new PlatFormWebApp();
        platFormWebApp.setId(id);
        List<PlatFormWebApp> fileList = platFormWebAppDao.webAppList(platFormWebApp);
        if (fileList != null && fileList.size() > 0) {
            for (PlatFormWebApp file : fileList) {
                sambaService.showAppImage(file.getWebapppicsmall(), response);
            }
        }
    }

    @Override
    public void getFileByPath(String path, String filename, HttpServletResponse response) {
        sambaService.getSambaFileOut(path, filename, response);
    }

    /**
     * 根据应用类型获取应用列表
     *
     * @param platFormWebApp
     * @return
     */
    @Override
    public BaseResultDto listAppByKind(PlatFormWebApp platFormWebApp) {
        BaseResultDto baseResultDto = new BaseResultDto();
        //add by qxk 17/11/01增加应用状态表示----只查出没有下架的应用
        platFormWebApp.setWebappstatus(CommonEnum.APP_STATUS.已下架.getValue());
        //如果主键不为空,则去查询附件
        List<HashMap<String, String>> filepathList = new ArrayList<>();
        if (StringUtils.isNotBlank(platFormWebApp.getId())) {
            List<PlatFormWepAppFile> fileList = platFormWebAppDao.queryFileList(platFormWebApp.getId());
            if (fileList != null && fileList.size() > 0) {
                for (PlatFormWepAppFile webAppFile : fileList) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("filepath", appfilepath + webAppFile.getId());
                    filepathList.add(hashMap);
                }
            }
        }
        //操作手册
        List<HashMap<String, String>> appOperationList = new ArrayList<>();

        List<PlatFormWebApp> resultList = platFormWebAppDao.listAppByKind(platFormWebApp).stream().map(webApp -> {
            byte[] applogo = webApp.getWebapppicsmall();
            //转换成base64编码
            webApp.setWebapppicsmallbase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(applogo));
            webApp.setAppfilepath(filepathList);
            String appsuitobj = webApp.getAppsuitobj();
            if ("all".equals(appsuitobj)) {
                webApp.setAppsuitobj("全部");
            } else if ("teacher".equals(appsuitobj)) {
                webApp.setAppsuitobj("教师");
            } else if ("student".equals(appsuitobj)) {
                webApp.setAppsuitobj("学生");
            } else {
                webApp.setAppsuitobj("无");
            }
            String appOperationUrl = webApp.getAppOperationUrl();
            String appOperationName = webApp.getAppOperationName();
            if (StringUtils.isNotBlank(appOperationName)) {
                HashMap<String, String> hashMap = new HashMap<>();
                String apppath = appoperation.replace("appOperationUrl", appOperationUrl).replace("appOperationName", appOperationName);
                hashMap.put("appOperationUrl", apppath);
                hashMap.put("appOperationName", appOperationName);
                appOperationList.add(hashMap);
            }
            webApp.setAppOperationList(appOperationList);
            return webApp;
        }).collect(Collectors.toList());
        Integer resultCount = platFormWebAppDao.listAppByKindCount(platFormWebApp);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }
}
