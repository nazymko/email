package org.nazymko;

import javax.mail.*;
import java.util.HashMap;
import java.util.Properties;

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
}
