package com.primeforce.prodcast.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hai on 10/15/2016.
 */
public class TimeZoneConvertor {
       public static java.sql.Date getCurrentDateForTimeZone(String timeZone) {
           return convertDateForTimeZone(new Date(),timeZone);

   }
       public static java.sql.Date convertDateForTimeZone(Date d, String timeZone) {
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(d);
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//Here you say to java the initial timezone. This is the secret
              sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
//Will print in UTC
              return java.sql.Date.valueOf(sdf.format(calendar.getTime()));


       }


}
