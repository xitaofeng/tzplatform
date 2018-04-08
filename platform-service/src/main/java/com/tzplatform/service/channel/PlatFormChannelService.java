package com.tzplatform.service.channel;

import com.tzplatform.entity.channel.PlatFormChannel;
import com.tzplatform.entity.common.BaseResultDto;

public interface PlatFormChannelService {

    BaseResultDto addChannel(PlatFormChannel platFormChannel);

    BaseResultDto editChannel(PlatFormChannel platFormChannel);

    BaseResultDto delChannel(PlatFormChannel platFormChannel);

    BaseResultDto channelList(PlatFormChannel platFormChannel);

    BaseResultDto channelRootList(PlatFormChannel platFormChannel);

    BaseResultDto channelTree(PlatFormChannel platFormChannel);

}
