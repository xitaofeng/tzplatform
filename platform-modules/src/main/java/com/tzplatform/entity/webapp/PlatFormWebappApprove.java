package com.tzplatform.entity.webapp;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用审批表
 *
 * @author leijie
 */
public class PlatFormWebappApprove extends PageDto implements Serializable {

    private static final long serialVersionUID = 9004459964135490788L;

    private String id;//主键
    private String appid;//应用id
    private String approvestatus;//应用审批状态
    private String approveadvice;//审批意见
    private String approveuser;//审批人
    private Date approvetime;//审批时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getApprovestatus() {
        return approvestatus;
    }

    public void setApprovestatus(String approvestatus) {
        this.approvestatus = approvestatus;
    }

    public String getApproveadvice() {
        return approveadvice;
    }

    public void setApproveadvice(String approveadvice) {
        this.approveadvice = approveadvice;
    }

    public String getApproveuser() {
        return approveuser;
    }

    public void setApproveuser(String approveuser) {
        this.approveuser = approveuser;
    }

    public Date getApprovetime() {
        return approvetime;
    }

    public void setApprovetime(Date approvetime) {
        this.approvetime = approvetime;
    }
}
