package com.tzplatform.web.channel;

import com.tzplatform.entity.channel.PlatFormChannelContent;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.channel.PlatFormChannelContentService;
import com.tzplatform.service.tokenfilter.AccessToken;
import com.tzplatform.utils.common.CommonUtils;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/platformChannelContent")
public class PlatFormChannelContentController {

    @Resource
    private PlatFormChannelContentService platFormChannelContentService;

    /**
     * 添加栏目内容
     *
     * @param platFormChannelContent
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/addChannelContent", produces = "application/json; charset=utf-8")
    public BaseResultDto addChannelContent(PlatFormChannelContent platFormChannelContent, @RequestParam(value = "channelFileList", required = false) CommonsMultipartFile[] channelFileList) {
        return platFormChannelContentService.addContent(platFormChannelContent, channelFileList);
    }

    /**
     * 修改栏目内容
     *
     * @param platFormChannelContent
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/editChannelContent", produces = "application/json; charset=utf-8")
    public BaseResultDto editChannelContent(PlatFormChannelContent platFormChannelContent, @RequestParam(value = "channelFileList", required = false) CommonsMultipartFile[] channelFileList, @RequestParam(value = "deleteIdList", required = false) String[] deleteIdList) {
        return platFormChannelContentService.editContent(platFormChannelContent, channelFileList, deleteIdList);
    }

    /**
     * 删除栏目内容
     *
     * @param platFormChannelContent
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/delChannelContent", produces = "application/json; charset=utf-8")
    public BaseResultDto delChannelContent(PlatFormChannelContent platFormChannelContent) {
        return platFormChannelContentService.delContent(platFormChannelContent);
    }

    /**
     * 查询栏目内容
     *
     * @param platFormChannelContent
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/channelContentList", produces = "application/json; charset=utf-8")
    public BaseResultDto channelContentList(PlatFormChannelContent platFormChannelContent) {
        return platFormChannelContentService.contentList(platFormChannelContent);
    }

    /**
     * 点击栏目修改时----获取附件名称
     * @param id
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/getContentFileList", produces = "application/json;charset=utf-8")
    public BaseResultDto getContentFileList(String id) {
        return platFormChannelContentService.getContentFileList(id);
    }
}
