package com.poly.DATN_BookWorms.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.beans.MailInformation;
import com.poly.DATN_BookWorms.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImp implements MailService{
	private static final Logger logger = LogManager.getLogger();
	
	private List<MailInformation> listMails = new ArrayList<>();
	
	@Autowired
	JavaMailSender sender;
	
	@Override
	public void send(MailInformation mail) throws MessagingException {
		logger.info("send mail start .....");
		logger.info("send mail with MailInformation : {}", mail.toString());
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
			messageHelper.setFrom(mail.getTo());
			messageHelper.setTo(mail.getTo());
			messageHelper.setSubject(mail.getSubject());
			messageHelper.setText(mail.getBody(), true);
			messageHelper.setReplyTo(mail.getFrom());
			String[] cc = mail.getCc();// ktra mang cc co ton tai hay khong
			if (cc != null && cc.length > 0) {
				messageHelper.setCc(cc);
				logger.info("send mail with cc : {}",cc.toString());
			}
			String[] bcc = mail.getBcc();// ktra mang bcc co ton tai hay khong
			if (bcc != null && bcc.length > 0) {
				messageHelper.setBcc(bcc);
				logger.info("send mail with bcc : {}",bcc.toString());
			}
			List<File> files = mail.getFiles();// mang file
			if (files.size()>0) {
				for (File file:files) {
					messageHelper.addAttachment(file.getName(), file);
					logger.info("send mail with files : {}",files.toString());
				}
			}
			logger.info("send mail with MimeMessageHelper : {}",messageHelper.toString());
			logger.info("send mail with MimeMessage : {}",message.toString());
			// Gửi message đến SMTP server
			sender.send(message);
			logger.info("send mail is successfullt");
		} catch (Exception e) {
			logger.info("send mail is failed with message: {} and error : {}",message.toString(),e);
			// TODO: handle exception
		}
		
	}

	@Override
	public void send(String to, String subject, String body) throws MessagingException {
		logger.info("send mail with : to : {} and subject : {} and body : {}",to.toString(), subject.toString(), body.toString());
		MailInformation mailin = new MailInformation(to, subject, body);
		System.out.println("in mail" + mailin.toString());
		send(mailin);
		
	}

	@Override
	public void queue(MailInformation mail) {
		logger.info("send mail with queue : mail : {}",mail.toString());
		listMails.add(mail);
		
	}

	@Override
	public void queue(String to, String subject, String body) {
		logger.info("send mail with queue : to : {} and subject : {} and body : {}",to.toString(), subject.toString(), body.toString());
		queue(new MailInformation(to, subject, body));
		
	}
	
	@Scheduled(fixedDelay = 5000)
	public void run() {
		while (!listMails.isEmpty()) {
			MailInformation mail = listMails.remove(0);
			try {
				this.send(mail);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
