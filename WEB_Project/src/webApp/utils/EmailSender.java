package webApp.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	private final static String hostMail = "rafweb2018@outlook.com";
	private final static String password = "H6X\\GvWLRq";

	public static synchronized void sendVerification(String email, String token) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(hostMail, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(hostMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("E-mail verification");
			message.setText(
					"verification link: http://localhost:8080/WEB_Project/server/user/validation?token=" + token);

			Transport.send(message);

			System.out.println("Verification mail sent...");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static synchronized void informSeller(String email, String imageName, String imageResolution) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(hostMail, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(hostMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("E-mail verification");
			message.setText("Image SOLD\n" + "name: " + imageName + "\nresolution [" + imageResolution + "]");

			Transport.send(message);

			System.out.println("Information mail sent...");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
