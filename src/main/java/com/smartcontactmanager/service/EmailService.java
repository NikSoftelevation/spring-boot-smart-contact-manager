package com.smartcontactmanager.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    /*String to = "bhardwajnikhil0777@mail.com";
    String from = "nikhil.softelevation@gmail.com";
    String subject = "Smart Contact Manager Confirmation";
    String message = "Hello ! This message is for security check";
*/
    public boolean sendEmail(String subject, String message, String to) {

        boolean f = false;

        String from = "nikhil.softelevation@gmail.com";

        /*Variable(host) for gmail*/
        String host = "smtp.gmail.com";

        /*get the system properties*/
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES" + properties);

        /*setting important information to properties object*/

        /*host set*/
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        /*Step 1: To get the session object*/
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("nikhil.softelevation@gmail.com", "welcome@123");
            }
        });
        session.setDebug(true);
        /*Step 2: Compose the message [text,multi-media]*/
        MimeMessage m = new MimeMessage(session);

        try {
            /*From email*/
            m.setFrom(from);

            /*Adding Recipient to message */
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            /*Adding Subject to message*/
            m.setSubject(subject);

            /*Adding Text to message*/
            //m.setText(message);
            m.setContent(message, "text/html");

            /*Send*/

            /*Step 3: Send the message using Transport class*/
            Transport.send(m);

            System.out.println("Sent Success ................");
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        return f;
    }
}