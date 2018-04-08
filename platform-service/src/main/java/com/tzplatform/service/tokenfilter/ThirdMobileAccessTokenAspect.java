package com.tzplatform.service.tokenfilter;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.service.common.RedisClusterHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Order(5)
@Component
public class ThirdMobileAccessTokenAspect {
    Logger logger = Logger.getLogger(AccessTokenAspect.class);

    @Resource
    private RedisClusterHelper redisClusterHelper;

    private final String TOKEN_NAME = "access_token";

    @Around("@annotation(com.tzplatform.service.tokenfilter.ThirdMobileAccessToken)")
    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        BaseResultDto baseResultDto = new BaseResultDto();
        String token = request.getParameter(TOKEN_NAME);

        logger.debug("app端传过来的token================"+token);

        if (StringUtils.isNotBlank(token)) {
            boolean result = redisClusterHelper.exists(token);
            if (result) {
                Object object = pjp.proceed(); //执行连接点方法
                return object;
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setData(ResultMessage.FAILED_VERIFY_TOKEN);
                baseResultDto.setMsg(ResultMessage.FAILED_TOKEN_EXPIRE_MESSAGE);
                return baseResultDto;
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setData(ResultMessage.FAILED_VERIFY_TOKEN);
            baseResultDto.setMsg(ResultMessage.FAILED_TOKEN_EXPIRE_MESSAGE);
            return baseResultDto;
        }
    }
}

