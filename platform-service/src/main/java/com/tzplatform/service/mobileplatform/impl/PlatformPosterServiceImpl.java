package com.tzplatform.service.mobileplatform.impl;

import com.tzplatform.dao.mobileplatform.PlatFormCommonDao;
import com.tzplatform.dao.mobileplatform.PlatformPosterDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.mobileplatform.Poster;
import com.tzplatform.service.helper.SambaService;
import com.tzplatform.service.mobileplatform.PlatformPosterService;
import com.tzplatform.service.tokenfilter.AccessToken;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.ByteObjUtils;
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
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "platformPosterService")
public class PlatformPosterServiceImpl implements PlatformPosterService {

    @Resource
    private PlatformPosterDao mobilePlatformPosterDao;
    @Resource
    private PlatFormCommonDao platFormCommonDao;
    @Resource
    private CommonServiceImpl commonService;
    @Resource
    private SambaService sambaService;
    @Value("${poater.file.path}")
    private String posterPath;
    @Value("${image.path}")
    private String imagePath;

    /**
     * 新增海报
     * @param poster
     * @param posterImage
     * @return
     */
    @Transactional
    @AopLog(description = "添加海报", menuname = "海报管理")
    public BaseResultDto addPoster(Poster poster, CommonsMultipartFile[] posterImage){
        BaseResultDto baseResultDto = new BaseResultDto();
        //获取当前登录账号
        String accountId = CommonUtils.getAccount();
        //用户角色 校管理  区管理
        String roleCode = commonService.checkUserRole(accountId,"");
        if(roleCode.equals(CommonEnum.ROLE_CODE.校管理员.getValue())){
            //如果是校管理 这设置学校标识
            String schoolId = commonService.getSchoolId(accountId);
            poster.setSchoolId(schoolId);
        }
        ///上传图片处理
        if(posterImage!=null) {
            String imagePath="";//附件名称
            for (int i = 0; i < posterImage.length; i++) {
                // 图片批量上传，如果为空则不插入数据
                if (!posterImage[i].isEmpty()) {
                    Map<String, Object> result = CommonUtils.upLoadFiles(posterImage[i], posterPath,CommonEnum.IMAGT_TYPE.海报.getValue());
                    imagePath = String.valueOf(result.get("filepath"));
                    //文件服务器上传
                    String realpath = result.get("realpath").toString();
                    String serveraddr = result.get("filepath").toString();
                    sambaService.uploadFile(realpath, serveraddr);
                    //设置应用主键
                    poster.setId(UidUtils.getId());
                    poster.setImagePath(imagePath);
                    if(CommonEnum.MOBILE_STATUS.上架.getValue().equals(poster.getStatus())){
                        poster.setUpdateTime(new Date());
                    }
                    poster.setCreateTime(new Date());
                    mobilePlatformPosterDao.addPoster(poster);
                }
            }

        }
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);

        return baseResultDto;
    }

    /**
     * 海报列表 &  查看详情
     * @param poster
     * @return
     */
    @Override
    public BaseResultDto listPoster(Poster poster,HttpServletRequest request) {
            BaseResultDto baseResultDto = new BaseResultDto();
            //获取当前登录账号
            String accountId = CommonUtils.getAccount();
            //移动端请求
         Integer resultCount=0;
//        String requestSource = request.getHeader(CommonEnum.HEAD_PARAM.REQUESTSOURCE.getValue());
        String requestSource =poster.getRequestSource();
        if(("app").equals(requestSource)){
                poster.setStatus(CommonEnum.MOBILE_STATUS.上架.getValue());
                //查询该学校海报
                String schoolId = commonService.getSchoolId(accountId);
                poster.setSchoolId(schoolId);
                //判断该学校是否有自己的海报
                 resultCount = mobilePlatformPosterDao.listPosterCount(poster);
                //未设置海报，查询区管理是否设置校级海报
                if(resultCount==0){
                    poster.setRange(CommonEnum.APP_SCOPE.校级.getValue());
                    resultCount = mobilePlatformPosterDao.listPosterCount(poster);
                    //未设置 则，查询区级海报
                    if(resultCount==0){
                        poster.setRange(CommonEnum.APP_SCOPE.区级.getValue());
                    }
                }
            }else{
                //用户角色  校管理, 区管理
               String roleCode = commonService.checkUserRole(accountId,requestSource);
               if(roleCode.equals(CommonEnum.ROLE_CODE.校管理员.getValue())){
                    //如果是校管理 这设置学校标识
                    String schoolId = commonService.getSchoolId(accountId);
                    poster.setSchoolId(schoolId);
                }
            }
            List<Poster> resultList = mobilePlatformPosterDao.listPoster(poster).stream().map(pos -> {
                 String image = pos.getImagePath();
                 pos.setImagePath(imagePath+image);
                 return pos;
            }).collect(Collectors.toList());
            resultCount = mobilePlatformPosterDao.listPosterCount(poster);
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setData(resultList);
            baseResultDto.setTotal(resultCount);
            return baseResultDto;
    }

    /**
     * 编辑
     * @param poster
     * @return
     */
    @Override
    @AopLog(description = "修改海报", menuname = "海报管理")
    public BaseResultDto updatePoster(Poster poster, CommonsMultipartFile[] posterImage) {
        BaseResultDto baseResultDto = new BaseResultDto();
        ///上传图片处理
        if(posterImage!=null) {
            String imagePath="";//附件名称
            for (int i = 0; i < posterImage.length; i++) {
                // 图片批量上传，如果为空则不插入数据
                if (!posterImage[i].isEmpty()) {
                    Map<String, Object> result = CommonUtils.upLoadFiles(posterImage[i], posterPath,CommonEnum.IMAGT_TYPE.海报.getValue());
                    imagePath = String.valueOf(result.get("filepath"));
                    //文件服务器上传
                    String realpath = result.get("realpath").toString();
                    String serveraddr = result.get("filepath").toString();
                    sambaService.uploadFile(realpath, serveraddr);
                    poster.setImagePath(imagePath);
                    if(CommonEnum.MOBILE_STATUS.上架.getValue().equals(poster.getStatus())){
                        poster.setUpdateTime(new Date());
                    }
//                    mobilePlatformPosterDao.updatePoster(poster);
                }
            }

        }
        if(CommonEnum.MOBILE_STATUS.上架.getValue().equals(poster.getStatus())){
            poster.setUpdateTime(new Date());
        }
        int resultCode =mobilePlatformPosterDao.updatePoster(poster);
        if (1 == resultCode) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    /**|
     * 删除
     * @param poster
     * @return
     */
    @Override
    @AopLog(description = "删除海报", menuname = "海报管理")
    public BaseResultDto deletePoster(Poster poster) {
        BaseResultDto baseResultDto = new BaseResultDto();
        int resultCode =mobilePlatformPosterDao.deletePoster(poster);
        if (1 == resultCode) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 海报 上架 下架
     * @param poster
     * @return
     */
    @Override
    @AopLog(description = "海报状态修改", menuname = "海报管理")
    public BaseResultDto updateStatus(Poster poster) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if(CommonEnum.MOBILE_STATUS.上架.getValue().equals(poster.getStatus())){
            poster.setUpdateTime(new Date());
        }
        int resultCode =mobilePlatformPosterDao.updateStatus(poster);
        if (1 == resultCode) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

}
