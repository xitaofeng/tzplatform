package com.tzplatform.web.channel;

import com.tzplatform.entity.channel.PlatFormChannelContent;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.channel.PlatFormChannelContentService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformChannelContent")
public class PlatFormChannelContentController {

    @Resource
    private PlatFormChannelContentService platFormChannelContentService;

    /**
     * 查询栏目内容
     *
     * @param platFormChannelContent
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/channelContentList", produces = "application/json; charset=utf-8")
    public BaseResultDto channelContentList(PlatFormChannelContent platFormChannelContent) {
        return platFormChannelContentService.contentList(platFormChannelContent);
    }

    /**
     * 获取附件
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/getContentFileList", produces = "application/json;charset=utf-8")
    public BaseResultDto getContentFileList(String id) {
        return platFormChannelContentService.getContentFileList(id);
    }

}
