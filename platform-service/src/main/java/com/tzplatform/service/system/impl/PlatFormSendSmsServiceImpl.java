package com.tzplatform.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.system.SmsEntity;
import com.tzplatform.service.system.PlatFormSendSmsService;
import com.tzplatform.utils.common.HttpClientHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "platFormSendSmsService")
public class PlatFormSendSmsServiceImpl implements PlatFormSendSmsService {

    private Logger logger = Logger.getLogger(PlatFormSendSmsServiceImpl.class);

    @Value("${sms.sendUrl}")
    private String sendUrl;

    @Value("${sms.validUrl}")
    private String validateUrl;

    private final String SMS_MOBILE = "mobile";

    private final String SMS_CAPTCHA = "captcha";

    /**
     * 发送短信
     *
     * @param telphone
     * @return
     */
    @Override
    public BaseResultDto sendSms(String telphone) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair(SMS_MOBILE, telphone));
        String jsonStr = "";
        try {
            jsonStr = HttpClientHelper.requestPost(sendUrl, params);
            SmsEntity smsEntity = JSON.parseObject(jsonStr, SmsEntity.class);
            if ("0".equals(smsEntity.getCode())) {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(smsEntity.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 校验短信
     *
     * @param telphone
     * @param captcha
     * @return
     */
    @Override
    public BaseResultDto checKSms(String telphone, String captcha) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair(SMS_MOBILE, telphone));
        params.add(new BasicNameValuePair(SMS_CAPTCHA, captcha));
        String jsonStr = "";
        try {
            jsonStr = HttpClientHelper.requestPost(validateUrl, params);
            SmsEntity smsEntity = JSON.parseObject(jsonStr, SmsEntity.class);
            if ("0".equals(smsEntity.getCode())) {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(smsEntity.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }
}
