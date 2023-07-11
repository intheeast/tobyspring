package com.intheeast.springframe.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.intheeast.springframe.dao.UserDao;
import com.intheeast.springframe.domain.Level;
import com.intheeast.springframe.domain.User;

public class UserService {
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;

	private UserDao userDao;
	private MailSender mailSender;
//	private JavaMailSenderImpl  mailSender;
	private PlatformTransactionManager transactionManager;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	public void upgradeLevels() {
		TransactionStatus status = 
				this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			List<User> users = userDao.getAll();
			for (User user : users) {
				if (canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			this.transactionManager.commit(status);
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}
	
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel(); 
		switch(currentLevel) {                                   
		case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER); 
		case SILVER: return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
		case GOLD: return false;
		default: throw new IllegalArgumentException("Unknown Level: " + currentLevel); 
		}
	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEMail(user);
	}
	
	private void sendUpgradeEMail(User user) {
				
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("useradmin@ksug.org");
		mailMessage.setSubject("Upgrade 반가워요");
		mailMessage.setText("테스트 메일입니다. " + user.getLevel().name());
		
		this.mailSender.send(mailMessage);
	}
	
	
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
//	private void sendUpgradeEMailThroughJAVAMAIL(User user) {
//		
//		MimeMessage mailMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
//
//        try {
//            helper.setFrom(mailSender.getUsername());
//            helper.setTo(user.getEmail()); // 수신자 이메일 주소
//            helper.setSubject("반가워요"); // 제목
//            helper.setText("테스트 메일입니다."); // 내용
//
//            mailSender.send(mailMessage);
//
//            System.out.println("Email sent successfully!");
//        } catch (MessagingException e) {
//            System.out.println("Failed to send email. Error message: " + e.getMessage());
//            fail("This sendEmailToGmail test has failed!!!");
//        }
//		
//		this.mailSender.send(mailMessage);
//	}
}
