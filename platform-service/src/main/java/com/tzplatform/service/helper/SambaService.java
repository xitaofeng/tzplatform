package com.tzplatform.service.helper;

import com.tzplatform.entity.common.BaseResultDto;

import javax.servlet.http.HttpServletResponse;

public interface SambaService {

    BaseResultDto uploadFile(String filePath, String sourcePath);

    BaseResultDto getSambaFile(String filePath, String targetPath);

    BaseResultDto getSambaFileOut(String filePath,String filename, HttpServletResponse response);

    BaseResultDto getSambaFileOutOpen(String filePath, HttpServletResponse response);

    BaseResultDto showeImage(String remoteUrl);

    void showAppImage(byte[] webapppicsmall, HttpServletResponse response);
}
