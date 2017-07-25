package com.primeforce.prodcast;

import com.primeforce.prodcast.dto.ProdcastDTO;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.QueryParam;
import java.util.Properties;

/**
 * Created by sarathan732 on 8/7/2016.
 */
public class EmailSender {

    public static void main(String[] args){

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtp.port", "465");
            //props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ehlo", "false");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            String username = "AKIAJ32EKFNBCZIPIA5A";
            String password = "AlYhHv63z8rx8WFHJIn9a5Jh9ggNrcPGYWMSVQRIw4G/";

            javax.mail.Session session = javax.mail.Session.getDefaultInstance(props);
            session.setDebug(true);

            try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("prodcastadmin@samasysdev.awsapps.com", "Prodcast Administrator"));
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress("t_saravanan@hotmail.com", "Mr. Shravan"));
                msg.setSubject("Your password is this");
                Transport tpt = session.getTransport("smtps");
                System.out.println("Before Connect");
                tpt.connect("email-smtp.us-east-1.amazonaws.com", username , password);
                System.out.println("Connected");
                tpt.sendMessage(msg, msg.getAllRecipients());
            } catch (Exception er){
                System.out.println(er.toString());
                //er.printStackTrace();
            }

        }
    }
