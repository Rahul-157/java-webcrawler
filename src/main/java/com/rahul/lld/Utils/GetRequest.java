package com.rahul.lld.Utils;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class GetRequest {
    private static Logger log= Log.LOGGER;
    public Document get(URL url) throws IOException {
           return Jsoup.newSession().newRequest().url(url.toString()).ignoreHttpErrors(true)
            .followRedirects(true)
            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
            .referrer(url.getHost())
            .get();
        
        
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        InputStream responseStream = connection.getInputStream();
//        BufferedInputStream bis = new BufferedInputStream(responseStream);
//        ByteArrayOutputStream buf = new ByteArrayOutputStream();
//        for (int result = bis.read(); result != -1; result = bis.read()) {
//            buf.write((byte) result);
//        }
//        return Jsoup.parse(buf.toString());
    }
}
