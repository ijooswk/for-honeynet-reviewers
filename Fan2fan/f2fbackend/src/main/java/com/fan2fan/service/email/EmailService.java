package com.fan2fan.service.email;

/**
 * 邮件服务接口
 * @author Ruici Luo
 *
 */
public interface EmailService {

    /**
     * 简单的邮件发送服务
     *
     * @param to
     *            收件人邮件地址
     * @param subject
     *            主题
     * @param content
     *            ，由于设置的contentType是text/html，所以content可以是html格式的
     */
    void sendEmail(String to, String subject, String content);

}