package com.tzplatform.entity.api;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;

/**
 * 接口类型
 *
 * @author leijie
 */
public class PlatFormApiType extends PageDto implements Serializable {

    private static final long serialVersionUID = 2683278329710590301L;

    private String id;//主键
    private String typename;//类型名称
    private String typecode;//类型代码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }
}
