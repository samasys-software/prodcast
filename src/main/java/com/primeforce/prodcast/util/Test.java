package com.primeforce.prodcast.util;

import ch.qos.logback.core.util.FileUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.xml.transform.Source;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by sarathan732 on 9/3/2016.
 */
public class Test {

    public static void main(String[] args) throws Exception{
        System.out.println(readString("templates/EmailTemplate.html.html"));
    }
    public static String readString(String fileName) throws IOException, URISyntaxException
    {
        String filePath = fileName;
        InputStream inputStream = new Test().getClass().getResourceAsStream(filePath);

        return toString(inputStream);
    }

    private static String toString(InputStream inputStream) throws IOException
    {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String result = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        inputStream.close();

        return result;
    }
}
