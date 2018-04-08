package com.tzplatform.dao.system;

import com.tzplatform.entity.api.PlatFormNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlatFormNoticeDao {
    Integer updateNotice(PlatFormNotice platFormNotice);

    Integer queryCount(PlatFormNotice platFormNotice);

    List<PlatFormNotice> queryNotice(PlatFormNotice platFormNotice);

    Integer deleteNotice(@Param(value = "id") String id);

    Integer addNotice(PlatFormNotice platFormNotice);
}
