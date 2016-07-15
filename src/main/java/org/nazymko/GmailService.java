package org.nazymko;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringJoiner;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class GmailService implements Service {

    private static final GmailService INSTANCE = new GmailService();
    private transient final HashMap<User, Session> sessionCache = new HashMap<User, Session>();

    /**
     * singleton
     */
    private GmailService() {
    }

    public static GmailService getInstance() {
        return INSTANCE;
    }

    public Session session(final User user) {
        if (!sessionCache.containsKey(user)) {
            Session session = Session.getInstance(config(),
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user.user(), user.password());
                        }
                    });
            sessionCache.put(user, session);

            return sessionCache.get(user);
        } else {
            return sessionCache.get(user);
        }

    }

    public Properties config() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    public void send(Message message) throws MessagingException {
        Transport.send(message);
    }

    public void send(org.nazymko.mailer.Message message, Session session) throws MessagingException {
        send(convert(message, session));
    }

    private MimeMessage convert(org.nazymko.mailer.Message message, Session session) throws MessagingException {
        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(message.getFrom()));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address(message.getTo())));
        msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(address(message.getBcc())));
        msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(address(message.getCc())));

        msg.setSubject(message.getSubject());
        msg.setContent(message.getMessage(), message.getContext());

        return msg;
    }

    private String address(String[] list) {
        StringJoiner joiner = new StringJoiner(",");
        for (String element : list) {
            joiner.add(element);
        }
        return joiner.toString();
    }


}
