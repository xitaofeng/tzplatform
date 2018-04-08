package com.tzplatform.web.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.mobileplatform.NoticeModel;
import com.tzplatform.service.helper.SambaService;
import com.tzplatform.service.mobileplatform.impl.CommonServiceImpl;
import com.tzplatform.utils.common.CommonUtils;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * 公共控制层
 *
 */
@RestController
@RequestMapping(value = "/common")
public class CommonController {

    @Value("${attachment.file.path}")
    private String webPath;// 通知公告附件下载路径
    @Value("${lostFound.file.path}")
    private String baseImagePath;//失物招领 跟图片路径
    @Value("${campusScenery.file.path}")
    private String campusSceneryPath;//上传图片路径
    @Value("${poater.file.path}")
    private String posterPath; //海报
    @Resource
    private CommonServiceImpl commonService;
    @Resource
    private SambaService sambaService;

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

    /**
     *  图片显示
     * @param imageUrl
     * @param type
     * @param request
     * @param response
     *
     * @return
     */
    @RequestMapping(value = "/showImage", produces = "application/json;charset=utf-8")
    public BaseResultDto showImage(String imageUrl,String type, HttpServletRequest request, HttpServletResponse response)throws Exception{
       /* String basePath="";
        if(CommonEnum.IMAGT_TYPE.失物招领.getValue().equals(type)){
            basePath =baseImagePath;
        }else if(CommonEnum.IMAGT_TYPE.校风光.getValue().equals(type)){
            basePath = campusSceneryPath;
        }else if(CommonEnum.IMAGT_TYPE.海报.getValue().equals(type)){
            basePath = posterPath;
        }*/
            sambaService.showeImage(imageUrl);
       // CommonUtils.showImage(request,response,basePath,imageUrl);

        return null;
    }

    /**
     *用户角色
     * @return
     */
    @RequestMapping(value = "/showUserRole", produces = "application/json;charset=utf-8")
    public BaseResultDto showUserRole(){
        BaseResultDto baseResultDto = new BaseResultDto();
         String accountId= CommonUtils.getAccount();
         String roleColde=commonService.checkUserRole(accountId,"");
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(roleColde);
        return baseResultDto;
    }

    /**
     * 校验图片格式空方法
     */
    @RequestMapping(value = "/checkImage", produces = "application/json;charset=utf-8")
    public BaseResultDto checkImage(){
        BaseResultDto baseResultDto = new BaseResultDto();
        baseResultDto.setMsg(commonService.checkImage());
        return baseResultDto;
    }
}
