package com.cybermediatheque.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Classe utilitaire d'envoi de mail
 * 
 * @author sylar
 * @version 1.0
 */
public class EmailSender {

	protected static String user = null;
	protected static String password = null;
	private static Multipart mult = new MimeMultipart();
	protected static MimeMessage message;

	public static void envoyerMail(String email, String subject, String text) throws MessagingException, IOException {
		Properties props = new Properties();

		props.put("mail.from", "cybermediatheque@gmail.com"); // @
																// expediteur
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Properties properties = new Properties();
		InputStream inputStream = EmailSender.class.getResourceAsStream("mail.properties");
		properties.load(inputStream);
		user = properties.getProperty("user");
		password = properties.getProperty("password");

		Session lSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);// Specify the Username and the PassWord
			}
		});

		message = new MimeMessage(lSession);
		message.setSubject(subject);
		message.setSentDate(new Date());
		InternetAddress recipient = new InternetAddress(email);
		message.setRecipient(Message.RecipientType.TO, recipient);

		// corps du message
		BodyPart lBody = new MimeBodyPart();
		lBody.setContent(text, "text/plain");
		mult.addBodyPart(lBody);

		// affectation du receptacle multiparties au message
		message.setContent(mult);

		// Transport
		Transport.send(message);

	}

}
