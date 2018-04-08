package com.tzplatform.service.aoplog;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.system.PlatFormExceptionLog;
import com.tzplatform.entity.system.PlatFormNormalLog;
import com.tzplatform.service.system.PlatFormLogService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.CommonUtils;
import com.tzplatform.utils.common.UidUtils;
import com.tzplatform.utils.jwtauth.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * aop技术去记录日志功能 免得写过多的调用代码造成冗余
 *
 * @author leijie
 */
@Aspect
@Order(5)
@Component
public class WebSystemLogAspect {

    private Logger logger = Logger.getLogger(getClass());

    @Resource
    private PlatFormLogService platFormLogService;


    //保证线程访问顺序
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.tzplatform.utils.aoplog.AopLog)")
    public void serviceErrorLog() {
    }

    /**
     * 环绕通知 用于拦截service层记录用户 增 删 改操作日志
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "serviceErrorLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.debug("Service_URL : " + request.getRequestURL().toString());
        logger.debug("Service_HTTP_METHOD : " + request.getMethod());
        logger.debug("Service_IP : " + CommonUtils.getRemoteAddress(request));
        logger.debug("Service_CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.debug("Service_ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.debug("Service_METHOD_NAME:" + getServiceMthodDescription(joinPoint).get("description"));
        logger.debug("Service_MENU_NAME:" + getServiceMthodDescription(joinPoint).get("menuname"));

        String handleip = CommonUtils.getRemoteAddress(request);
        String menuname = getServiceMthodDescription(joinPoint).get("menuname").toString();
        String description = getServiceMthodDescription(joinPoint).get("description").toString();
        String token = request.getHeader(CommonEnum.HEAD_PARAM.TOKEN.getValue());

        String accountid ="";
        if(StringUtils.isNotBlank(token)){
            accountid = JwtUtils.veriftToken(token);
        }

        String handleresult = "";
        if (ret instanceof BaseResultDto) {
            if (((BaseResultDto) ret).getCode().equals("1")) {
                handleresult = CommonEnum.HANDLE_REUSLT.success.getValue();
            } else {
                handleresult = CommonEnum.HANDLE_REUSLT.failed.getValue();
            }
        }
        PlatFormNormalLog platFormNormalLog = new PlatFormNormalLog(UidUtils.getId(), accountid, "", menuname, handleip, description, handleresult, new Date(), new Date());
        platFormLogService.insertNormalLog(platFormNormalLog);


    }

    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceErrorLog()", throwing = "e")
    public void doafterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.debug("Service_异常代码:" + e.getClass().getName());
        logger.debug("Service_异常信息:" + e.getMessage());
        logger.debug("Service_异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
        logger.debug("Service_Exception_URL : " + request.getRequestURL().toString());
        logger.debug("Service_Exception_HTTP_METHOD : " + request.getMethod());
        logger.debug("Service_Exception_IP : " + CommonUtils.getRemoteAddress(request));
        logger.debug("Service_Exception_CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.debug("Service_Exception_ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.debug("Service_Exception_METHOD_NAME:" + getServiceMthodDescription(joinPoint).get("description"));
        logger.debug("Service_Exception_MENU_NAME:" + getServiceMthodDescription(joinPoint).get("menuname"));

        String handleip = CommonUtils.getRemoteAddress(request);
        String menuname = getServiceMthodDescription(joinPoint).get("menuname").toString();
        String description = getServiceMthodDescription(joinPoint).get("description").toString();

        String methodname = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        String errorinfo = e.getClass().getName();

        String token = request.getHeader(CommonEnum.HEAD_PARAM.TOKEN.getValue());
        String accountid ="";
        if(StringUtils.isNotBlank(token)){
            accountid = JwtUtils.veriftToken(token);
        }

        PlatFormExceptionLog platFormExceptionLog = new PlatFormExceptionLog(UidUtils.getId(), accountid, handleip, "", menuname, description, methodname, errorinfo, new Date());
        platFormLogService.insertExceptionLog(platFormExceptionLog);
    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Map<String, Object> resultPropertyMap = new HashMap<>();
        String description = "";
        String menuname = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(AopLog.class).description();
                    menuname = method.getAnnotation(AopLog.class).menuname();
                    resultPropertyMap.put("description", description);
                    resultPropertyMap.put("menuname", menuname);
                    break;
                }
            }
        }
        return resultPropertyMap;
    }
}