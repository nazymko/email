package org.nazymko;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import java.util.Properties;

/**
 * Created by nazymko.patronus@gmail.com
 */
public interface Service {

    Session session(User user);

    Properties config();

    void send(Message message) throws MessagingException;


}
