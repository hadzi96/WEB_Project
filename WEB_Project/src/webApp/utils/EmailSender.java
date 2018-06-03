package webApp.utils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.common.io.Files;

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
			message.setSubject("Item sold");
			message.setText("Image sold\n" + "name: " + imageName + "\nresolution [" + imageResolution + "]");

			Transport.send(message);

			System.out.println("Information mail sent...");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static synchronized void sendPictures(String email, List<byte[]> pictures, List<String> pictureNames) {
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
			// creates a new e-mail message
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(hostMail));
			InternetAddress[] toAddresses = { new InternetAddress(email) };
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			msg.setSubject("Your Order");
			msg.setSentDate(new Date());

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("Picture names: " + pictureNames.toString(), "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			int counter = 0;
			for (byte[] pic : pictures) {
				MimeBodyPart attachPart = new MimeBodyPart();
				File file = new File("D:/Sending/" + counter + pictureNames.get(counter) + ".png");
				Files.write(pic, file);
				attachPart.attachFile(file);
				multipart.addBodyPart(attachPart);
				counter++;
			}

			// sets the multi-part as e-mail's content
			msg.setContent(multipart);

			Transport.send(msg);

			System.out.println("Photos sent...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
