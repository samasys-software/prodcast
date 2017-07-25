package com.primeforce.prodcast.messaging;

import com.primeforce.prodcast.dao.DatabaseManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import java.text.MessageFormat;

import java.util.Scanner;

/**
 * Created by Nandhini on 8/31/2016.
 */
public class MessagingManager {
    public final static String[] TEMPLATES={"templates/Responsive.html","templates/PaymentTemplate.html"};
    public String readString(String fileName) throws IOException, URISyntaxException
    {
        String filePath = "/" + fileName;
        InputStream inputStream = getClass().getResourceAsStream(filePath);

        return toString(inputStream);
    }

    public String toString(InputStream inputStream) throws IOException
    {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String result = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        inputStream.close();

        return result;
    }
    public String getTemplate(int key)
    {
        String str="";
        String template_filename=TEMPLATES[key];

        try
        {
            str=readString(template_filename);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return str;

    }
    public String mailMerge(int key, MessagingDataProvider dataProvider, DatabaseManager dataBaseManager){
        String message = getTemplate(key);

        MessageFormat mf = new MessageFormat(message);
        String formailmerge=mf.format(dataProvider.getData(dataBaseManager));
        return formailmerge;


    }

}
