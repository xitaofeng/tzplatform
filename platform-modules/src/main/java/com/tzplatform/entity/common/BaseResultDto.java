package com.tzplatform.entity.common;

import java.io.Serializable;

/**
 * json返回对象封装
 *
 * @author leijie
 */
public class BaseResultDto implements Serializable {

    private static final long serialVersionUID = 2888453337123968902L;

    private String msg;// 返回信息
    private String code;// 返回码
    private Object data;// 对象
    private Integer total;//数据总数

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BaseResultDto() {
    }

    public BaseResultDto(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public BaseResultDto(String msg, String code, Object data, Integer total) {
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.total = total;
    }


}
