package com.tzplatform.entity.system;

import java.io.Serializable;

/**
 * 短信结果接收对象
 */
public class SmsEntity implements Serializable {

    private static final long serialVersionUID = 4653938052877450934L;

    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
