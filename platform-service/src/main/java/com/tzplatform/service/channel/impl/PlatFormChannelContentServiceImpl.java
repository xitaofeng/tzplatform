package com.tzplatform.service.channel.impl;

import com.tzplatform.dao.channel.PlatFormChannelContentDao;
import com.tzplatform.entity.channel.PlatFormChannelContent;
import com.tzplatform.entity.channel.PlatFormChannelFile;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.service.channel.PlatFormChannelContentService;
import com.tzplatform.service.helper.SambaService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.ByteObjUtils;
import com.tzplatform.utils.common.CommonUtils;
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

@Service(value = "platFormChannelContentService")
public class PlatFormChannelContentServiceImpl implements PlatFormChannelContentService {

    @Resource
    private PlatFormChannelContentDao platFormChannelContentDao;

    @Value("${tmp.file.path}")
    private String tmppath;

    @Value("${platform.channel.filepath}")
    private String channelfilepath;

    @Resource
    private SambaService sambaService;

    /**
     * 添加栏目内容
     *
     * @param platFormChannelContent
     * @param channelFileList
     * @return
     */
    @Override
    @AopLog(description = "添加栏目内容", menuname = "栏目管理")
    public BaseResultDto addContent(PlatFormChannelContent platFormChannelContent, CommonsMultipartFile[] channelFileList) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormChannelContent.setId(UidUtils.getId());
        platFormChannelContent.setCreatetime(new Date());
        Integer result = platFormChannelContentDao.addContent(platFormChannelContent);
        if (1 == result) {
            //添加成功后提交附件
            if (channelFileList != null && channelFileList.length > 0) {
                Integer count = addfileList(platFormChannelContent, channelFileList);
                if (count > 0) {
                    baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                    baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
                }
            } else {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    private Integer addfileList(PlatFormChannelContent platFormChannelContent, CommonsMultipartFile[] channelFileList) {
        Integer count = 0;
        for (int i = 0; i < channelFileList.length; i++) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(channelFileList[i], tmppath);

            //文件服务器上传
            String realpath = resultMap.get("realpath").toString();
            String serveraddr = resultMap.get("filepath").toString();
            sambaService.uploadFile(realpath, serveraddr);

            File file = (File) resultMap.get("file");
            String filepath = (String) resultMap.get("filepath");
            PlatFormChannelFile platFormChannelFile = new PlatFormChannelFile();
            platFormChannelFile.setId(UidUtils.getId());
            platFormChannelFile.setChannelid(platFormChannelContent.getId());
            platFormChannelFile.setFilename(file.getName());
            platFormChannelFile.setFileoldname(channelFileList[i].getFileItem().getName());
            platFormChannelFile.setFilepath(filepath);
            platFormChannelFile.setCreatetime(new Date());
            count = platFormChannelContentDao.channelUploadFile(platFormChannelFile);
        }
        return count;
    }


    /**
     * 修改栏目内容
     *
     * @param platFormChannelContent
     * @param channelFileList
     * @param deleteIdList           @return
     */
    @Override
    @Transactional
    @AopLog(description = "修改栏目内容", menuname = "栏目管理")
    public BaseResultDto editContent(PlatFormChannelContent platFormChannelContent, CommonsMultipartFile[] channelFileList, String[] deleteIdList) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormChannelContent.setUpdatetime(new Date());
        Integer result = platFormChannelContentDao.editContent(platFormChannelContent);
        if (1 == result) {
            //添加成功后提交附件
            if (channelFileList != null && channelFileList.length > 0) {
                addfileList(platFormChannelContent, channelFileList);
            }
            //删除除去的附件只是数据库删除,附近并没有删除
            if (deleteIdList != null && deleteIdList.length > 0) {
                platFormChannelContentDao.deleteFileList(deleteIdList);
            }
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_UPDATE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_UPDATE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 删除栏目内容
     *
     * @param platFormChannelContent
     * @return
     */
    @Override
    @AopLog(description = "删除栏目内容", menuname = "栏目管理")
    public BaseResultDto delContent(PlatFormChannelContent platFormChannelContent) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormChannelContentDao.delContent(platFormChannelContent);
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
     * 栏目列表
     *
     * @param platFormChannelContent
     * @return
     */
    @Override
    public BaseResultDto contentList(PlatFormChannelContent platFormChannelContent) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<HashMap<String, String>> newList = new ArrayList<>();
        if (StringUtils.isNotBlank(platFormChannelContent.getId())) {
            List<PlatFormChannelFile> fileList = platFormChannelContentDao.getContentFileList(platFormChannelContent.getId());
            if (fileList != null && fileList.size() > 0) {
                for (PlatFormChannelFile file : fileList) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("filepath", channelfilepath + file.getId());
                    hashMap.put("filename", file.getFileoldname());
                    newList.add(hashMap);
                }
            }
        }
        List<PlatFormChannelContent> resultList = platFormChannelContentDao.contentList(platFormChannelContent).stream().map(channel -> {
            channel.setFilelist(newList);
            return channel;
        }).collect(Collectors.toList());
        Integer resultCount = platFormChannelContentDao.contentListCount(platFormChannelContent);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 获取栏目附件
     *
     * @param id
     * @return
     */
    @Override
    public BaseResultDto getContentFileList(String id) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormChannelFile> fileList = platFormChannelContentDao.getContentFileList(id);
        List<HashMap<String, String>> newList = new ArrayList<>();
        if (fileList != null && fileList.size() > 0) {
            for (PlatFormChannelFile file : fileList) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id",file.getId());
                hashMap.put("filename", file.getFileoldname());
                hashMap.put("filepath", channelfilepath + file.getId());
                newList.add(hashMap);
            }
        }
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        baseResultDto.setData(newList);
        return baseResultDto;
    }

    /**
     * 根据id下载文件
     *
     * @param id
     * @param response
     */
    @Override
    public void getFileList(String id, HttpServletResponse response) {
        List<PlatFormChannelFile> fileList = platFormChannelContentDao.getContentFile(id);
        if (fileList != null && fileList.size() > 0) {
            for (PlatFormChannelFile file : fileList) {
                String filename = file.getFileoldname();
                sambaService.getSambaFileOut(file.getFilepath(), filename, response);
            }
        }
    }
}
