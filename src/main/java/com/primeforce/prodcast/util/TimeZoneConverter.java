package com.primeforce.prodcast.util;

/**
 * Created by Hai on 9/10/2016.
 */
public class TimeZoneConverter {
    public long getCurrentTimeForCountry(String countryId)
    {
        return System.currentTimeMillis();
    }
    public long getSpecificTimeForCountry(long time,String countryId)
    {
      return time;
    }
}
