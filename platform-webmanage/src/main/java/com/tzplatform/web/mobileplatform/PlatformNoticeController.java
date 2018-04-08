package com.tzplatform.web.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.mobileplatform.NoticeModel;
import com.tzplatform.service.mobileplatform.PlatformNoticeService;
import com.tzplatform.utils.common.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通知公告
 */
@RestController
@RequestMapping(value = "/platformMobileNotice")
public class PlatformNoticeController {

    @Resource
    private PlatformNoticeService platformNoticeService;

    @Value("${attachment.file.path}")
    private String webPath;
    /**
     * 新增 通知公告
     * @param noticeModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/addNotice", produces = "application/json;charset=utf-8")
    public BaseResultDto addNotice(NoticeModel noticeModel,@RequestParam(value = "attachments", required = false)CommonsMultipartFile[] attachments){
        return platformNoticeService.addNotice(noticeModel,attachments);
    }
    /**
     * 通知公告 详情
     * @param noticeModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/noticeDetail", produces = "application/json;charset=utf-8")
    public BaseResultDto noticeDetail(NoticeModel noticeModel){
        return platformNoticeService.noticeDetail(noticeModel);
    }

    /**
     * 通知公告 修改
     * @param noticeModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/noticeUpdate", produces = "application/json;charset=utf-8")
    public BaseResultDto noticeUpdate(NoticeModel noticeModel){
        return platformNoticeService.noticeUpdate(noticeModel);
    }
    /**
     * 通知公告 发布或取消
     * @param noticeModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/release", produces = "application/json;charset=utf-8")
    public BaseResultDto release(NoticeModel noticeModel){
        return platformNoticeService.release(noticeModel);
    }

    /**
     * 通知公告列表
     * @param noticeModel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/noticeList", produces = "application/json;charset=utf-8")
    public BaseResultDto noticeList(NoticeModel noticeModel){
        return platformNoticeService.noticeList(noticeModel);
    }

    /**
     * 附件下载
     * @param noticeModel
     * @return
     */
    @RequestMapping(value = "/download", produces = "application/json;charset=utf-8")
    public BaseResultDto download(NoticeModel noticeModel,HttpServletRequest request,HttpServletResponse response) throws Exception {
          String downPath= webPath+"/"+noticeModel.getAttachmentPath();
          String fileName= noticeModel.getAttachment();
          CommonUtils.download(request,response,downPath,fileName);
          return  null;
    }

}
