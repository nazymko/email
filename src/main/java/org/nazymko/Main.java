package org.nazymko;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {

    public static void main(String[] args) {
        final String username = "";
        final String password = "";

        Session session = GmailService.getInstance().session(new UserImpl(username, password));


        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("nazymko.patronus@gmail.com"));

            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("nazymko.patronus@gmail.com"));
            message.setSubject("Testing Subject. Yeah!");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            GmailService.getInstance().send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
