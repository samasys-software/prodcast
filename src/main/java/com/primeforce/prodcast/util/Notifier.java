package com.primeforce.prodcast.util;

import javax.mail.internet.InternetAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sarathan732 on 9/5/2016.
 */
public class Notifier extends TimerTask {
    private static Timer notificationHelper = new Timer();

    private String smsPhoneNumber;
    private String subject;
    private String mailMessage;
    private String[] emailIds;

    public Notifier(String smsPhoneNumber, String subject, String mailMessage , String[] mailIds ){
        this.smsPhoneNumber = smsPhoneNumber;
        this.subject = subject;
        this.mailMessage = mailMessage;
        this.emailIds = mailIds;
    }
    public void run(){
        try {
            if(smsPhoneNumber!=null) {
                System.out.println("Sending SMS "+smsPhoneNumber+" Subject "+subject );
                Amazon.sendSMS(subject, smsPhoneNumber);
            }
        }
        catch(Exception er){
            er.printStackTrace();
        }
        
        if( mailMessage == null ) return;

        try {
            List<InternetAddress> list = new LinkedList<InternetAddress>();
            for (int i = 0; i < emailIds.length; i++) {
                InternetAddress temp = Amazon.isValidEmailAddress(emailIds[i]);
                if (temp != null) list.add(temp);
            }

            System.out.println("Sending Email to " + list);

            if (list.size() > 0) {
                InternetAddress[] emailIds = new InternetAddress[list.size()];
                emailIds = list.toArray(emailIds);

                Amazon.sendEmail(emailIds, subject, mailMessage);
            }
        }
        catch(Exception er){
            er.printStackTrace();
        }


    }
    public static void sendNotification( String smsPhoneNumber , String subject, String mailMessage, String[] mailIds ){
        Notifier notifier = new Notifier( smsPhoneNumber , subject,  mailMessage , mailIds );
        notificationHelper.schedule( notifier, 0 );
    }
}
