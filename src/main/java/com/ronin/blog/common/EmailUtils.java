package com.ronin.blog.common;

import com.ronin.blog.service.impl.CommentServiceImpl;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: 98
 * @Date: 2019-7-4 9:19
 */
public class EmailUtils {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    private static final String EMAIL_SMTP = "smtp.qq.com";

    private static final String EMAIL_USERNAME = "843482858@qq.com";

    private static final String EMAIL_PASSWORD = "gkujcfhcjgpybbba";

    private static final String EMAIL_SUBJECT = "RoninBlog回复邮件";

    private static final String EMAIL_MESSAGE = "感谢您留言我的网站--www.ronintottoo.me，本篇邮件是对于您留言的内容进行的回复：";

    public static int sendEmail(String addTo,String sendText){
        Email email = new HtmlEmail();
        email.setHostName(EMAIL_SMTP);
        email.setSmtpPort(465);
        email.setAuthentication(EMAIL_USERNAME,EMAIL_PASSWORD);
        email.setSSLOnConnect(true);
        email.setCharset("utf-8");
        try {
            email.setFrom(EMAIL_USERNAME);
            email.setSubject(EMAIL_SUBJECT);
            email.setMsg(EMAIL_MESSAGE + sendText);
            email.addTo(addTo);
            email.send();
            //成功返回1
            return 1;
        } catch (EmailException e) {
            logger.error("邮件发送失败");
            e.printStackTrace();
            return 0;
        }
    }


}
