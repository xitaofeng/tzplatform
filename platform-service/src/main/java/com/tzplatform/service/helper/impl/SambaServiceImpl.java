package com.tzplatform.service.helper.impl;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.service.helper.SambaService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.smab.SambaHelper;
import com.tzplatform.utils.smab.SambaUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service(value = "sambaService")
public class SambaServiceImpl implements SambaService {

    @Value("${samba.username}")
    private String sambausername;

    @Value("${samba.password}")
    private String sambapassword;

    @Value("${samba.host}")
    private String sambahost;

    @Value("${samba.target}")
    private String sambatarget;


    /**
     * 上传文件到服务器
     *
     * @param filePath
     * @param sourcePath
     * @return
     */
    @Override
    @AopLog(description = "上传文件到服务器", menuname = "文件上传")
    public BaseResultDto uploadFile(String filePath, String sourcePath) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(filePath) && StringUtils.isNotBlank(sourcePath)) {
            try {
                SambaUtils.uploadFile(sambausername, sambapassword, sambahost, filePath, sambatarget + sourcePath);
                baseResultDto.setMsg(ResultMessage.SUCCESS_CODE);
                baseResultDto.setCode(ResultMessage.SUCCESS_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
            }
        } else {
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
            baseResultDto.setCode(ResultMessage.FAILED_CODE);

        }
        return baseResultDto;
    }

    @Override
    public BaseResultDto getSambaFile(String filePath,String targetPath) {
        try {
            SambaUtils.download(sambausername, sambapassword, sambahost,sambatarget+filePath,targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 点击下载文件服务器附件
     * @param filePath
     * @param filename
     * @param response
     * @return
     */
    @Override
    public BaseResultDto getSambaFileOut(String filePath,String filename,HttpServletResponse response) {
        try {
            SambaHelper.downloadFileOut(sambausername, sambapassword, sambahost,sambatarget+filePath,filename,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接查看文件服务器图片
     * @param filePath
     * @param response
     * @return
     */
    @Override
    public BaseResultDto getSambaFileOutOpen(String filePath, HttpServletResponse response) {
        try {
            SambaHelper.downloadFileOutOpen(sambausername, sambapassword, sambahost,sambatarget+filePath,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param remoteUrl
     * @return
     */
    @Override
    public BaseResultDto showeImage(String remoteUrl){
        BaseResultDto baseResultDto = new BaseResultDto();
        try {
            SambaUtils.showImage(sambausername, sambapassword, sambahost,sambatarget + remoteUrl);
            baseResultDto.setMsg(ResultMessage.SUCCESS_CODE);
            baseResultDto.setCode(ResultMessage.SUCCESS_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
        }
        return baseResultDto;
    }

    /**
     * 查看应用图标
     * @param webapppicsmall
     * @param response
     */
    @Override
    public void showAppImage(byte[] webapppicsmall, HttpServletResponse response) {
        try {
            SambaUtils.showAppImage(webapppicsmall,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
