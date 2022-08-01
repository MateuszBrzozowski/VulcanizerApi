package pl.mbrzozowski.vulcanizer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void confirmYourEmail(String to, String token) {
        //TODO
        // Config and testing
        String subject = "Please confirm your email";
        String body = token;

        log.info("EMAIL: ");
        log.info("SUBJECT: {}", subject);
        log.info("TEXT: Please confirm your email: http://localhost:4200/users/confirm?token={}", token);
//        send(to, subject, body);
    }

    private void send(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreplay@wulkanizator.pl");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        getJavaMailSender().send(message);
    }

    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.wp.pl");
        mailSender.setPort(465);

        mailSender.setUsername("***");
        mailSender.setPassword("***");
        return mailSender;
    }


//    public void sendWelcomeEmail(String firstName, String password, String email) {
//        try {
//            Message message = createMail(firstName, password, email);
//            Transport transport = getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
//            transport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Message createMail(String firstName, String password, String email) throws MessagingException {
//        InternetAddress[] addresses = InternetAddress.parse(email, false);
//        InternetAddress address = Arrays.stream(addresses).findFirst().get();
//        Message message = new MimeMessage(getEmailSession());
//        message.setFrom(new InternetAddress(FROM_EMAIL));
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email, false));
////        message.setRecipient(Message.RecipientType.TO, new InternetAddress(CC_EMAIL, false));
//        message.setSubject(EMAIL_SUBJECT);
//        message.setText(String.format("Hello %s\n\n Your account is registered.", firstName));
//        message.setSentDate(new Date());
//        message.saveChanges();
//        return message;
//    }
//
//    private Session getEmailSession() {
//        Properties properties = System.getProperties();
//        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
//        properties.put(SMTP_AUTH, true);
//        properties.put(SMTP_PORT, DEFAULT_PORT);
//        properties.put(SMTP_STARTTLS_ENABLE, true);
//        properties.put(SMTP_STARTTLS_REQUIRED, true);
//        return Session.getInstance(properties, null);
//
//    }
}
