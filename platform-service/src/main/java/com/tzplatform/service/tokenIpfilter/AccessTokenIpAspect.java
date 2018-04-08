package com.tzplatform.service.tokenIpfilter;

import com.github.pagehelper.util.StringUtil;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.utils.common.InetAddressUtil;
import com.tzplatform.utils.common.IpUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Order(5)
@Component
public class AccessTokenIpAspect {
    @Value("${web.appAccessToken}")
    private String apptoken;

    @Value("${appIp}")
    private String appIp;

    private Logger logger=Logger.getLogger(AccessTokenIpAspect.class);

    @Around("@annotation(com.tzplatform.service.tokenIpfilter.AccessTokenIp)")
    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getParameter(CommonEnum.APP_ACCESSTOKEN.TOKEN.getValue());
        BaseResultDto baseResultDto=new BaseResultDto();
        //如果token相等
        if(StringUtil.isNotEmpty(token)){
            if(apptoken.equals(token)){
                String ip= InetAddressUtil.getRemoteHost(request);
                boolean flag = IpUtil.ipExistsInRange(ip, appIp);
                if(flag){
                    Object object = pjp.proceed(); //执行连接点方法
                    return object;
                }else{
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_APP_ACCESSTOKENIP);
                }

            }else{
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_APP_ERRORACCESSTOKEN);
            }
        }else{
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_APP_NOACCESSTOKEN);
        }
        return baseResultDto;
    }
}
