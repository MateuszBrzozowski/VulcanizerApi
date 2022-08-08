package pl.mbrzozowski.vulcanizer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    public static final String SUBJECT_RESET_PASSWORD_LINK = "Link to reset password";
    public static final String SUBJECT_CONFIRM_EMAIL = "Please confirm your email";
    public static final String SUBJECT_YOUR_ACCOUNT_WAS_BLOCKED = "Your account was blocked";
    public static final String SUBJECT_PASSWORD_CHANGED_CORRECTLY = "Password changed";
    public static final String SUBJECT_BUSINESS_APPLICATION = "Password changed";

    public void confirmYourEmail(String to, String token) {
        //TODO
        // Config and testing

        log.info("EMAIL: ");
        log.info("SUBJECT: {}", SUBJECT_CONFIRM_EMAIL);
        log.info("TEXT: Please confirm your email: http://localhost:4200/users/confirm?id={}", token);
//        send(to, subject, body);
    }

    public void resetPassword(String to, String token) {
        //TODO
        // Config and testing
        log.info("EMAIL: ");
        log.info("SUBJECT: {}", SUBJECT_RESET_PASSWORD_LINK);
        log.info("TEXT: To reset your password click link: http://localhost:4200/users/resetpass?id={}", token);
        //send(to,subject,body);
    }

    public void yourAccountWasBlocked(String email) {
        //TODO
        // Config and testing
        log.info("EMAIL: ");
        log.info("SUBJECT: {}", SUBJECT_YOUR_ACCOUNT_WAS_BLOCKED);
        log.info("TEXT: Yours account was blocked because you entered the wrong password too many times when logging in. " +
                "You can active it by reset password.");
        //send(to,subject,body);
    }

    public void yourPasswordChangedCorrectly(String to) {
        //TODO
        // Config and testing, maybe user can blocked account if this change has not made by him
        log.info("EMAIL: ");
        log.info("SUBJECT: {}", SUBJECT_PASSWORD_CHANGED_CORRECTLY);
        log.info("TEXT: Your password changed correctly.");
        //send(to,subject,body);
    }

    public void businessApplicationAccepted(String to) {
        //TODO
        // Config and testing, maybe user can blocked account if this change has not made by him
        log.info("EMAIL: ");
        log.info("SUBJECT: {}", SUBJECT_BUSINESS_APPLICATION);
        log.info("TEXT: Przyjeliśmy zgłoszenie rejestracji biznesu. Wynik werfikacji prześlemy w osobnym mailu.");
        //send(to,subject,body);
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
