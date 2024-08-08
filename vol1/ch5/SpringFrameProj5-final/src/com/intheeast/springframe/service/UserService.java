package com.intheeast.springframe.service;

import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

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
		
		// mailMessage.setFrom("swseokitec@gamil.com"); 코드는 
		// 이메일 메시지의 "보내는 사람(From)" 주소를 설정하는 부분입니다. 
		// 이 코드는 SimpleMailMessage 객체를 사용하여 생성되는 이메일 메시지에 대한 
		// 발신자의 이메일 주소를 지정합니다.

        // setFrom 메소드: 이 메소드는 이메일 메시지에 포함될 발신자의 이메일 주소를 설정합니다. 
		// 여기서 "swseokitec@gmail.com"는 메일이 발송될 때 수신자에게 보여지는 발신자의 주소입니다.
		// 발신자 주소의 중요성: 발신자 주소는 이메일 수신자가 메일을 받았을 때 "From" 필드에 표시되며, 
		// 메일의 출처를 나타냅니다. 또한, 수신자가 답장을 보낼 경우 이 주소가 기본적으로 사용됩니다.

		// 도메인 일치 여부: 보통 발신자 주소는 메일 서버의 도메인(smtp.gmail.com의 경우 Gmail 계정)과 
		// 일치하는 것이 좋습니다. 하지만 일부 경우에는 다른 주소를 사용할 수도 있습니다. 
		// 이 경우에는 메일 서버 설정이나 보안 정책에 따라 메일이 거부되거나 스팸으로 분류될 수도 있습니다.

		// 이 코드에서는 mailSender 객체를 통해 실제 이메일 전송이 이루어지며, 
		// mailMessage.setFrom("useradmin@ksug.org");를 통해 발신자 주소를 설정하고 있습니다. 
		// 이 주소는 메일을 받는 사람이 보게 될 '보내는 사람' 주소입니다.
		mailMessage.setFrom("swseokitec@gmail.com");//
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
