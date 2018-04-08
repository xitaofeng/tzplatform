package com.tzplatform.service.sendmail;

import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 发送邮件 多线程处理
 *
 * @author leijie
 */
@Component
public class SendEmailThread {

    @Value("${email.smtpServer}")
    private String smtpServer;// 服务smtp

    @Value("${email.sendEmailUser}")
    private String sendEmailUser;// 邮件发送人

    @Value("${email.sendPassword}")
    private String sendPassword;// 邮件发送密码

    // 内部类去发送邮件
    class EmailThread implements Runnable {

        private String recieveEmailUser;// 邮件接收人

        private String subject;// 主题

        private String content;// 内容

        private String htmlContent;// html内容

        @Override
        public void run() {
            try {
                long startTime = System.currentTimeMillis();
                SmtpServer<?> smtp = SmtpServer.create(smtpServer);
                smtp.authenticateWith(sendEmailUser, sendPassword);
                SendMailSession session = smtp.createSession();
                Email email = Email.create().from(sendEmailUser).to(recieveEmailUser).subject(subject).addHtml(htmlContent);
                session.open();// 打开连接
                session.sendMail(email);// 发送邮件
                session.close();// 关闭连接
                long endTime = System.currentTimeMillis();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        public EmailThread() {
            super();
        }

        public EmailThread(String recieveEmailUser, String subject, String content, String htmlContent) {
            super();
            this.recieveEmailUser = recieveEmailUser;
            this.subject = subject;
            this.content = content;
            this.htmlContent = htmlContent;
        }

    }

}
