package pl.mbrzozowski.vulcanizer.service;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import static pl.mbrzozowski.vulcanizer.constant.EmailConstant.*;

@Service
public class EmailService {

    public void sendWelcomeEmail(String firstName, String password, String email) {
        try {
            Message message = createMail(firstName, password, email);
            Transport transport = getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
            transport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message createMail(String firstName, String password, String email) throws MessagingException {
        InternetAddress[] addresses = InternetAddress.parse(email, false);
        InternetAddress address = Arrays.stream(addresses).findFirst().get();
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email, false));
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setText(String.format("Hello %s\n\n Your account is registered.", firstName));
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);

    }
}
