package com.tzplatform.dao.channel;

import com.tzplatform.entity.channel.PlatFormChannelContent;
import com.tzplatform.entity.channel.PlatFormChannelFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlatFormChannelContentDao {

    Integer addContent(PlatFormChannelContent platFormChannelContent);

    Integer editContent(PlatFormChannelContent platFormChannelContent);

    Integer delContent(PlatFormChannelContent platFormChannelContent);

    List<PlatFormChannelContent> contentList(PlatFormChannelContent platFormChannelContent);

    Integer contentListCount(PlatFormChannelContent platFormChannelContent);

    Integer channelUploadFile(PlatFormChannelFile platFormChannelFile);

    List<PlatFormChannelFile> getContentFileList(@Param(value = "channelid") String id);

    void deleteFileList(@Param(value = "idArray") String[] idArray);

    List<PlatFormChannelFile> getContentFile(@Param(value = "id")String id);
}
