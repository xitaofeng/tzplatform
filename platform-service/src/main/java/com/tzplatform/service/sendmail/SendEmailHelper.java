package com.tzplatform.service.sendmail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 发送邮件公共类
 *
 * @author leijie
 */
@Service
public class SendEmailHelper {

    @Resource
    private SendEmailThread sendEmailThread;


    // 线程池中所保存的核心线程数。
    @Value("${email.corePoolSize}")
    private static final Integer corePoolSize = 4;

    // 线程池允许创建的最大线程数。
    @Value("${email.maximumPoolSize}")
    private static final int maximumPoolSize = 30;

    // 当前线程池线程总数大于核心线程数时，终止多余的空闲线程的时间。
    @Value("${email.keepAliveTime}")
    private static final long keepAliveTime = 0;

    // 邮件队列（线程共享）
    private static BlockingQueue<Runnable> emailQueue = new LinkedBlockingDeque<Runnable>();

    // 实例化线程池（线程共享）
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, emailQueue, new ThreadPoolExecutor.AbortPolicy());

    public void sendEmail(String recieveEmailUser, String subject, String content, String htmlContent) {
        threadPool.execute(sendEmailThread.new EmailThread(recieveEmailUser, subject, content, htmlContent));
    }

}
