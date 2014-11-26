package org.openwebflow.alarm.impl;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailSender extends MailSenderSupport
{

	public void sendMail(String receiver, String subject, String message) throws Exception
	{
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议
		properties.setProperty("mail.smtp.auth", "true");//需要验证

		Session session = Session.getInstance(properties);
		session.setDebug(false);
		//邮件信息
		Message messgae = new MimeMessage(session);
		messgae.setFrom(new InternetAddress(getMailFrom()));//设置发送人
		messgae.setText(message);//设置邮件内容
		messgae.setSubject(subject);//设置邮件主题
		//发送邮件
		Transport tran = session.getTransport();
		tran.connect(getServerHost(), getServerPort(), getAuthUserName(), getAuthPassword());
		tran.sendMessage(messgae, new Address[] { new InternetAddress(receiver) });//设置邮件接收人
		tran.close();

		Logger.getLogger(this.getClass()).debug(String.format("sent mail to <%s>: %s", receiver, subject));
	}
}
