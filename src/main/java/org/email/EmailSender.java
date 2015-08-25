package org.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
  
public class EmailSender
{
	
    public static void main(String[] args) 
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
  
        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("shwetabh.gaurav@gmail.com","Sg@3954067");
                }
            });
  
        try {
  
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("my_GMail@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("puja.ps6195@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Hello this is not spam," +
                    "\n\n This is a JavaMail test...!");
  
            Transport.send(message);
  
            System.out.println("Done");
  
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
