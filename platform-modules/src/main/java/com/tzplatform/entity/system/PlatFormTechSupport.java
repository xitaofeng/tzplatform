package com.tzplatform.entity.system;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台问题对象
 * @author leijie
 */
public class PlatFormTechSupport extends PageDto implements Serializable {

    private static final long serialVersionUID = 4307048780408320559L;

    private String id;//主键
    private String question;//问题名称
    private String answer;//问题答案
    private Date createtime;//提问时间
    private String questionuser;//提问人
    private Date answertime;//回答时间
    private String answeruser;//回答人
    private String questiontype;//问题类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getQuestionuser() {
        return questionuser;
    }

    public void setQuestionuser(String questionuser) {
        this.questionuser = questionuser;
    }

    public Date getAnswertime() {
        return answertime;
    }

    public void setAnswertime(Date answertime) {
        this.answertime = answertime;
    }

    public String getAnsweruser() {
        return answeruser;
    }

    public void setAnsweruser(String answeruser) {
        this.answeruser = answeruser;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }
}
