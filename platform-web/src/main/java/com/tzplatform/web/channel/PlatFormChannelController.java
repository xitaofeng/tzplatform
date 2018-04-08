package com.tzplatform.web.channel;

import com.tzplatform.entity.channel.PlatFormChannel;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.channel.PlatFormChannelService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformChannel")
public class PlatFormChannelController {

    @Resource
    private PlatFormChannelService platFormChannelService;


    /**
     * 栏目列表
     *
     * @param platFormChannel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/channelList", produces = "application/json; charset=utf-8")
    public BaseResultDto channelList(PlatFormChannel platFormChannel) {
        return platFormChannelService.channelList(platFormChannel);
    }

    /**
     * 栏目树
     *
     * @param platFormChannel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/channelTree", produces = "application/json; charset=utf-8")
    public BaseResultDto channelTree(PlatFormChannel platFormChannel) {
        return platFormChannelService.channelTree(platFormChannel);
    }

    /**
     * 一级栏目
     *
     * @param platFormChannel
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/channelRoot", produces = "application/json; charset=utf-8")
    public BaseResultDto channelRoot(PlatFormChannel platFormChannel) {
        return platFormChannelService.channelRootList(platFormChannel);
    }


}
